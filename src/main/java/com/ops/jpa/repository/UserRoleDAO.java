/*
 * Copyright (C) 2013 , Inc. All rights reserved 
 */
package com.ops.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.User;
import com.ops.jpa.entities.UserRole;

/**
 * The Interface UserRoleDAO.
 * 
 * 
 */
public interface UserRoleDAO extends JpaRepository<UserRole, Long> {

	public List<UserRole> findByUser(User user);
}
