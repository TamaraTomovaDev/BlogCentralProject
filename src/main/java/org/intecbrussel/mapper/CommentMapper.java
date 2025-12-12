package org.intecbrussel.mapper;

import org.intecbrussel.dto.CommentResponse;
import org.intecbrussel.model.Comment;

public class CommentMapper {
    public static CommentResponse toResponse(Comment comment) {
        CommentResponse dto = new CommentResponse();

        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());

        dto.setUserId(comment.getUser().getId());
        dto.setUsername(comment.getUser().getUsername());

        if(comment.getBlogPost() != null && comment.getBlogPost().getId() != null) {
            dto.setBlogPostId(comment.getBlogPost().getId());
        }

        return dto;
    }
}
