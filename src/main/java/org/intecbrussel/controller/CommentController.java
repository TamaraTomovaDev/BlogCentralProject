package org.intecbrussel.controller;

import org.intecbrussel.model.Comment;
import org.intecbrussel.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public List<Comment> findAll(){
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment findById(@PathVariable Long id){
        return commentService.getCommentById(id);
    }

    @PostMapping
    public Comment save(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @PutMapping
    public Comment update(@RequestBody Comment comment){
        return commentService.updateComment(comment);
    }

    @DeleteMapping
    public void deleteById(@RequestBody Comment comment){
    }
}
