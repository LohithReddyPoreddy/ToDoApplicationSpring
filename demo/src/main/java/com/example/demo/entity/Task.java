package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskId;

    private String taskName;

    private String taskData;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    private LocalDate deadLine;

    private LocalDate creationDate = LocalDate.now();

    private boolean completed;


    public void setTaskId(long taskId){
        this.taskId = taskId;
    }

    @PrePersist
    protected void onCreate(){
        this.creationDate = LocalDate.now();
    }

    // Getter and Setter for taskId
    public long getTaskId() {
        return taskId;
    }

    // Getter and Setter for taskName
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    // Getter and Setter for taskData
    public String getTaskData() {
        return taskData;
    }

    public void setTaskData(String taskData) {
        this.taskData = taskData;
    }

    // Getter and Setter for priority
    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    // Getter and Setter for deadLine
    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    // Getter and Setter for creationDate
    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    // Getter and Setter for completed
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Task(long taskId, String taskName, String taskData, TaskPriority priority, LocalDate deadLine, LocalDate creationDate, boolean completed) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskData = taskData;
        this.priority = priority;
        this.deadLine = deadLine;
        this.creationDate = creationDate != null ? creationDate : LocalDate.now(); // Default to current date if creationDate is null
        this.completed = completed;
    }
    public Task(){};

    public Task(String taskName, String taskData, TaskPriority priority, LocalDate deadLine, LocalDate creationDate, boolean completed) {
        this.taskName = taskName;
        this.taskData = taskData;
        this.priority = priority;
        this.deadLine = deadLine;
        this.creationDate = creationDate != null ? creationDate : LocalDate.now(); // Default to current date if creationDate is null
        this.completed = completed;
    }
    // PrePersist method to set the creation date before persisting the entity

}
