package com.DPC.spring.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Imagetest {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id ;
	@Column(name = "picByte", length = 1000)
	private byte[] picByte;
	@Column(name = "image", length = 1000)
    private String nom ; 
	@Column(name = "type")
	private String type;
	private String nomeven ;
	private String typeeven ;
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
	public String getNomeven() {
		return nomeven;
	}
	public void setNomeven(String nomeven) {
		this.nomeven = nomeven;
	}
	public String getTypeeven() {
		return typeeven;
	}
	public void setTypeeven(String typeeven) {
		this.typeeven = typeeven;
	}
	public Imagetest(byte[] picByte, String nom, String type, String nomeven, String typeeven) {
		super();
		this.picByte = picByte;
		this.nom = nom;
		this.type = type;
		this.nomeven = nomeven;
		this.typeeven = typeeven;
	}
	public Imagetest() {
		super();
	}
	
}
