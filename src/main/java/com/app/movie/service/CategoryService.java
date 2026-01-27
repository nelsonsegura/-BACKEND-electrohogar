package com.app.movie.service;

import com.app.movie.dto.ResponseDto;
import com.app.movie.entities.Category;
import com.app.movie.interfaces.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    ICategoryRepository repository;

    public Iterable<Category> getAll() {
        return repository.findAll();
    }

    public ResponseDto create(Category request) {
        ResponseDto response = new ResponseDto();

        String normalized = request.getName().trim();

        Optional<Category> existing =
                repository.findByNameIgnoreCase(normalized);

        if (existing.isPresent()) {
            response.status = false;
            response.message = "La categoría ya existe";
            return response;
        }

        request.setName(normalized);
        repository.save(request);

        response.status = true;
        response.message = "Categoría creada correctamente";
        response.id = request.getId();

        return response;
    }
}
