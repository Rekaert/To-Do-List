package hu.rekaert.todo_app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import hu.rekaert.todo_app.model.Task;
import hu.rekaert.todo_app.repository.TaskRepository;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testGetAllTasks() {

        List<Task> mockTasks = List.of(
            new Task(1L, "Task 1", "Description 1", false),
            new Task(2L, "Task 2", "Description 2", true));

        when(taskRepository.findAll()).thenReturn(mockTasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskById_Found() {

        Task mockTask = new Task(1L, "Task 1", "Description 1", false);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));

        Task result = taskService.getTaskById(1L);

        assertNotNull(result);
        assertEquals("Task 1", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskById_NotFound() {

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        Exception result = assertThrows(RuntimeException.class, () -> taskService.getTaskById(1L));
        assertEquals("Task not found", result.getMessage());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTask() {
        Task newTask = new Task(null, "Title 1", "Description 1", false);
        Task savedTask = new Task(1L, "Title 1", "Description 1", false);
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.createTask(newTask);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(taskRepository, times(1)).save(newTask);

    }

    @Test
    void testUpdateTask() {

        Task existingTask = new Task(1L, "Title 1", "Description 1", false);
        Task updatedTask = new Task(1L, "Title 1", "Description 1", true);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        Task result = taskService.updateTask(1L, true);

        assertNotNull(result);
        assertTrue(result.getCompleted());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(updatedTask);
    }

    @Test
    void testDeleteTask() {

        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

}
