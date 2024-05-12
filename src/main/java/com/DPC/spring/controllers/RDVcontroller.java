package com.DPC.spring.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.RendezVous;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.repositories.RDVrepos;
import com.DPC.spring.repositories.UtilisateurRepository;

@CrossOrigin("*")
@RestController
@RequestMapping ("/RendezVous")
public class RDVcontroller {

	
	@Autowired
	RDVrepos rdvrepos;
	@Autowired
	UtilisateurRepository userrepo;
	
	
	@PreAuthorize("hasAuthority('Docteur')")

	//modifier
	@PutMapping("/modifier")
	public String modiftab(@RequestBody RendezVous t , 	String email)
	{
		 Utilisateur u=this.userrepo.findByEmail(email);
		RendezVous tab=this.rdvrepos.findById(t.getId()).get();
		
		 tab.setUtilisateur(u);
		 tab.setValider(false);
		 
		tab=rdvrepos.saveAndFlush(t);
		 
		 
		return "true" ;
	}
	
	//ajout
	@PostMapping("/ajout")
	public String ajoutab (@RequestBody RendezVous t , Long id , String email) {
		Utilisateur u=this.userrepo.findById(id).get();
		Utilisateur uc=this.userrepo.findByEmail(email);
		if(uc==null) {
			return "false";
		}
		else {
		t.setClient(uc);
		t.setUtilisateur(u);
		t.setValider(false);
		this.rdvrepos.save(t);
		
		return "true";
	}}
	
	//valider
	@PreAuthorize("hasAuthority('Docteur')")
	@GetMapping("/valider")
		public String valider(Long id ) {
		 RendezVous r=this.rdvrepos.findById(id).get();
		 r.setValider(true);
		 this.rdvrepos.save(r);
		 return "true";
		}
	@PreAuthorize("hasAuthority('Docteur')")
	@GetMapping("/affichervalider")
		public List<RendezVous> affichervalider(String email ) {
		 Utilisateur u=this.userrepo.findByEmail(email);
		 return this.rdvrepos.findByUtilisateurAndValiderIsTrue(u);
		 }
	@PreAuthorize("hasAuthority('Docteur')")
	@GetMapping("/affichernonvalider")
		public List<RendezVous> affichernonvalider(String email ) {
		 Utilisateur u=this.userrepo.findByEmail(email);
		 return this.rdvrepos.findByUtilisateurAndValiderIsFalse(u);
		 }
	

	//afficher list selon client 
		@GetMapping("/afficherRDVbyemailClient")
		public List<RendezVous> afficherlistclient (String email)
		{
			Utilisateur uc=this.userrepo.findByEmail(email);
			List<RendezVous> Listrdv= this.rdvrepos.findByClient(uc);
			return Listrdv;
			
		}
		

		//afficher RDV selon utilisateur 
			@GetMapping("/afficherRDV")
			public List<Object> afficherlist (String email)
			{
				Utilisateur u=this.userrepo.findByEmail(email);
				List<Object> Listrdv= this.rdvrepos.magasinebydatedebut();
				return Listrdv;
				
			}
		@GetMapping("/afficherRDVbydate")
		public List<RendezVous> afficherlist (Date date,Long id)
		{
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        String formattedDate = dateFormat.format(date);
	        System.out.println(formattedDate);

			List<RendezVous> Listrdv= this.rdvrepos.rdvbydate(formattedDate,id);
			return Listrdv;
		}
		
}
