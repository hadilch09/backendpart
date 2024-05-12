package com.DPC.spring.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.DPC.spring.entities.Magasine;
import com.DPC.spring.entities.RendezVous;
import com.DPC.spring.entities.Utilisateur;

public interface RDVrepos extends JpaRepository<RendezVous, Long> {

	

	RendezVous findByDate(String date);

	List<RendezVous> findByUtilisateur(Utilisateur u);


	@Query(nativeQuery=true,value="SELECT id ,date FROM `rendez_vous`")
	List<Object> magasinebydatedebut ();

	List<RendezVous> findByUtilisateurAndDateAndValiderIsTrue(Utilisateur u, Date date);

	@Query(nativeQuery=true,value="SELECT * FROM `rendez_vous` WHERE date=:date and utilisateur_id=:id and valider=1 ")
	List<RendezVous> rdvbydate (@Param("date") String date,Long id);

	List<RendezVous> findByUtilisateurAndValiderIsTrue(Utilisateur u);

	List<RendezVous> findByUtilisateurAndValiderIsFalse(Utilisateur u);

	List<RendezVous> findByClient(Utilisateur uc);

}
