package org.intecbrussel.dto;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String content;
    private Long postId;
    private Long userId;

    private LocalDateTime createdAt;
    private Long AuthorId;
    private String authorUsername;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getPostId() { return postId; }
    public void setPostId(Long postId) { this.postId = postId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(Long authorId) {
        AuthorId = authorId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
