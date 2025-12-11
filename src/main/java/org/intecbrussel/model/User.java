package org.intecbrussel.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(nullable = false, unique = true)
    private String username; // verplicht + uniek

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; // gehashed opslaan!

    private String avatarUrl;

    // adresgegevens
    private String street;
    private String houseNr;
    private String city;
    private String zip;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    // relaties (worden door andere modules gevuld)
    @OneToMany(mappedBy = "author")
    private List<BlogPost> blogPosts;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    public User() {}

    // getters & setters (generator van IDE aanbevolen)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getHouseNr() { return houseNr; }
    public void setHouseNr(String houseNr) { this.houseNr = houseNr; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getZip() { return zip; }
    public void setZip(String zip) { this.zip = zip; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public List<BlogPost> getBlogPosts() { return blogPosts; }
    public void setBlogPosts(List<BlogPost> blogPosts) { this.blogPosts = blogPosts; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
}
