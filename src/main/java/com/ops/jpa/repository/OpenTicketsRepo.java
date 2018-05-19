package com.ops.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.OpenTicketsView;

public interface OpenTicketsRepo extends JpaRepository<OpenTicketsView, Long> {

}
