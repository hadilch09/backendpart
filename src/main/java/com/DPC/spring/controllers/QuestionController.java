package com.DPC.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DPC.spring.entities.Question;
import com.DPC.spring.repositories.QuestionRpos;

@CrossOrigin("*")
@RestController
@RequestMapping("Question")
public class QuestionController {
	@Autowired
	QuestionRpos Qesrepos;
	
	

	   //ajouter 
		@PostMapping ("/ajoutQuestion")
		public String ajoutequestion (@RequestBody Question t ) {
			this.Qesrepos.save(t);
			return "table";
		}
		
	    //afficher la question
		@GetMapping("/affichelist")
		public List<Question> affichlist() {
			return this.Qesrepos.findAll();
		}

		//update
		@PutMapping("/modifierquestion")
		public String modifquestion(@RequestBody Question t)
		{
			 
			Question tab=this.Qesrepos.findById(t.getId()).get();
			 tab.setQuestion(t.getQuestion());
			 this.Qesrepos.saveAndFlush(tab);
			return "true" ;
		}
		
}
