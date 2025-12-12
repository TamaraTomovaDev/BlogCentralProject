package org.intecbrussel.service;

import org.intecbrussel.dto.BlogPostCreateRequest;
import org.intecbrussel.dto.BlogPostResponse;
import org.intecbrussel.dto.BlogPostUpdateRequest;
import org.intecbrussel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogPostService {

    BlogPostResponse createPost(BlogPostCreateRequest request, User author);

    BlogPostResponse updatePost(Long id, BlogPostUpdateRequest request, User currentUser);

    void deletePost(Long id, User currentUser);

    BlogPostResponse likePost(Long id, User currentUser);

    Page<BlogPostResponse> getAllPosts(String sort, Pageable pageable);

    BlogPostResponse getPostById(Long id);
}
