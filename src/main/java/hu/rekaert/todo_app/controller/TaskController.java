package hu.rekaert.todo_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hu.rekaert.todo_app.model.Task;
import hu.rekaert.todo_app.service.TaskService;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "tasks-list";
    }

    public Task getTaskById(Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/add")
    public String createTask(@RequestParam String title, @RequestParam String description) {
        Task task = new Task(title, description, false);
        taskService.createTask(task);
        return "redirect:/tasks";
    }

    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean completed) {
        taskService.updateTask(id,completed);
        return "redirect:/tasks";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

}
