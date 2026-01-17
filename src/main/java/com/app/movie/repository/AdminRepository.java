/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.movie.repository;

import com.app.movie.entities.Admin;
import com.app.movie.interfaces.IAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepository {

    @Autowired
    IAdminRepository repository;

    public Iterable<Admin> getAll() {
        return repository.findAll();
    }

    // 🔴 MÉTODO CORREGIDO (YA NO LLAMA getAdminsByEmail)
    public Optional<Admin> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Optional<Admin> findById(String id) {
        return repository.findById(id);
    }

    public Optional<Admin> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Boolean existsById(String id) {
        return repository.existsById(id);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public Admin save(Admin admin) {
        return repository.save(admin);
    }
}
