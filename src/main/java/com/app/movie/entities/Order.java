package com.app.movie.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String clientId;
    private String clientName;
    private String email;

    private String phone;
    private String address;
    private String paymentMethod;

    private Double total;
    private String status;   // PENDING | APPROVED | REJECTED | SHIPPED
    private String date;

    // Productos comprados
    private List<String> items;

    public Order() {}


    // GETTERS Y SETTERS
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }


    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public List<String> getItems() { return items; }
    public void setItems(List<String> items) { this.items = items; }

}
