package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;
import com.example.demo.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;



    public Optional<Task> getTask(long id){
        return taskRepository.findById(id);
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
    public Task addTask(Task task){
        Task addTask = taskRepository.save(task);
        return addTask;
    }

    public Task updateTask(long id,Task task){
        if(taskRepository.existsById(id)){
            task.setTaskId(id);
            return taskRepository.save(task);
        }
        return null;
    }

    public void deleteTask(long id){
        taskRepository.deleteById(id);
    }

    public List<Task> getAllTasksByPriority(TaskPriority priority){
        return taskRepository.findAllByPriority(priority);
    }

    public List<Task> getAllTasksByDeadLine(LocalDate date){
        return taskRepository.findAllByDeadLine(date);
    }

    public List<Task> getAllTasksByCreationDate(LocalDate date){
        return taskRepository.findAllByCreationDate(date);
    }

    public List<Task> getAllByStatus(boolean status){
        return taskRepository.findAllByCompleted(status);
    }

    public Optional<Task> getByTaskName(String name){
        return Optional.ofNullable(taskRepository.findByTaskName(name));
    }

}
