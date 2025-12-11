package org.intecbrussel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blog_posts")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                 // PK

    @Column(nullable = false)
    private String title;            // STORY11 + 12

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;          // STORY08 + 09 + 12

    @Column(nullable = false)
    private LocalDateTime createdAt; // datum/tijd

    @Column(nullable = false)
    private int likes = 0;           // aantal likes (STORY13)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnoreProperties({"blogPosts", "comments"})
    private User author;             // relatie naar User

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("blogPost")
    private List<Comment> comments = new ArrayList<>(); // list of comments

    public BlogPost() {
    }

    public BlogPost(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    //getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
