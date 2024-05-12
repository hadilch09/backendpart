package com.DPC.spring.controllers;

import java.text.DateFormat;
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

import com.DPC.spring.entities.Magasine;
import com.DPC.spring.repositories.MagasinRepos;

@CrossOrigin("*")
@RestController
@RequestMapping("Magasine")
public class MagasinController {

	@Autowired
	MagasinRepos magrepos;
	
	

	  
	//ajouter 
	@PreAuthorize("hasAnyAuthority('Admin', 'Docteur')")

		@PostMapping ("/ajoutmagasine")
		public String ajoutetab (@RequestBody Magasine t ) {
			this.magrepos.save(t);
			return "table ajout";
		}
		
	   		
	//update 
	@PreAuthorize("hasAnyAuthority('Admin', 'Docteur')")

		@PutMapping("/modifiermagasin")
		public String modiftab(@RequestBody Magasine t)
		{
			 
			Magasine tab=this.magrepos.findById(t.getId()).get();
			 tab.setType(t.getType());
			 this.magrepos.saveAndFlush(tab);
			return "true" ;
		}
		
		
		
	
		
			//afficher reponse selon date fin et date debut 
			@GetMapping("/afficherlistmagasinefinetdebut")
			public List<Magasine> afficherlistbyfin ()
			{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			    Date date = new Date();
			    String dateToStr = dateFormat.format(date);
			    System.out.println("Date is " + dateToStr);

			    
			    
			    List<Magasine> Listmag= this.magrepos.magasinebydatebetweendebutfin(dateToStr,dateToStr);
				return Listmag;
				
			
}
			}
