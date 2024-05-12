package com.DPC.spring.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reponse {


	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)

	private Long id;
	private String reponse;
	
	@ManyToOne
	private Question question; 
	@ManyToOne
	private Utilisateur utilisateur; 
}
