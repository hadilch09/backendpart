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
import com.DPC.spring.entities.ville;
import com.DPC.spring.repositories.villerepos;

@CrossOrigin("*")

@RestController
@RequestMapping ("ville")

public class Villecontroller {

	@Autowired
	villerepos tabrepos ;
	@PreAuthorize("hasAuthority('Admin')")
	//@PreAuthorize("hasAnyAuthority('Admin', 'Docteur')")
	
	//ajouter avec test existe ou non
		@PostMapping("/ajouteravecville")
		public String ajouteravectestville (@RequestBody ville table) {
			//recherche dans base de données
			ville ville = this.tabrepos.findByVille(table.getVille());
			if(ville!=null) {
				return "exist";
			}
			else {
				this.tabrepos.save(table);
				return "ajouter table";
			}
		}
		  
			//afficher by id
			@GetMapping("/affichbyid")
			public ville affbyid(Long id) {
				return this.tabrepos.findById(id).get();
			}
			
			//afficher tous les ville
			@GetMapping("/listville")
			public List<ville> listtable(){
				return this.tabrepos.findAll();
			}
			
	
}

