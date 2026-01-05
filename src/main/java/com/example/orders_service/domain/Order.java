package com.example.orders_service.domain;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders_micro")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false)
  private String customerName;

  @Column(nullable = false)
  private String status; // después lo pasamos a Enum

  @Column(nullable = false, updatable = false)
  private Instant createdAt;

  protected Order() {} // requerido por JPA

  public Order(String customerName, String status) {
    this.customerName = customerName;
    this.status = status;
  }

  @PrePersist
  void onCreate() {
    this.createdAt = Instant.now();
  }

  public String getId() { return id; }
  public String getCustomerName() { return customerName; }
  public String getStatus() { return status; }
  public Instant getCreatedAt() { return createdAt; }

  public void setStatus(String status) { this.status = status; }
}
