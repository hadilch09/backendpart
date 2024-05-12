package com.DPC.spring.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DPC.spring.entities.Evenement;
import com.DPC.spring.repositories.EvenementRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/Evenement")
public class EvenementController {
	@Autowired
	EvenementRepository eventrepos;
	
	 

@PostMapping("/ajouterEvenement")
public String uplaodImageEleve(@RequestPart("eventfile") MultipartFile file,String cat ,String titre ,String description  ,Date debut ,Date fin ) throws IOException {
	
Evenement event = new Evenement(cat , titre , description , debut , fin , compressBytes(file.getBytes()),file.getOriginalFilename(), file.getContentType());

this.eventrepos.save(event);
	return "true";
}	


@GetMapping("/allevents")
public List <Evenement>list(){
return this.eventrepos.findAll();
}




@PutMapping("/modifier")
public String modiftab(@RequestBody Evenement tab) {
	Evenement t=this.eventrepos.findById(tab.getId()).get();
    t.setNom(tab.getNom());
    this.eventrepos.saveAndFlush(t);
    return "true";
    
}



public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}






public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		return outputStream.toByteArray();
	}



}




