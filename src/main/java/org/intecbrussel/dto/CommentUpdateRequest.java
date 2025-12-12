package org.intecbrussel.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentUpdateRequest {
    @NotBlank
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
