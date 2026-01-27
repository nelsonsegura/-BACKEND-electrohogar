package com.app.movie.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document("movie")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    private String id;

    private String name;
    private String trailerLink;
    private String imageLink;
    private String description;
    private Double price;

    private Category category;   // 🔥 SOLO UNA
}

