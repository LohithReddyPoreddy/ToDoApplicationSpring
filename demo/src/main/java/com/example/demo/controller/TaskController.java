package com.example.demo.controller;

import com.example.demo.entity.Task;
import com.example.demo.entity.TaskPriority;
import com.example.demo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Operation(summary = "create task",description = "Create a task and add it to database")
    @ApiResponse(responseCode = "201", description = "Successfully created the task")
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task createdTask = taskService.addTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
    @Operation(summary = "get task",description = "Get a task with specified task ID from database")
    @ApiResponse(responseCode = "200", description = "Successfully retrived the task")
    @ApiResponse(responseCode = "404", description = "task not found")
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable long id) {
        return taskService.getTask(id)
                .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "get all available tasks",description = "Get all tasks from database")
    @ApiResponse(responseCode = "200", description = "Successfully retrived all the tasks")
    @ApiResponse(responseCode = "404", description = "task not found")
    @GetMapping
    public ResponseEntity<List<Task>> getAllAvailableTasks(){
        List<Task> tasks = taskService.getAllTasks();
        if(tasks.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @Operation(summary = "update task",description = "Update the task retrived by ID from database")
    @ApiResponse(responseCode = "200", description = "Successfully updated the task")
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable long id, @RequestBody Task task){
        return new ResponseEntity<>(taskService.updateTask(id, task), HttpStatus.OK);
    }

    @Operation(summary = "delete task",description = "Delete a task based on taskID from database")
    @ApiResponse(responseCode = "204", description = "Successfully deleted the task")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable long id){
        try {
            taskService.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "get all tasks by priority",description = "List of all the tasks with given priority")
    @ApiResponse(responseCode = "200", description = "Successfully retrived all the tasks by priority")
    @ApiResponse(responseCode = "404", description = "task not found")
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getAllTasksByPriority(@PathVariable TaskPriority priority){
        List<Task> tasks = taskService.getAllTasksByPriority(priority);
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Operation(summary = "get all tasks by deadline",description = "Retrive list of all tasks with deadline date as specified")
    @ApiResponse(responseCode = "200", description = "Successfully retrived all the tasks by deadline")
    @ApiResponse(responseCode = "404", description = "task not found")
    @GetMapping("/deadline/{date}")
    public ResponseEntity<List<Task>> getAllTasksByDeadLine(@PathVariable String  date){
        try {
            List<Task> tasks = taskService.getAllTasksByDeadLine(LocalDate.parse(date,formatter));
            if (tasks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "get all tasks by creation date",description = "Retrive all the tasks by creation date")
    @ApiResponse(responseCode = "200", description = "Successfully retrived all the tasks by creation date")
    @ApiResponse(responseCode = "404", description = "task not found")
    @GetMapping("/creationdate/{date}")
    public ResponseEntity<List<Task>> getAllTasksByCreationDate(@PathVariable String date){
        try{
            List<Task> tasks = taskService.getAllTasksByCreationDate(LocalDate.parse(date,formatter));
            if(tasks.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "get all tasks by status",description = "Get all tasks from datebase that match the creation completed status")
    @ApiResponse(responseCode = "200", description = "Successfully retrived all the tasks by status of task ")
    @ApiResponse(responseCode = "404", description = "task not found")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getAllTasksByStatus(boolean status){
        try {
            List<Task> tasks = taskService.getAllByStatus(status);
            if(tasks.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tasks,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "get task by name",description = "retrive a task that match the given task name database")
    @ApiResponse(responseCode = "200", description = "Successfully retrived the task")
    @ApiResponse(responseCode = "404", description = "task not found")
    @GetMapping("/taskName/{name}")
    public ResponseEntity<Optional<Task>> getTaskByTaskName(@PathVariable String name){
        try {
            Optional<Task> task = taskService.getByTaskName(name);
            if(task.isPresent()){
                return new ResponseEntity<>(task, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
}
