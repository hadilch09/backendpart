package com.DPC.spring.services;


import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImp implements  IUtilisateurService{


    @Autowired
    private UtilisateurRepository utilisateurRepository;

 /*   @Override
    public void add(Utilisateur utilisateur) {

        utilisateurRepository.save(utilisateur);
    } */
 
    @Override
    public void deleteUtilisateur(Long idUtilisateur) {
        Utilisateur user1 = utilisateurRepository.findById(idUtilisateur).orElse(null);
        utilisateurRepository.delete(user1);
    }

    @Override
    public List<Utilisateur> GetUtilisateur() {
        List<Utilisateur> utilisateur = (List<Utilisateur>) utilisateurRepository.findByArchiverIsFalse();
        return  utilisateur;

    }

	@Override
	public void updateUtilisateur(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		
	}

}
