package org.intecbrussel.service;

import org.intecbrussel.dto.*;
import org.intecbrussel.exception.*;
import org.intecbrussel.model.BlogPost;
import org.intecbrussel.repository.BlogPostRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.intecbrussel.model.User;

@Service
public class BlogPostServiceImpl implements BlogPostService {

    private final BlogPostRepository blogPostRepository;

    public BlogPostServiceImpl(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @Override
    public BlogPostResponse createPost(BlogPostCreateRequest request, User author) {
        BlogPost post = new BlogPost();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(author);

        BlogPost saved = blogPostRepository.save(post);
        return toResponse(saved);
    }

    @Override
    public BlogPostResponse getPostById(Long id) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blogpost with id " + id + " not found"));

        return toResponse(post);
    }

    @Override
    public BlogPostResponse updatePost(Long id, BlogPostUpdateRequest request, User currentUser) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blogpost with id " + id + " not found"));

        if (!post.getAuthor().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("You are not allowed to edit this blogpost");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        BlogPost saved = blogPostRepository.save(post);
        return toResponse(saved);
    }

    @Override
    public void deletePost(Long id, User currentUser) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blogpost with id " + id + " not found"));

        if (!post.getAuthor().getId().equals(currentUser.getId())) {
            throw new ForbiddenException("You are not allowed to delete this blogpost");
        }

        blogPostRepository.delete(post);
    }

    @Override
    public BlogPostResponse likePost(Long id, User currentUser) {
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blogpost with id " + id + " not found"));

        post.setLikes(post.getLikes() + 1);
        BlogPost saved = blogPostRepository.save(post);

        return toResponse(saved);
    }

    @Override
    public Page<BlogPostResponse> getAllPosts(String sort, Pageable pageable) {
        Page<BlogPost> posts;

        switch (sort.toLowerCase()) {
            case "oldest":
                posts = blogPostRepository.findAllByOrderByCreatedAtAsc(pageable);
                break;
            case "popular":
                posts = blogPostRepository.findAllByOrderByLikesDesc(pageable);
                break;
            default:
                posts = blogPostRepository.findAllByOrderByCreatedAtDesc(pageable);
        }

        return posts.map(this::toResponse);
    }

    private BlogPostResponse toResponse(BlogPost post) {
        BlogPostResponse dto = new BlogPostResponse();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setCreatedAt(post.getCreatedAt());
        dto.setLikes(post.getLikes());

        if (post.getAuthor() != null) {
            dto.setAuthorId(post.getAuthor().getId());
            dto.setAuthorUsername(post.getAuthor().getUsername());
        }

        //comment
        if (post.getComments() != null) {
            dto.setComments(
                    post.getComments().stream()
                            .map(comment -> {
                                CommentResponse c = new CommentResponse();
                                c.setId(comment.getId());
                                c.setContent(comment.getContent());
                                c.setCreatedAt(comment.getCreatedAt());
                                c.setAuthorId(comment.getAuthor().getId());
                                c.setAuthorUsername(comment.getAuthor().getUsername());
                                return c;
                            })
                            .toList()
            );
        }

        return dto;
    }
    }

