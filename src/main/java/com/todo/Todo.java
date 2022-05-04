package com.todo;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Todo {
    private String name;
    private String details;
    private final LocalDateTime dayCreated;
    private TodoStatus status;

    public enum TodoStatus {
        ACTIVE, COMPLETED
    }

    public enum TodoLookupDateStrategy {
        BEFORE_DATE, SAME_DATE, AFTER_DATE
    }

    public Todo(String name, String details, LocalDateTime dayCreated) {
        this.name = name;
        this.details = details;
        this.dayCreated = dayCreated;
        status = TodoStatus.ACTIVE;
    }

    public Todo(String name, String details) {
        this(name, details, LocalDateTime.now(ZoneId.systemDefault()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getDayCreated() {
        return dayCreated;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "name='" + name + '\'' +
                ", description='" + details + '\'' +
                ", dayCreated=" + dayCreated +
                ", status=" + status +
                '}';
    }
}
