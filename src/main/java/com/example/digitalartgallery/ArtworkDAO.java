package com.example.digitalartgallery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtworkDAO {
    private Connection connection;

    public ArtworkDAO() {
        connection = Database.getConnection();
    }

    public void uploadArtwork(Artwork artwork) {
        String sql = "INSERT INTO artwork (user_id, image_path, title) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, artwork.getUserId());
            pstmt.setString(2, artwork.getImagePath());
            pstmt.setString(3, artwork.getTitle());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Artwork> getUserArtworks(int userId) {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT * FROM artwork WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                artworks.add(new Artwork(rs.getInt("id"), rs.getInt("user_id"), rs.getString("image_path"), rs.getString("title")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artworks;
    }

    public List<Artwork> getAllArtworks() {
        List<Artwork> artworks = new ArrayList<>();
        String sql = "SELECT * FROM artwork";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                artworks.add(new Artwork(rs.getInt("id"), rs.getInt("user_id"), rs.getString("image_path"), rs.getString("title")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artworks;
    }
}
