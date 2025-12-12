package org.intecbrussel.controller;

import org.intecbrussel.dto.CommentCreateRequest;
import org.intecbrussel.dto.CommentResponse;
import org.intecbrussel.dto.CommentUpdateRequest;
import org.intecbrussel.model.Comment;
import org.intecbrussel.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> getAll() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public CommentResponse getOne(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    public CommentResponse create(@RequestBody CommentCreateRequest request) {
        return commentService.createComment(request);
    }

    @PutMapping("/{id}")
    public CommentResponse update(@PathVariable Long id, @RequestBody CommentUpdateRequest request) {
        return commentService.updateComment(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

}
