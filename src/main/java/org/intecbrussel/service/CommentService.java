package org.intecbrussel.service;

import org.intecbrussel.model.Comment;
import org.intecbrussel.repository.CommentRepository;
import org.intecbrussel.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
//    @Autowired
//    private PostRepository postRepository;


    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

//    public Comment addComment(Long postId,String content) {
//        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
//        Comment comment = new Comment();
//        comment.setContent(content);
//        return commentRepository.save(comment);
//    }

    public Comment updateComment(Long commentId,String newContent) {
        User user = AuthService.getAuthenticatedUser();
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(newContent);
            return commentRepository.save(comment);
        }else{
            return null;
        }

    }
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

}
