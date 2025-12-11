package org.intecbrussel.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
// PostBlog
@Entity
public class BlogPost { // eigenaar
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private Date createdAt;
    private int likes;
    @ManyToOne
    @JoinColumn(name= "user_id")
    private User author;
    @OneToMany(mappedBy = "id")
    private List<Comment> comments;

    public BlogPost() {
    }
    public BlogPost(String title, String content) {}

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
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

    @Override
    public String toString() {
        return "BlogPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", likes=" + likes +
                ", author=" + author +
                ", comments=" + comments +
                '}';
    }
}
