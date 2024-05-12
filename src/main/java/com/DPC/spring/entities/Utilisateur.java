package com.DPC.spring.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data


public class Utilisateur {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String nom ;
	private String prenom ;
	private String password ;
	private String login ;
	private String adresse;
	private String email;
	private String num;
	private String ddn;
	private String fax;
	private String jourtravail;
	private String profil ;
	private Boolean archiver ;
	private Boolean valider;
	@JsonIgnore
	@Temporal(TemporalType.DATE)
	private Date datecreation ;
	@JsonIgnore
	@ManyToOne
    private Autority authorities;
	@ManyToOne
	private ville ville; 
	@ManyToOne
	private Specialite specialite;
	@ManyToOne
	private Reponse reponse;
	@ManyToOne
	private Question question;
	
	
	}
