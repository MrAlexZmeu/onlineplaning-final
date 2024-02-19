package ru.alexzmeu.java.onlineplaning.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.alexzmeu.java.onlineplaning.objects.Status;
import ru.alexzmeu.java.onlineplaning.objects.Task;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBaseTaskProcess {
    private static final String BASE_URL = "jdbc:postgresql://localhost:5432/onlineplaning";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private final Logger logger = LogManager.getLogger(String.valueOf(DataBaseTaskProcess.class));

    public String createTask(Task task) {
        Statement statement = null;
        try (Connection connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tasks(\"taskId\",title,description,statusId,userId,deadline) VALUES (nextval('sequence_task_id'),?,?,?,?,?)", statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, task.getTitle());
                preparedStatement.setString(2, task.getDescription());
                preparedStatement.setInt(3, Status.NEW.getStatusId());
                preparedStatement.setInt(4, task.getUserId());
                preparedStatement.setDate(5, Date.valueOf(task.getDeadLine()));
                preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getString(1);
                }
            }

        } catch (SQLException e) {
            logger.error("Failed create task: " + e.getMessage());
        }
        return "Failed";
    }

    public String updateTask(Task task){
        try (Connection connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tasks SET title = ?,description = ?,statusId = ?,deadline = ? WHERE \"taskId\" = ?")) {
                preparedStatement.setString(1, task.getTitle());
                preparedStatement.setString(2, task.getDescription());
                preparedStatement.setInt(3, task.getStatusId());
                preparedStatement.setDate(4, Date.valueOf(task.getDeadLine()));
                preparedStatement.setInt(5, task.getTaskId());
                preparedStatement.executeUpdate();
                return "OK";
            }

        } catch (SQLException e) {
            logger.error("Failed create task: " + e.getMessage());
        }
        return "Failed";
    }

    public List<Task> getTaskByTaskID(int userId, int taskId){
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks WHERE userid=? AND \"taskId\"=?")) {
                preparedStatement.setInt(1, userId);
                preparedStatement.setInt(2, taskId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String title = resultSet.getString("title");
                        String description = resultSet.getString("description");
                        LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                        int statusId = resultSet.getInt("statusId");
                        tasks.add(new Task(taskId,title,description,userId,deadline,statusId));
                    }

                }
            }
        } catch (SQLException e) {
            logger.error("Failed check user: " + e.getMessage());
        }
        return tasks;
    }

    public List<Task> getAllTaskByUser(int userId){
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tasks WHERE userid=?")) {
                preparedStatement.setInt(1, userId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int taskId = resultSet.getInt("taskid");
                        String title = resultSet.getString("title");
                        String description = resultSet.getString("description");
                        LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
                        int statusId = resultSet.getInt("statusid");
                        tasks.add(new Task(taskId,title,description,userId,deadline,statusId));
                    }

                }
            }
        } catch (SQLException e) {
            logger.error("Failed check user: " + e.getMessage());
        }
        return tasks;
    }
}

