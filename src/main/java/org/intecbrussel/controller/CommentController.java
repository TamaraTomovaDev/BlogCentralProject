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

    @PostMapping("/{id}")
    public Comment save(@PathVariable Long id,@RequestBody String content){
        return commentService.addComment(id,content);
    }

    @PutMapping("/{id}")
    public Comment update(@PathVariable Long id,@RequestBody String content){
        return commentService.updateComment(id,content);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        commentService.deleteCommentById(id);
    }

}
