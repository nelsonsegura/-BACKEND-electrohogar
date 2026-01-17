/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.movie.service;

import com.app.movie.dto.ReportAdminDto;
import com.app.movie.dto.ResponseDto;
import com.app.movie.entities.Admin;
import com.app.movie.entities.Client;
import com.app.movie.repository.AdminRepository;
import com.app.movie.repository.ClientRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AdminService {

    private final String ADMIN_REGISTERED="el correo ya esta registrado  o falta ingresar un dato";
    private final String ADMIN_SUCCESS="el COMERCIANTE   se registró correctamente";
    @Autowired
    AdminRepository repository;

    @Autowired
    private ClientRepository clientRepository;


    @Value("${admin.secret.key}")
    private String adminSecretKey;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Iterable<Admin> get() {
        Iterable<Admin> response = repository.getAll();
        return response;
    }

    public Optional<Admin> getByCredential(String credential) {
        String pair = new String(Base64.decodeBase64(credential.substring(6)));
        String email = pair.split(":")[0];
        String pass = pair.split(":")[1];

        Optional<Admin> admin = repository.findByEmail(email);
        if(!matchPass(pass,admin.get().getPassword())){
            return null;
        }
        return admin;
    }

    public ReportAdminDto getReport() {
        Optional<Admin> admin = repository.findById("6380442df71ad74770fc57e1");
        ReportAdminDto reportAdminDto = new ReportAdminDto();
        reportAdminDto.birthDate = admin.get().getBirthDate();
        reportAdminDto.email = admin.get().getEmail();
        reportAdminDto.id = admin.get().getId();
        return reportAdminDto;
    }

    public ResponseDto create(Admin request, String secretKey) {

        ResponseDto response = new ResponseDto();

        // 🔐 VALIDAR CLAVE SECRETA DE COMERCIANTE
        if (secretKey == null || !adminSecretKey.equals(secretKey)) {
            response.status = false;
            response.message = "Clave de comerciante inválida";
            return response;
        }

        // 🔴 VALIDACIÓN CRUZADA: ¿EXISTE COMO CLIENTE?
        List<Client> clients = clientRepository.getByEmail(request.getEmail());
        if (!clients.isEmpty()) {
            response.status = false;
            response.message = "Este correo ya está registrado como cliente";
            return response;
        }


        // 🔎 VALIDAR SI YA EXISTE COMO ADMIN
        Optional<Admin> adminOpt = repository.getByEmail(request.getEmail());
        if (adminOpt.isPresent()) {
            response.status = false;
            response.message = ADMIN_REGISTERED;
            return response;
        }

        // 🔐 SEGURIDAD
        request.setSecretKey(null);                 // ❌ NO guardar la clave
        request.setPassword(encrypt(request.getPassword())); // 🔐 cifrar contraseña

        repository.save(request);

        response.status = true;
        response.message = ADMIN_SUCCESS;
        response.id = request.getId();

        return response;
    }



    public Admin update(Admin admin) {
        Admin adminToUpdate = new Admin();

        Optional<Admin> currentAdmin = repository.findById(admin.getId());
        if (!currentAdmin.isEmpty()) {
            adminToUpdate = admin;
            adminToUpdate = repository.save(adminToUpdate);
        }
        return adminToUpdate;
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
