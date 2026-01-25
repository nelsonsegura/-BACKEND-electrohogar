package com.app.movie.controller;

import com.app.movie.dto.ResponseDto;
import com.app.movie.entities.Order;
import com.app.movie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService service;

    // ================= CLIENTE =================
    @PostMapping("")
    public ResponseDto create(@RequestBody Order order) {
        return service.create(order);
    }

    // ================= ADMIN =================
    @GetMapping("/client/{id}")
    public Iterable<Order> getByClient(@PathVariable String id){
        return service.getByClient(id);
    }



    @PutMapping("/{id}/{status}")
    public ResponseDto updateStatus(
            @PathVariable String id,
            @PathVariable String status) {

        return service.updateStatus(id, status);
    }
}

