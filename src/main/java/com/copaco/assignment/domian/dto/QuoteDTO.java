package com.copaco.assignment.domian.dto;

import com.copaco.assignment.domian.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteDTO implements Serializable {
    private  Long id;

    @JsonProperty("Quote")
    private String quote;

    @JsonProperty("Author")
    private String author;

    @JsonProperty("Tags")
    private List<String> tags;

    @JsonProperty("Category")
    private String category;

    private int likes;

    public void setLikedByUsers(List<User> likedByUsers) {
        this.likes = likedByUsers.size();
    }

}
