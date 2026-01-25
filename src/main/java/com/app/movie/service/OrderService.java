package com.app.movie.service;

import com.app.movie.dto.ResponseDto;
import com.app.movie.entities.Order;
import com.app.movie.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> getByClient(String clientId){
        return repository.findByClientId(clientId);
    }


    // ================== CLIENTE ==================
    public ResponseDto create(Order order){

        ResponseDto response = new ResponseDto();

        // 🛑 carrito vacío
        if(order.getItems() == null || order.getItems().isEmpty()){
            response.status = false;
            response.message = "El carrito está vacío";
            return response;
        }

        // 🛑 datos obligatorios
        if(order.getClientName() == null || order.getClientName().isEmpty() ||
                order.getEmail() == null || order.getEmail().isEmpty() ||
                order.getPhone() == null || order.getPhone().isEmpty() ||
                order.getAddress() == null || order.getAddress().isEmpty() ||
                order.getPaymentMethod() == null || order.getPaymentMethod().isEmpty()){

            response.status = false;
            response.message = "Debe completar todos los datos de compra";
            return response;
        }

        // ⚙️ datos automáticos
        order.setStatus("PENDING");
        order.setDate(LocalDate.now().toString());

        repository.save(order);

        response.status = true;
        response.message = "Pedido creado correctamente";
        return response;
    }

    // ================== ADMIN ==================
    public Iterable<Order> getAll() {
        return repository.findAll();
    }

    public ResponseDto updateStatus(String id, String status){

        ResponseDto response = new ResponseDto();

        Optional<Order> opt = repository.findById(id);
        if(!opt.isPresent()){
            response.status = false;
            response.message = "Pedido no encontrado";
            return response;
        }

        Order order = opt.get();
        order.setStatus(status);
        repository.save(order);

        response.status = true;
        response.message = "Estado del pedido actualizado";
        return response;
    }
}
