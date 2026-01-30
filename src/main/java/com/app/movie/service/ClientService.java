/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.movie.service;

import com.app.movie.dto.ReportClientDto;
import com.app.movie.dto.ResponseDto;
import com.app.movie.entities.Admin;
import com.app.movie.entities.Client;

import com.app.movie.repository.AdminRepository;
import com.app.movie.repository.ClientRepository;


import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Andres
 */
@Service
public class ClientService {

    private final String CLIENT_REGISTERED="el correo ya esta registrado  o falta ingresar un dato";
    private final String CLIENT_SUCCESS="el cliente  se registró correctamente";
    @Autowired
    ClientRepository repository;

    @Autowired
    AdminRepository adminRepository;


    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Iterable<Client> get() {
        Iterable<Client> response = repository.getAll();
        return response;
    }

    public Optional<Client> getById(String id) {
        return repository.findById(id);
    }


    public Optional<Client> getByCredential(String credential) {
        String pair = new String(Base64.decodeBase64(credential.substring(6)));
        String email = pair.split(":")[0];
        String pass = pair.split(":")[1];

        Optional<Client> client = repository.findByEmail(email);
        if(!matchPass(pass,client.get().getPassword())){
            return null;
        }
        return client;
    }

    public ReportClientDto getReport() {
        Optional<Client> client = repository.findById("6380442df71ad74770fc57e1");
        ReportClientDto reportClientDto = new ReportClientDto();
        reportClientDto.birthDate = client.get().getBirthDate();
        reportClientDto.email = client.get().getEmail();
        reportClientDto.id = client.get().getId();
        return reportClientDto;
    }

    public ResponseDto create(Client request) {

        ResponseDto response = new ResponseDto();

        // 🔴 VALIDACIÓN CRUZADA: ¿EXISTE COMO ADMIN?
        Optional<Admin> adminOpt = adminRepository.findByEmail(request.getEmail());
        if (adminOpt.isPresent()) {
            response.status = false;
            response.message = "Este correo ya está registrado como comerciante";
            return response;
        }

        // 🔐 CIFRAR CONTRASEÑA
        request.setPassword(encrypt(request.getPassword()));

        // 🔴 VALIDACIÓN NORMAL: CLIENTE
        List<Client> clients = repository.getByEmail(request.getEmail());
        if (!clients.isEmpty()) {
            response.status = false;
            response.message = CLIENT_REGISTERED;
        } else {
            repository.save(request);
            response.status = true;
            response.message = CLIENT_SUCCESS;
            response.id = request.getId();
        }

        return response;
    }


    public Client update(Client client) {
        Client clientToUpdate = new Client();

        Optional<Client> currentClient = repository.findById(client.getId());
        if (!currentClient.isEmpty()) {
            clientToUpdate = client;
            clientToUpdate = repository.save(clientToUpdate);
        }
        return clientToUpdate;
    }

    public Boolean delete(String id) {
        repository.deleteById(id);
        Boolean deleted = true;
        return deleted;
    }

    private String encrypt(String pass){
        return this.passwordEncoder.encode(pass);
    }

    private Boolean matchPass(String pass,String dbPass){
        return this.passwordEncoder.matches(pass,dbPass);
    }
}
