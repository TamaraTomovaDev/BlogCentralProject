package org.intecbrussel.service;

import org.intecbrussel.dto.CommentCreateRequest;
import org.intecbrussel.dto.CommentResponse;
import org.intecbrussel.dto.CommentUpdateRequest;
import org.intecbrussel.mapper.CommentMapper;
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

    public CommentService(CommentRepository commentRepository, BlogPostRepository blogPostRepository, UserRepository userRepository, AuthService authService) {
        this.commentRepository = commentRepository;
        this.blogPostRepository = blogPostRepository;
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public List<CommentResponse> getAllComments() {
        return commentRepository.findAll()
                .stream()
                .map(CommentMapper::toResponse)
                .toList();
    }

    public CommentResponse getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        return CommentMapper.toResponse(comment);
    }

    public CommentResponse createComment(CommentCreateRequest request) {

        User user = authService.getAuthenticatedUser();

        BlogPost blogPost = blogPostRepository.findById(request.getBlogPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BlogPost not found"));

        Comment comment = new Comment();
        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setBlogPost(blogPost);

        Comment saved = commentRepository.save(comment);

        return CommentMapper.toResponse(saved);

    }

    public CommentResponse updateComment(Long id, CommentUpdateRequest request) {

        User user = authService.getAuthenticatedUser();

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot edit someone else's comment");
        }

        comment.setContent(request.getContent());

        Comment updated = commentRepository.save(comment);

        return CommentMapper.toResponse(updated);
    }

    public void deleteComment(Long id) {

        User user = authService.getAuthenticatedUser();

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        User commentAuthor = comment.getUser();
        User postAuthor = comment.getBlogPost().getAuthor();

        boolean isCommentOwner = commentAuthor.getId().equals(user.getId());
        boolean isPostOwner = postAuthor.getId().equals(user.getId());

        if (!isCommentOwner && !isPostOwner) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete someone else's comment");
        }

        commentRepository.delete(comment);
    }

}
