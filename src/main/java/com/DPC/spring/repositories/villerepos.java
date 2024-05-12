package com.DPC.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.ville;

public interface villerepos extends JpaRepository<ville, Long> {

	ville findByVille(String ville);

	
}
