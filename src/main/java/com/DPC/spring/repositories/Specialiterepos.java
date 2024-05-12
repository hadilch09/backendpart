

package com.DPC.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Specialite;

public interface Specialiterepos extends JpaRepository<Specialite, Long> {

	Specialite findBySpecialite(String specialite);

	
}
