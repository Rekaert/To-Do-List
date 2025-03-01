package hu.rekaert.todo_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import hu.rekaert.todo_app.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
