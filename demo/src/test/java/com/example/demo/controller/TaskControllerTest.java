package com.example.demo.controller;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;
import com.example.demo.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Task task;

    @Autowired
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTaskName("ToDo");
        task.setTaskData("Build a todo application using spring");
        task.setPriority(TaskPriority.HIGH);
        task.setDeadLine(LocalDate.now());
        task.setCreationDate(LocalDate.now());
        task.setCompleted(false);
    }

    @Test
    public void testCreateTask() throws Exception {
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.taskName").value(task.getTaskName()))
                .andExpect(jsonPath("$.taskData").value(task.getTaskData()))
                .andExpect(jsonPath("$.priority").value(task.getPriority().name()))
                .andExpect(jsonPath("$.completed").value(task.isCompleted()));
    }


    @Test
    @Transactional
    public void testGetTaskById() throws Exception {
        // Save the task and use its ID
        Task savedTask = taskService.addTask(task);

        mockMvc.perform(get("/api/tasks/{id}", savedTask.getTaskId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value((int) savedTask.getTaskId()))
                .andExpect(jsonPath("$.taskName").value(savedTask.getTaskName()));
    }

    @Test
    public void testNoTaskReturnedWhenNoTaskId() throws Exception{
        long nonExistingTaskId = 999;
        mockMvc.perform(get("/api/tasks/{id}",nonExistingTaskId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    @Transactional
    public void testGetAllAvailableTasks() throws Exception{
        Task task1 = new Task("Task2","This is task 2",TaskPriority.HIGH,LocalDate.now(),LocalDate.now(),false);
        taskService.addTask(task);
        taskService.addTask(task1);

        mockMvc.perform(get("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].taskName").value("ToDo"))
                .andExpect(jsonPath("$[1].taskName").value("Task2"));
    }

    @Test
    @Transactional
    public void testUpdateTask() throws Exception{
        Task task1 = new Task("Task2","This is task 2",TaskPriority.HIGH,LocalDate.now(),LocalDate.now(),false);

        Task added = taskService.addTask(task);

        mockMvc.perform(put("/api/tasks/{id}",added.getTaskId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value((int) added.getTaskId()))
                .andExpect(jsonPath("$.taskName").value("Task2"));
    }

    @Test
    @Transactional
    public void testDeleteTask() throws Exception{
        Task added = taskService.addTask(task);

        mockMvc.perform(delete("/api/tasks/{id}",added.getTaskId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    public void testGetAllTasksByPriority() throws Exception{
        Task task1 = new Task("Task1","This is task 2",TaskPriority.HIGH,LocalDate.now(),LocalDate.now(),false);
        Task task2 = new Task("Task2","This is task 2",TaskPriority.LOW,LocalDate.now(),LocalDate.now(),false);
        TaskPriority priority = TaskPriority.HIGH;
        taskService.addTask(task);
        taskService.addTask(task1);
        taskService.addTask(task2);

        mockMvc.perform(get("/api/tasks/priority/{priority}",priority)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].priority").value("HIGH"));
    }

    @Test
    @Transactional
    public void testGetAllTasksByDeadLine() throws Exception{
        Task task1 = new Task("Task1","This is task 2",TaskPriority.HIGH,LocalDate.now(),LocalDate.now(),false);
        Task task2 = new Task("Task2","This is task 2",TaskPriority.LOW, LocalDate.parse("2024-12-22"),LocalDate.now(),false);
        LocalDate date = LocalDate.parse("2024-12-14");
        taskService.addTask(task);
        taskService.addTask(task1);
        taskService.addTask(task2);

        mockMvc.perform(get("/api/tasks/deadline/{date}",date)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].deadLine").value("2024-12-14"));
    }

    @Test
    @Transactional
    public void testGetAllTasksByCreationDate() throws Exception{
        Task task1 = new Task("Task1","This is task 2",TaskPriority.HIGH,LocalDate.now(),LocalDate.now(),false);
        Task task2 = new Task("Task2","This is task 2",TaskPriority.LOW, LocalDate.parse("2024-12-22"),LocalDate.now(),false);
        LocalDate date = LocalDate.now();
        taskService.addTask(task);
        taskService.addTask(task1);
        taskService.addTask(task2);

        mockMvc.perform(get("/api/tasks/creationdate/{date}",date)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].creationDate").value((LocalDate.now()).toString()));
    }

    @Test
    @Transactional
    public void testGetAllTasksByStatus() throws Exception{
        Task task1 = new Task("Task1","This is task 2",TaskPriority.HIGH,LocalDate.now(),LocalDate.now(),false);
        Task task2 = new Task("Task2","This is task 2",TaskPriority.LOW, LocalDate.parse("2024-12-22"),LocalDate.now(),true);
        boolean status = false;
        taskService.addTask(task);
        taskService.addTask(task1);
        taskService.addTask(task2);

        mockMvc.perform(get("/api/tasks/status/{status}",status)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].completed").value(false));
    }

    @Test
    @Transactional
    public void testGetTaskByName() throws Exception{
        Task newTask = taskService.addTask(task);
        String noTaskName = "no_name";

        mockMvc.perform(get("/api/tasks/taskName/{name}",newTask.getTaskName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskName").value(newTask.getTaskName()));

        mockMvc.perform(get("/api/tasks/taskName/{name}",noTaskName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }




}
