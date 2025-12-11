package org.intecbrussel.service;

import org.intecbrussel.dto.BlogPostCreateRequest;
import org.intecbrussel.dto.BlogPostResponse;
import org.intecbrussel.dto.BlogPostUpdateRequest;
import org.intecbrussel.model.BlogPost;
import org.intecbrussel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogPostService {
    BlogPostResponse createPost(BlogPostCreateRequest request, User currentUser);     // STORY08

    BlogPostResponse updatePost(Long id, BlogPostUpdateRequest request, User currentUser); // STORY09

    void deletePost(Long id, User currentUser);                                       // STORY10

    Page<BlogPostResponse> getAllPosts(String sort, Pageable pageable);               // STORY11

    BlogPostResponse getPostById(Long id);                                            // STORY12

    BlogPostResponse likePost(Long id, User currentUser);                             // STORY13
}
