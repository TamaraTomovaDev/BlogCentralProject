package org.intecbrussel.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    // ---- Relaties ----

    // Elke post heeft één auteur (User)
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    // Een post kan veel comments hebben
    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    // ---- Constructors ----

    public BlogPost() {
        this.createdAt = LocalDateTime.now();
    }

    public BlogPost(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    // ---- Getters & Setters ----

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
