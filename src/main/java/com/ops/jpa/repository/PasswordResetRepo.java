package com.ops.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ops.jpa.entities.PasswordChangeInfo;

public interface PasswordResetRepo extends JpaRepository<PasswordChangeInfo, Long> {

	public PasswordChangeInfo findByToken(String token);

}
