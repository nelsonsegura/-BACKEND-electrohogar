/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.movie.service;

import com.app.movie.dto.ResponseDto;
import com.app.movie.entities.Movie;
import com.app.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


import com.app.movie.entities.Category;
import com.app.movie.interfaces.ICategoryRepository;



/**
 *
 * @author Andres
 */
@Service
public class MovieService {

    private final String MOVIE_REGISTERED="el producto ya se encuentra registrado";
    private final String MOVIE_SUCCESS="el producto se registró correctamente";

    @Autowired
    MovieRepository repository;
    @Autowired
    ICategoryRepository categoryRepository;


    public Iterable<Movie> get() {
        Iterable<Movie> response = repository.getAll();
        return response;
    }

    public Optional<Movie> getById(String id) {
        Optional<Movie> response = repository.findById(id);
        return response;
    }

    public ResponseDto create(Movie request) {

        ResponseDto response = new ResponseDto();

        // Validar nombre duplicado
        List<Movie> movies = repository.getByName(request.getName());
        if(movies.size() > 0){
            response.status = false;
            response.message = MOVIE_REGISTERED;
            return response;
        }

        // Validar categoría
        if(request.getCategory() == null || request.getCategory().getId() == null){
            response.status = false;
            response.message = "Debe seleccionar una categoría";
            return response;
        }

        Category category = categoryRepository
                .findById(request.getCategory().getId())
                .orElse(null);

        if(category == null){
            response.status = false;
            response.message = "Categoría no válida";
            return response;
        }

        // Insertar categoría real
        request.setCategory(category);

        repository.save(request);

        response.status = true;
        response.message = MOVIE_SUCCESS;
        response.id = request.getId();

        return response;
    }

    public ResponseDto update(Movie request) {

        ResponseDto response = new ResponseDto();

        Optional<Movie> movieOpt = repository.findById(request.getId());

        if (movieOpt.isEmpty()) {
            response.status = false;
            response.message = "Producto no encontrado";
            return response;
        }

        // Validar categoría
        if(request.getCategory() == null || request.getCategory().getId() == null){
            response.status = false;
            response.message = "Debe seleccionar una categoría";
            return response;
        }

        Category category = categoryRepository
                .findById(request.getCategory().getId())
                .orElse(null);

        if(category == null){
            response.status = false;
            response.message = "Categoría no válida";
            return response;
        }

        request.setCategory(category);

        repository.save(request);

        response.status = true;
        response.message = "Producto actualizado correctamente";

        return response;
    }



    public Boolean delete(String id) {
        repository.deleteById(id);
        Boolean deleted = true;
        return deleted;
    }
}
