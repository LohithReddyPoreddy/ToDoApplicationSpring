package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;
import com.example.demo.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    @Autowired
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    public void setUp(){
        task = new Task();
        task.setTaskId(1L);
        task.setTaskName("ToDo");
        task.setTaskData("Build the application using spring");
        task.setCompleted(false);
        task.setDeadLine(LocalDate.parse("2024-12-15", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        task.setCreationDate(LocalDate.now());
        task.setPriority(TaskPriority.valueOf("HIGH"));
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddTask(){
        when(taskRepository.save(task)).thenReturn(task);

        Task createdTask = taskService.addTask(task);

        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getTaskName()).isEqualTo(task.getTaskName());
    }

    @Test
    public void testDeleteTask(){
        taskRepository.deleteById(task.getTaskId());

        verify(taskRepository, times(1)).deleteById(task.getTaskId());
    }

    @Test
    public void testUpdateTask(){
        long taskId = 1;
        Task task1 = new Task(2L, "Task2","This is task 2",TaskPriority.HIGH,LocalDate.now(),LocalDate.now(),false);

        when(taskRepository.existsById(taskId)).thenReturn(true);
        when(taskRepository.save(task1)).thenReturn(task1);

        Task updatedTask = taskService.updateTask(taskId, task1);

        assertThat(updatedTask).isNotNull();
        assertThat(updatedTask.getTaskId()).isEqualTo(1L);
        assertThat(updatedTask.getTaskName()).isEqualTo(task1.getTaskName());
        assertThat(updatedTask.getCreationDate()).isEqualTo(task1.getCreationDate());
    }

    @Test
    public void testUpadteWithNonExistingTask(){
        long taskId = 2;
        Task task1 = new Task(1L, "Task2","This is task 2",TaskPriority.HIGH,LocalDate.now(),LocalDate.now(),false);

        when(taskRepository.existsById(taskId)).thenReturn(false);
        when(taskRepository.save(task1)).thenReturn(null);

        Task updatedTask = taskService.updateTask(taskId, task1);

        assertThat(updatedTask).isNull();
    }

    @Test
    public void testGetAllTasks(){
        Task task1 = new Task(2L, "Task2","This is task 2",TaskPriority.HIGH,LocalDate.now(),LocalDate.now(),false);
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task,task1));

        List<Task> tasks1 = taskService.getAllTasks();

        assertThat(tasks1).isNotNull();
        assertThat(tasks1.size()).isEqualTo(2);

    }

    @Test
    public void testGetAllByPriority(){
        TaskPriority priority = TaskPriority.HIGH;
        when(taskRepository.findAllByPriority(priority)).thenReturn(Arrays.asList(task));

        List<Task> tasks = taskService.getAllTasksByPriority(priority);

        assertThat(tasks).isNotNull();
        assertThat(tasks.size()).isEqualTo(1);
        assertThat(tasks.getFirst().getPriority()).isEqualTo(priority);
    }

    @Test
    public void testGetAllTasksByDeadline(){
        LocalDate date = LocalDate.parse("2024-12-15", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        when(taskRepository.findAllByDeadLine(date)).thenReturn(Arrays.asList(task));

        List<Task> tasks = taskService.getAllTasksByDeadLine(date);

        assertThat(tasks).isNotNull();
        assertThat(tasks.size()).isEqualTo(1);
        assertThat(tasks.getFirst().getDeadLine()).isEqualTo(date);
    }

    @Test
    public void testGetAllTasksByCreationdate(){
        LocalDate date = LocalDate.parse("2024-12-13", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        when(taskRepository.findAllByCreationDate(date)).thenReturn(Arrays.asList(task));

        List<Task> tasks = taskService.getAllTasksByCreationDate(date);

        assertThat(tasks).isNotNull();
        assertThat(tasks.size()).isEqualTo(1);
        assertThat(tasks.getFirst().getCreationDate()).isEqualTo(date);
    }

    @Test
    public void testReadTask(){
        when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskService.getTask(task.getTaskId());

        assertThat(foundTask).isNotNull();
        foundTask.ifPresent(value -> assertThat(value.getTaskId()).isEqualTo(task.getTaskId()));

    }

    @Test
    public void testGetTaskByTaskName(){
        String name = "ToDo";
        when(taskRepository.findByTaskName(name)).thenReturn(task);

        Optional<Task> foundTask = taskService.getByTaskName(name);

        assertThat(foundTask).isNotNull();
        foundTask.ifPresent(value -> assertThat(value.getTaskName()).isEqualTo(task.getTaskName()));

    }







}