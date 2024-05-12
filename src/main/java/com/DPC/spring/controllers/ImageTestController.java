package com.DPC.spring.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DPC.spring.entities.Imagetest;
import com.DPC.spring.repositories.ImagetestRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/imagetest")

public class ImageTestController {
@Autowired
ImagetestRepository imgrepos ; 



@PostMapping("/ajouterimagetest")
public String uplaodImageEleve(@RequestPart("imageFile") MultipartFile file, String nomeven,String typeeeven) throws IOException {
	
Imagetest image = new Imagetest(compressBytes(file.getBytes()),file.getOriginalFilename(), file.getContentType(),nomeven,typeeeven);

this.imgrepos.save(image);
	return "true";
}	
@GetMapping("/all")
public List<Imagetest>list(){
return this.imgrepos.findAll();
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


