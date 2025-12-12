//package org.intecbrussel.controller;
//
//import jakarta.validation.Valid;
//import org.springframework.security.core.Authentication;
//import org.intecbrussel.dto.*;
//import org.intecbrussel.security.CustomUserDetails;
//import org.intecbrussel.service.BlogPostService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.data.domain.*;
//import org.intecbrussel.model.User;
//
//import java.net.URI;
//
//@RestController
//@RequestMapping("/blogposts")
//public class BlogPostController {
//    private final BlogPostService blogPostService;
//
//    public BlogPostController(BlogPostService blogPostService) {
//        this.blogPostService = blogPostService;
//    }
//
//    // STORY11: view all
//    @GetMapping
//    public Page<BlogPostResponse> getAllPosts(
//            @RequestParam(defaultValue = "newest") String sort,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "6") int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        return blogPostService.getAllPosts(sort, pageable);
//    }
//
//    // STORY12: details
//    @GetMapping("/{postId}")
//    public BlogPostResponse getPostDetails(@PathVariable Long postId) {
//        return blogPostService.getPostById(postId);
//    }
//
//    // STORY08: create
//    @PostMapping
//    public ResponseEntity<BlogPostResponse> createPost(
//            @Valid @RequestBody BlogPostCreateRequest request) {
//
//        User currentUser = getCurrentUser();
//        BlogPostResponse created = blogPostService.createPost(request, currentUser);
//
//        return ResponseEntity
//                .created(URI.create("/blogposts/" + created.getId()))
//                .body(created);
//    }
//
//    // STORY09: edit
//    @PutMapping("/{postId}")
//    public BlogPostResponse updatePost(
//            @PathVariable Long postId,
//            @Valid @RequestBody BlogPostUpdateRequest request) {
//
//        User currentUser = getCurrentUser();
//        return blogPostService.updatePost(postId, request, currentUser);
//    }
//
//    // STORY10: delete
//    @DeleteMapping("/{postId}")
//    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
//        User currentUser = getCurrentUser();
//        blogPostService.deletePost(postId, currentUser);
//        return ResponseEntity.noContent().build();
//    }
//
//    // STORY13: like
//    @PostMapping("/{postId}/like")
//    public BlogPostResponse likePost(@PathVariable Long postId) {
//        User currentUser = getCurrentUser();
//        return blogPostService.likePost(postId, currentUser);
//    }
//
//    private User getCurrentUser() {
//        // TODO: UserDetails
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails custom = (CustomUserDetails) auth.getPrincipal();
//        return custom.getUser();
//    }
//}
