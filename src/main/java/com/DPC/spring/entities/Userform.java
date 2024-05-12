package com.DPC.spring.entities;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userform {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Utilisateur u;
	private String ville ;
	private String specialite; 
	private List<Formation> listformation ;
}
