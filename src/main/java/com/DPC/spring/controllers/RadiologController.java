package com.DPC.spring.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DPC.spring.entities.Autority;
import com.DPC.spring.entities.Radiologe;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.entities.ville;
import com.DPC.spring.repositories.AuthorityRepository;
import com.DPC.spring.repositories.RadiologeRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.repositories.villerepos;

@CrossOrigin("*")
@RestController
@RequestMapping("Radiologe")
public class RadiologController {

	@Autowired
	villerepos tabrepos;
	@Autowired
	AuthorityRepository authrepos;
	@Autowired
	UtilisateurRepository userrepos;
	@Autowired
	RadiologeRepository radrepos;
	//security login 
	@Autowired
	private PasswordEncoder encoder;
		
	  
	//ajouter 

		@PostMapping ("/ajoutradio")
		public String ajoutetab (@RequestBody Utilisateur t ,String ville ) {
		ville v=this.tabrepos.findByVille(ville);
		Utilisateur u=this.userrepos.findByEmail(t.getEmail());
		Autority auth=this.authrepos.findByName("radiologue");
		String pass = encoder.encode(t.getPassword());
		if(u==null) {
			
	    t.setProfil("radiologue");
		t.setVille(v);	
		t.setPassword(pass);
		t.setValider(false);
		t.setAuthorities(auth);
  		this.userrepos.save(t); 
		return "table ajout";
		
		}
		else {
			return "false";
		}
		
	}
	
	
	
	//afficher liste selon ville
		@GetMapping("/afficherlistbyville")
		public List<Utilisateur> afficherlist (String ville)
		{
			ville v=this.tabrepos.findByVille(ville);
			List<Utilisateur> Listrad= this.userrepos.findByVilleAndProfil(v,"radiologue");
			return Listrad;
			
		}
		//affiche by id
		
		@GetMapping("/getbyid")
		public Utilisateur getbyid(Long id) {
		Utilisateur r =this.userrepos.findById(id).get();
		return r ;
		}
		
		//affiche by profil
		
		@GetMapping ("affichebyprofil")
		public List<Utilisateur> affichelistbyprofil (String profil)
		{
			List<Utilisateur> Listhome=this.userrepos.findByProfilAndValiderIsTrue(profil);
			return Listhome;
		}
	
		//affiche all
				@GetMapping("/affichelist")
				public List<Utilisateur> affichlist() {
					return this.userrepos.findByProfil("radiologue");
				}
	  
	//update 
	

		@PreAuthorize("hasAuthority('Admin')")

			@PutMapping("/modifierradio")
			public String modiftab(@RequestBody Utilisateur t)
			{
				 
			Utilisateur tab=this.userrepos.findById(t.getId()).get();
				 tab.setNom(t.getNom());
				 this.userrepos.saveAndFlush(tab);
				return "true" ;
			}
		//CRUD message client-radio
		//ajout message

		
		
		
				@PreAuthorize("hasAuthority('Client')")
				  @PostMapping("/envoyermesageclientrad")
						public String uplaodImageEleve(@RequestPart("imageFile") MultipartFile file, String message,String nomfichier, String emailclient ,Long idrad)
								throws IOException {
					  Utilisateur uc=this.userrepos.findByEmail(emailclient);
					  Utilisateur ur=this.userrepos.findById(idrad).get();
					  Date d=new Date(System.currentTimeMillis());
					  Radiologe cat = new Radiologe(message,null,d,nomfichier,compressBytes(file.getBytes()),file.getOriginalFilename(), file.getContentType(),ur,uc);

					  this.radrepos.save(cat);
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


                     //affiche list message 
					@PreAuthorize("hasAuthority('radiologue')")
					@GetMapping("listmessageclientrad")
					public List<Radiologe> listmessage(Long id) {
					 return this.radrepos.listemessageselonclient(id);
							
					}
				
					//affiche message selon id + image

 					@PreAuthorize("hasAnyAuthority('radiologue','client')")
 					@GetMapping("messageclientavecimage")
 					public List<Radiologe> listelmessageclientavecimage(Long id){
 					Utilisateur uc=this.userrepos.findById(id).get();
 					List<Radiologe> listfinale=new ArrayList<>();		 					
 					List<Radiologe> c=this.radrepos.findByClientAndReponseIsNull(uc);
 					for (int i = 0; i < c.size(); i++) {
						Radiologe l=new Radiologe();
						l.setId(c.get(i).getId());
						l.setMessage(c.get(i).getMessage());
						l.setDate(c.get(i).getDate());
						l.setClient(c.get(i).getClient());
						l.setRad(c.get(i).getRad());
						l.setNom(c.get(i).getNom());
	             //image
						l.setPicByte(decompressBytes(c.get(i).getPicByte()));
						listfinale.add(l);
					}
 						       
 					return listfinale;
 						
 					}
 					
 					//modification

 					@PreAuthorize("hasAuthority('Client')")
 					@PutMapping("/modifier")
 					public String modifiertable(@RequestBody Radiologe t) {
 						Radiologe tab = this.radrepos.findById(t.getId()).get();
 						tab.setMessage(t.getMessage());
 						this.radrepos.saveAndFlush(tab);
 						return "message modiefier";
 					}
 					//suppression
 					@DeleteMapping("/supprimer")
 						public String supprimer(Long id){
 						Radiologe t =this.radrepos.findById(id).get();
 						this.radrepos.delete(t);
 						return "table effacer";
 					}
 					
 		
 					
 			//CRUD reponse client-radio
 					//ajout reponse

 					@PreAuthorize("hasAuthority('radiologue')")
 					  @PostMapping("/envoyerreponseradclient")
 							public String reponserad(@RequestPart("imageFile") MultipartFile file, String reponse,String nomfichier, Long id ,Long idrad)
 									throws IOException {
 						  Utilisateur uc=this.userrepos.findById(id).get();
 						  Utilisateur ur=this.userrepos.findById(idrad).get();
 						  Date d=new Date(System.currentTimeMillis());
 						  Radiologe cat = new Radiologe(null,reponse,d,nomfichier,compresBytes(file.getBytes()),file.getOriginalFilename(), file.getContentType(),ur,uc);

 						  this.radrepos.save(cat);
 								return "true";
 							}	

 					  public static byte[] decompresBytes(byte[] data) {
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

 					  
 					  public static byte[] compresBytes(byte[] data) {
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

 						//affiche reponse selon id + image

 	 					@PreAuthorize("hasAnyAuthority('radiologue','Client')")
 	 					@GetMapping("reponseradavecimage")
 	 					public List<Radiologe> listelreponselaboavecimage(Long id){
 	 					Utilisateur ur=this.userrepos.findById(id).get();
 	 					List<Radiologe> listfinale=new ArrayList<>();		 					
 	 					List<Radiologe> r=this.radrepos.findByClientAndMessageIsNull(ur);
 	 					
 	 					for (int i = 0; i < r.size(); i++) {
 							Radiologe l=new Radiologe();
 							l.setId(r.get(i).getId());
 							l.setReponse(r.get(i).getReponse());
 							l.setDate(r.get(i).getDate());
 							l.setClient(r.get(i).getClient());
 							l.setRad(r.get(i).getRad());
 							l.setNom(r.get(i).getNom());
 		             //image
 							l.setPicByte(decompressBytes(r.get(i).getPicByte()));
 							listfinale.add(l);
 						}
 	 						       
 	 					return listfinale;
 	 						
 	 					}
 	 				    //affiche list reponse 
 						@PreAuthorize("hasAuthority('Client')")
 						@GetMapping("listreponseradclient")
 						public List<Radiologe> listreponse(Long id) {
 						 return this.radrepos.listereponseselonlabo(id);
 								
 						}
				
	
	
	
	 
}
