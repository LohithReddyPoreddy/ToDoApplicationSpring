package com.example.demo.repository;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByPriority(TaskPriority priority);
    List<Task> findAllByDeadLine(LocalDate date);
    List<Task> findAllByCreationDate(LocalDate date);
    List<Task> findAllByCompleted(boolean status);
    Task findByTaskName(String name);

}
