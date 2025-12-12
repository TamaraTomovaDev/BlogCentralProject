package org.intecbrussel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentCreateRequest {
    @NotBlank
    private String content;

    @NotNull
    private Long userId;

    @NotNull
    private Long blogPostId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(Long blogPostId) {
        this.blogPostId = blogPostId;
    }
}
