package org.intecbrussel.dto;

import jakarta.validation.constraints.NotBlank;

public class BlogPostUpdateRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;
    }
}
