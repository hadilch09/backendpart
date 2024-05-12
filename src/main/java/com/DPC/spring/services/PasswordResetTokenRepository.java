package com.DPC.spring.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.PasswordResetToken;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{

}
