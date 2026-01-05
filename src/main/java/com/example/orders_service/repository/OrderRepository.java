package com.example.orders_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.orders_service.domain.Order;

public interface OrderRepository extends JpaRepository<Order, String> {}
