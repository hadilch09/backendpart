package com.DPC.spring.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DPC.spring.entities.Image;
import com.DPC.spring.repositories.ImageRepos;


@RestController
@CrossOrigin("*")
@RequestMapping("/image")

public class ImageController {
@Autowired
ImageRepos imagerepos ; 
	
	
	
	
	
	  @PostMapping("/uploadOffre")
			public String uplaodImageEleve(@RequestPart("imageFile") MultipartFile file, String nom)
					throws IOException {
		  Image cat = new Image(nom,compressBytes(file.getBytes()),file.getOriginalFilename(), file.getContentType());

		  imagerepos.save(cat);
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
