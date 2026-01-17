/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.movie.service;

import com.app.movie.dto.AuthDto;
import com.app.movie.dto.AuthResponseDto;
import com.app.movie.dto.ReportClientDto;
import com.app.movie.entities.Admin;
import com.app.movie.entities.Client;
import com.app.movie.interfaces.IAdminRepository;
import com.app.movie.repository.AdminRepository;
import com.app.movie.repository.ClientRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AdminRepository adminRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthResponseDto check(AuthDto request) {

        AuthResponseDto response = new AuthResponseDto();

        if (request.user == null || request.user.isEmpty()
                || request.password == null || request.password.isEmpty()
                || request.role == null || request.role.isEmpty()) {
            return response;
        }

        if ("admin".equalsIgnoreCase(request.role)) {

            // ❌ NO permitir login admin si existe como cliente
            if (clientRepository.findByEmail(request.user).isPresent()) {
                return response;
            }

            return loginAdmin(request);

        } else if ("client".equalsIgnoreCase(request.role)) {

            // ❌ NO permitir login cliente si existe como admin
            if (adminRepository.findByEmail(request.user).isPresent()) {
                return response;
            }

            return loginClient(request);
        }

        return response;
    }


    // ================= CLIENT =================
    private AuthResponseDto loginClient(AuthDto request) {

        AuthResponseDto response = new AuthResponseDto();

        Optional<Client> client = clientRepository.findByEmail(request.user);

        if (client.isPresent() &&
                passwordEncoder.matches(request.password, client.get().getPassword())) {

            response.id = client.get().getId();
            response.name = client.get().getName() + " " + client.get().getLastName();
            response.email = client.get().getEmail();
            response.token = getToken(request.user, request.password);
            response.role = "client"; // 👈 AQUÍ
        }

        return response;
    }


    // ================= ADMIN =================
    private AuthResponseDto loginAdmin(AuthDto request) {

        AuthResponseDto response = new AuthResponseDto();

        Optional<Admin> admin = adminRepository.findByEmail(request.user);

        if (admin.isPresent() &&
                passwordEncoder.matches(request.password, admin.get().getPassword())) {

            response.id = admin.get().getId();
            response.name = admin.get().getName();
            response.email = admin.get().getEmail();
            response.token = getToken(request.user, request.password);
            response.role = "admin"; // 👈 AQUÍ
        }

        return response;
    }


    // ================= TOKEN =================
    private String getToken(String user, String pass) {
        String tokenString = user + ":" + pass;
        byte[] bytesEncoded = Base64.encodeBase64(tokenString.getBytes());
        return new String(bytesEncoded);
    }
}

