package ru.alexzmeu.java.onlineplaning.objects;

import ru.alexzmeu.java.onlineplaning.database.DataBaseTaskProcess;

import java.time.LocalDate;
import java.util.List;

public class Task {
    private int taskId;
    private String title;
    private String description;
    private int userId;
    private LocalDate deadLine;
    private int statusId;

    public Task(int taskId, String title, String description, int userId, LocalDate deadLine, int statusId) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.deadLine = deadLine;
        this.statusId = statusId;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public int getStatusId() {
        return statusId;
    }

    public int createTask(){
        String result = new DataBaseTaskProcess().createTask(this);
        if (result.equals("Failed")) {
            return 0;
        }
        return Integer.parseInt(result);
    }

    public String updateTask(){
        return new DataBaseTaskProcess().updateTask(this);
    }

    public static List<Task> getAllTasks(int userId){
        return new DataBaseTaskProcess().getAllTaskByUser(userId);
    }

    public static List<Task> getTask(int userId, int taskId){
        return new DataBaseTaskProcess().getTaskByTaskID(userId, taskId);
    }
}
