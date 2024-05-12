package com.DPC.spring.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)

	private Long id;
	private String nom ;
	private String prenom ;
	private String question ;
	private String description ;
	
}
