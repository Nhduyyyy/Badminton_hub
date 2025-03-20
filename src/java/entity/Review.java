/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Timestamp;


/**
 *
 * @author PC Windows 10
 */
public class Review {
      private String username;
    private int rating;
    private String comment;
    private Timestamp createdAt;

    public Review(String username, int rating, String comment, Timestamp createdAt) {
        this.username = username;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }
  

    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Review{" + "username=" + username + ", rating=" + rating + ", comment=" + comment + ", createdAt=" + createdAt + '}';
    }
    
}
