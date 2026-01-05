package com.example.orders_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.orders_service.domain.Order;
import com.example.orders_service.repository.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderRepository repo;

  public OrderController(OrderRepository repo) {
    this.repo = repo;
  }

  @PostMapping
  public ResponseEntity<Order> create(@RequestBody CreateOrderRequest req) {
    Order order = new Order(req.customerName(), "PENDING");
    Order saved = repo.save(order);
    return ResponseEntity.ok(saved);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getById(@PathVariable String id) {
    return repo.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<Order> updateStatus(@PathVariable String id,
                                            @RequestBody UpdateStatusRequest req) {
    return repo.findById(id).map(order -> {
      order.setStatus(req.status());
      Order saved = repo.save(order);
      return ResponseEntity.ok(saved);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Order>> list() {
    return ResponseEntity.ok(repo.findAll());
  }

  // DTOs (records) simples para requests
  public record CreateOrderRequest(String customerName) {}
  public record UpdateStatusRequest(String status) {}
}
