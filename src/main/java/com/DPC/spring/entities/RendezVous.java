package com.DPC.spring.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RendezVous {

	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date date;
	private String heure;
    private Boolean valider;
	@ManyToOne
	private Utilisateur	utilisateur;
	@ManyToOne
	private Utilisateur	client;
}
