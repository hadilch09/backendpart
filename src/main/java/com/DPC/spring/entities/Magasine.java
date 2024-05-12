package com.DPC.spring.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Magasine {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)

	private Long id;
	@Temporal(TemporalType.DATE)
	private Date debut;
	@Temporal(TemporalType.DATE)
	private Date fin;
	private String type;
	private String titre;
	private String description;

}
