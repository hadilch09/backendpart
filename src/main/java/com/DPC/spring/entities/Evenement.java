package com.DPC.spring.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity

public class Evenement {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id ;
	private String cat ;
	private String titre;
	private String description;
	
	@Temporal(TemporalType.DATE)
	private Date debut;
	@Temporal(TemporalType.DATE)
	private Date fin;
	
	@Column(name = "picByte", length = 1000)
	private byte[] picByte;
	@Column(name = "image", length = 1000)
    private String nom ; 
	@Column(name = "type")
	private String type;


	public String getCat() {
		return cat;
	}


	public void setCat(String cat) {
		this.cat = cat;
	}


	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getDebut() {
		return debut;
	}


	public void setDebut(Date debut) {
		this.debut = debut;
	}


	public Date getFin() {
		return fin;
	}


	public void setFin(Date fin) {
		this.fin = fin;
	}


	public byte[] getPicByte() {
		return picByte;
	}


	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Evenement(String cat, String titre, String description, Date debut, Date fin, byte[] picByte, String nom,
			String type) {
		super();
		this.cat = cat;
		this.titre = titre;
		this.description = description;
		this.debut = debut;
		this.fin = fin;
		this.picByte = picByte;
		this.nom = nom;
		this.type = type;
	}
	
	public Evenement() {
		super();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
}