package com.DPC.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Evenement;
public interface EvenementRepository extends JpaRepository<Evenement, Long> {

	Evenement findByCat(String cat);


}
