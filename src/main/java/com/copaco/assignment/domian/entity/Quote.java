package com.copaco.assignment.domian.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "quote", schema = "public")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(nullable = false, length = 5000)
    private String quote;

    @Column(nullable = false, length = 500)
    private String author;

    @ElementCollection
    @CollectionTable(name = "quote_tags", joinColumns = @JoinColumn(name = "quote_id"))
    @Column(name = "tag")
    private List<String> tags;

    @ManyToMany(mappedBy = "likedQuotes", cascade = CascadeType.ALL)
    private Set<User> likedByUsers;

    @Column(nullable = false, length = 500)
    private String category;

    public void addUserToLikedByUsers(User user) {
        if (!likedByUsers.contains(user)) {
            likedByUsers.add(user);
            user.getLikedQuotes().add(this);
        } else {
            removeUserFromLikedByUsers(user);
        }
    }

    public void removeUserFromLikedByUsers(User user) {
        likedByUsers.remove(user);
        user.getLikedQuotes().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote1 = (Quote) o;
        return Objects.equals(id, quote1.id) && Objects.equals(quote, quote1.quote) && Objects.equals(author, quote1.author) && Objects.equals(tags, quote1.tags) && Objects.equals(likedByUsers, quote1.likedByUsers) && Objects.equals(category, quote1.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quote, author, tags, category);
    }
}