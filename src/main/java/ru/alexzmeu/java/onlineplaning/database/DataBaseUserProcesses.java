package ru.alexzmeu.java.onlineplaning.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class DataBaseUserProcesses {
    private static final String BASE_URL = "jdbc:postgresql://localhost:5432/onlineplaning";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private final Logger logger = LogManager.getLogger(String.valueOf(DataBaseUserProcesses.class));

    public String getIdUserByLoginAndPassword(String login, String password) {

        try (Connection connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT \"userId\",password FROM users WHERE login=?")) {
                preparedStatement.setString(1, login);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        if (BCrypt.checkpw(password, resultSet.getString(2))) {
                            return resultSet.getString(1);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Failed check user: " + e.getMessage());
        }
        return "Failed";
    }

    public String createNewUser(String login, String password) {
        String salt = BCrypt.gensalt();
        String hashPass = BCrypt.hashpw(password, salt);
        try (Connection connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (nextval('sequence_user_id'),?,?)")) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, hashPass);
                preparedStatement.executeUpdate();
                return "OK";
            }

        } catch (SQLException e) {
            logger.error("Failed create user: " + e.getMessage());
        }
        return "Failed";
    }

    public boolean isLoginAlreadyExist(String login) {
        try (Connection connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT login FROM users WHERE login=?")) {
                preparedStatement.setString(1, login);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Failed find user: " + e.getMessage());
        }
        return false;
    }

}
