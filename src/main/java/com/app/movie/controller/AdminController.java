/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.movie.controller;

import com.app.movie.dto.ReportAdminDto;
import com.app.movie.dto.ResponseDto;
import com.app.movie.entities.Admin;
import com.app.movie.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    AdminService service;

    @GetMapping("")
    public Iterable<Admin> get() {
        return service.get();
    }

    @GetMapping("/{id}")
    public Admin getById(@PathVariable String id) {
        return service.getById(id).orElse(null);
    }


    @GetMapping("/report")
    public ReportAdminDto getReport() {
        return service.getReport();
    }

    @PostMapping("")
    public ResponseDto createAdmin(@RequestBody Admin request) {

        Admin admin = new Admin();
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());

        return service.create(admin, request.getSecretKey());
    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Admin update(@RequestBody Admin request) {
        return service.update(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }

}
