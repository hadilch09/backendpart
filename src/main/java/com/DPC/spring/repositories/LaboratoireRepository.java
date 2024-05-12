package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.DPC.spring.entities.Laboratoire;
import com.DPC.spring.entities.Radiologe;
import com.DPC.spring.entities.Utilisateur;


public interface LaboratoireRepository extends JpaRepository<Laboratoire, Long>{

	

	Laboratoire findByMessage(String message);

	@Query(nativeQuery=true,value="SELECT * FROM `laboratoire`WHERE labo_id=:id AND reponse IS NULL GROUP by client_id;")
	List<Laboratoire> listemessageselonclient (@Param("id") Long id);
	@Query(nativeQuery=true,value="SELECT * FROM `laboratoire`WHERE client_id=:id AND message IS NULL GROUP by labo_id;")
	List<Laboratoire> listereponselaboclient (@Param("id") Long id);

	List<Laboratoire> findByClient(Utilisateur uc);

	List<Laboratoire> findByLabo(Utilisateur ul);

	List<Laboratoire> findByLaboAndMessageIsNull(Utilisateur ul);

	List<Laboratoire> findByClientAndReponseIsNull(Utilisateur uc);


	   
}
