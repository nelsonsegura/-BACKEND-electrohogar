package com.app.movie.controller;

import com.app.movie.entities.Order;
import com.app.movie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    OrderService service;

    @GetMapping("")
    public Iterable<Order> getAll() {
        return service.getAll();
    }

    @PostMapping("")
    public Order create(@RequestBody Order order) {
        return service.create(order);
    }

    @PutMapping("/{id}/{status}")
    public void updateStatus(@PathVariable String id, @PathVariable String status) {
        service.updateStatus(id, status);
    }
}
