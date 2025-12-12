package org.intecbrussel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentCreateRequest {
    @NotBlank
    private String content;

    @NotNull
    private Long postId;

    @NotNull
    private Long userId;

    public String geContent(){
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Long getPostId() {
        return postId;
    }
    public void setPostId(Long postId) {
        this.postId = postId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
