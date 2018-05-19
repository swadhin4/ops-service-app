package com.ops.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

	public Customer findByEmail(String email);
}
