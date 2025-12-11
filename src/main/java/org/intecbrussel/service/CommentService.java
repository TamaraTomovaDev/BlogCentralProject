package org.intecbrussel.service;

import org.intecbrussel.model.BlogPost;
import org.intecbrussel.model.Comment;
import org.intecbrussel.model.User;
import org.intecbrussel.repository.BlogPostRepository;
import org.intecbrussel.repository.CommentRepository;
import org.intecbrussel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BlogPostRepository blogPostRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthService authService;


    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public Comment addComment(Long blogPostId,String content) {
        // Haal ingelogde gebruiker op met "SecurityContextHolder"
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        if(auth==null || !auth.isAuthenticated()|| auth instanceof AnonymousAuthenticationToken) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"User not logged in");
        }
        // Haal user uit database
        String username = auth.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Username not found"));

        // Haal BlogPost uit
        BlogPost blogPost = blogPostRepository.findById(blogPostId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Post not found"));

        // Comment maken
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setContent(content);
        comment.setBlogPost(blogPost);
        comment.setCreatedAt(LocalDateTime.now());
        // na alles opslaan
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long commentId,String newContent) {

        User user = authService.getAuthenticatedUser(); // haal ingelogde user

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Comment not found"));

        if(!comment.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You cannot edit someone else's comment");
        }
        comment.setContent(newContent);
        return commentRepository.save(comment);
    }

    public void deleteCommentById(Long id) {

        User user = authService.getAuthenticatedUser(); // Haal ingeloggede user

        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Comment not found"));
        User commentAuthor = comment.getUser();
        User postAuthor = comment.getBlogPost().getAuthor(); // auteur v/d blogpost

        boolean isCommentOwner = commentAuthor.getId().equals(postAuthor.getId());
        boolean isPostOwner = postAuthor.getId().equals(user.getId());

        if(!isCommentOwner && !isPostOwner) { // alleen de auteur van comment of blogpost mag verwijderen
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You cannot delete someone else's comment");
        }
        commentRepository.delete(comment);
    }

}
