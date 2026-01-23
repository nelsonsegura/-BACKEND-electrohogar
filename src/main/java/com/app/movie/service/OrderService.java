package com.app.movie.service;

import com.app.movie.dto.ResponseDto;
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
    public ResponseDto create(Order order){

        ResponseDto response = new ResponseDto();

        // 🛑 carrito vacío
        if(order.getItems() == null || order.getItems().isEmpty()){
            response.status = false;
            response.message = "El carrito está vacío";
            return response;
        }

        // 🛑 datos obligatorios
        if(order.getClientName() == null || order.getEmail() == null ||
                order.getPhone() == null || order.getAddress() == null ||
                order.getPaymentMethod() == null){
            response.status = false;
            response.message = "Datos incompletos";
            return response;
        }

        order.setStatus("PENDING");
        order.setDate(LocalDate.now().toString());

        repository.save(order);

        response.status = true;
        response.message = "Pedido creado correctamente";
        return response;
    }


    public void updateStatus(String id, String status) {
        Order order = repository.findById(id).orElse(null);
        if (order != null) {
            order.setStatus(status);
            repository.save(order);
        }
    }
}
