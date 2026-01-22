package com.app.movie.service;

import com.app.movie.entities.Order;
import com.app.movie.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderService {

    @Autowired
    OrderRepository repository;

    public Iterable<Order> getAll() {
        return repository.findAll();
    }

    public Order create(Order order) {
        order.setStatus("PENDIENTE");
        order.setDate(LocalDate.now().toString());
        return repository.save(order);
    }

    public void updateStatus(String id, String status) {
        Order order = repository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(status);
            repository.save(order);
        }
    }
}
