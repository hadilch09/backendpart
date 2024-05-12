package com.DPC.spring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Specialite {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	private Long id;
	private String specialite;

	@Column(name = "picByte", length = 1000)
	private byte[] picByte;
	@Column(name = "image", length = 1000)
    private String nom ; 
	@Column(name = "type")
	private String type;
	public String getSpecialite() {
		return specialite;
	}
	public void setSpecialite(String specialite) {
		this.specialite = specialite;
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
	public Specialite(String specialite, byte[] picByte, String nom, String type) {
		super();
		this.specialite = specialite;
		this.picByte = picByte;
		this.nom = nom;
		this.type = type;
	}
	public Specialite() {
		super();
	}

	
}
