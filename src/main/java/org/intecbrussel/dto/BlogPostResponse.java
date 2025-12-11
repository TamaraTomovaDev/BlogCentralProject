package org.intecbrussel.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BlogPostResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int likes;

    private Long authorId;
    private String authorUsername;

    private List<CommentResponse> comments;

    // getters/setters

    public Long getId() {return id;}
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public Long getAuthorId() {
        return authorId;
    }
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
    public String getAuthorUsername() {
        return authorUsername;
    }
    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
    public List<CommentResponse> getComments() {
        return comments;
    }
    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }
}
