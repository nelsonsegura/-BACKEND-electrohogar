package com.app.movie.repository;

import com.app.movie.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByClientId(String clientId);
}
