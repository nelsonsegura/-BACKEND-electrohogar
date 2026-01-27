package com.app.movie.controller;

import com.app.movie.dto.ResponseDto;
import com.app.movie.entities.Category;
import com.app.movie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping("")
    public Iterable<Category> getAll() {
        return service.getAll();
    }

    @PostMapping("")
    public ResponseDto create(@RequestBody Category category) {
        return service.create(category);
    }
}
