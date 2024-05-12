package com.DPC.spring.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Radiologe {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	
	private Long id;
	private String message;
	public Radiologe() {
		super();
	}
	public Radiologe(String message, String reponse, Date date, String nomfichier,
			byte[] picByte, String nom, String type, Utilisateur rad, Utilisateur client) {
		super();
		this.id = id;
		this.message = message;
		this.reponse = reponse;
		this.date = date;
		this.nomfichier = nomfichier;
		this.picByte = picByte;
		this.nom = nom;
		this.type = type;
		this.rad = rad;
		this.client = client;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getNomfichier() {
		return nomfichier;
	}
	public void setNomfichier(String nomfichier) {
		this.nomfichier = nomfichier;
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
	public Utilisateur getRad() {
		return rad;
	}
	public void setRad(Utilisateur rad) {
		this.rad = rad;
	}
	public Utilisateur getClient() {
		return client;
	}
	public void setClient(Utilisateur client) {
		this.client = client;
	}
	private String reponse;
	@Temporal(TemporalType.DATE)
	private Date date;
	private String nomfichier;
	@Lob
	@Column(name = "picByte", length = 1000)
	private byte[] picByte;
	@Column(name = "image", length = 1000)
    private String nom; 
    @Column(name = "type")
	private String type;

   
	@ManyToOne
	private Utilisateur	rad ;
	@ManyToOne
	private Utilisateur	client;
	
}
