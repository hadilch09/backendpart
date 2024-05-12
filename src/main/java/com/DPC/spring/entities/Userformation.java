package com.DPC.spring.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

public class Userformation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id ;
	@ManyToOne
	private Utilisateur user ;
	@ManyToOne 
	private Formation formation ; 
	
}
