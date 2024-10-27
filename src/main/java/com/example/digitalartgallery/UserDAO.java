package com.example.digitalartgallery;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
    public boolean registerUser(String username, String email, String password) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            Object BCrypt = new Object();
            String hashedPassword = String.valueOf(BCrypt.hashCode(password, BCrypt.equals()));
            preparedStatement.setString(3, hashedPassword); // Store hashed password
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("password");
                if (BCrypt.checkpw(password, hashedPassword)) {
                    return new User(resultSet.getInt("id"), resultSet.getString("username"), hashedPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if login fails
    }
}
