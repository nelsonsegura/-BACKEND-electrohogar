package com.app.movie.interfaces;

import com.app.movie.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ICategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findByNameIgnoreCase(String name);
}
