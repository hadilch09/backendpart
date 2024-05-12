package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.DPC.spring.repositories.ReponseRepos;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.entities.Question;
import com.DPC.spring.entities.Reponse;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.QuestionRpos;

@CrossOrigin("*")
@RestController
@RequestMapping("Reponse")

public class ReponseController {

	@Autowired
	ReponseRepos reprepos;
	@Autowired
	QuestionRpos quesrepos;
	@Autowired
	UtilisateurRepository userrepos;
	
	//afficher reponse selon question
	@GetMapping("/afficherlistbyquestion")
	public List<Reponse> afficherlist (String question)
	{
		Question q=this.quesrepos.findByQuestion(question);
		List<Reponse> Listrep= this.reprepos.findByQuestion(q);
		return Listrep;
		
	}
	
	@PreAuthorize("hasAnyAuthority('Admin', 'Docteur')")
	//ajouter avec relation 
		@PostMapping ("/ajouter avec relation")
		public String ajoutavecrelation (@RequestBody Reponse r, String email , Long idquestion) {
		
		Utilisateur u=this.userrepos.findByEmail(email);
		Question q=this.quesrepos.findById(idquestion).get();
		if(u == null  || q == null )
		 {
			 return "n'existe pas";
			 
		 }
		 else {
			
		 
			 r.setUtilisateur(u);
			 r.setQuestion(q);
		     this.reprepos.save(r);
		     return ("table ajoute");
		     
		 } 
		}
		}
