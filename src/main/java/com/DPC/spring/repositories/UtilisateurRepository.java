package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DPC.spring.entities.Specialite;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.entities.ville;

import org.springframework.stereotype.Repository;

@Repository

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
  //  Utilisateur findById (long idUtilisateur) ;
	Utilisateur findByEmail(String email);

	List<Utilisateur> findByArchiverIsFalse();
	@Query(nativeQuery=true,value="select COUNT(*) FROM utilisateur where archiver=0")
	Long countuser ();
	@Query(nativeQuery=true,value="select COUNT(*) FROM menmbre")
	Long countmembre ();
	@Query(nativeQuery=true,value="select COUNT(*) FROM joueur")
	Long countjoueur ();
	@Query(nativeQuery=true,value="select COUNT(*) FROM document")
	Long countdocument ();



	List<Utilisateur> findByVille(ville v);

	List<Utilisateur> findBySpecialite(Specialite s);

	List<Utilisateur> findByValider(boolean b);

	Utilisateur findByVille(String email);

	List<Utilisateur> findByVilleAndSpecialiteAndProfilAndValiderIsTrue(ville v, Specialite s, String string);

	List<Utilisateur> findByProfilAndValiderIsTrue(String profil);

	List<Utilisateur> findByProfil(String profil);

	List<Utilisateur> findByVilleAndProfil(ville v, String string);

	Utilisateur findByEmailAndProfil(String emaillabo, String string);

}
