package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.DPC.spring.entities.Laboratoire;
import com.DPC.spring.entities.Magasine;
import com.DPC.spring.entities.Radiologe;
import com.DPC.spring.entities.Utilisateur;

public interface RadiologeRepository extends JpaRepository<Radiologe, Long>{

	@Query(nativeQuery=true,value="SELECT * FROM `radiologe`WHERE rad_id=:id AND reponse IS NULL GROUP by client_id;")
	List<Radiologe> listemessageselonclient (@Param("id") Long id);

	@Query(nativeQuery=true,value="SELECT * FROM `radiologe`WHERE client_id=:id AND message IS NULL GROUP by rad_id;")
	List<Radiologe> listereponseselonlabo (@Param("id") Long id);

	List<Radiologe> findByClient(Utilisateur uc);


	List<Radiologe> findByClientAndReponseIsNull(Utilisateur ur);

	List<Radiologe> findByClientAndMessageIsNull(Utilisateur ur);


}
