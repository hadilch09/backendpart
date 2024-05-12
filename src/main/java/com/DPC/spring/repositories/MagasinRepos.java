package com.DPC.spring.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.DPC.spring.entities.Magasine;

public interface MagasinRepos extends JpaRepository<Magasine, Long> {


	@Query(nativeQuery=true,value="SELECT * FROM `magasine` WHERE debut=:date ")
	List<Magasine> magasinebydatedebut (@Param("date") String unite);

	@Query(nativeQuery=true,value="SELECT * FROM `magasine` WHERE debut <= ? AND ? <=fin;")
	List<Magasine> magasinebydatebetweendebutfin (String unite, String date);
	List<Magasine> findByDebut(Date ddebut);

	List<Magasine> findByFin(Date dfin);
}
