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
import com.DPC.spring.entities.Laboratoire;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.entities.ville;
import com.DPC.spring.repositories.AuthorityRepository;
import com.DPC.spring.repositories.LaboratoireRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.repositories.villerepos;

@CrossOrigin("*")
@RestController
@RequestMapping("Laboratoire")
public class LaboratoireController {

	 
	@Autowired
	
	villerepos tabrepos;

	@Autowired
	LaboratoireRepository labrepos;
	@Autowired
	AuthorityRepository authrepos;
	@Autowired
	UtilisateurRepository userrepos;
	
	//security login 
	@Autowired
	private PasswordEncoder encoder;
	  
	//ajouter 
	@PostMapping ("/ajoutlabo")
	public String ajoutetab (@RequestBody Utilisateur t ,String ville ) {
	ville v=this.tabrepos.findByVille(ville);
	Utilisateur u=this.userrepos.findByEmail(t.getEmail());
	Autority auth=this.authrepos.findByName("laboratoire");
	String pass = encoder.encode(t.getPassword());
	if(u==null) {
    t.setProfil("laboratoire");
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
		
	  

	//update 
	

	@PreAuthorize("hasAuthority('Admin')")

		@PutMapping("/modifierLabo")
		public String modiftab(@RequestBody Utilisateur t)
		{
			 
			Utilisateur tab=this.userrepos.findById(t.getId()).get();
			String pass = encoder.encode(tab.getPassword());
			 tab.setNom(t.getNom());
			 tab.setPassword(pass);
			 this.userrepos.save(tab);
			return "true" ;
		}

	
  
//afficher liste selon ville
	@GetMapping("/afficherlistbyville")
	public List<Utilisateur> afficherlistlabo (String ville)
	{
		ville v=this.tabrepos.findByVille(ville);
		List<Utilisateur> Listlabo= this.userrepos.findByVilleAndProfil(v,"laboratoire");
		return Listlabo;
		
	}
	//affiche by profil
	@GetMapping ("affichebyprofil")
	public List<Utilisateur> affichelistbyprofil (String profil)
	{
		List<Utilisateur> Listhome=this.userrepos.findByProfilAndValiderIsTrue(profil);
		return Listhome;
	}

	
	
	//afficher by id
		@GetMapping("/tablebyid")
		public Utilisateur tablebyid(Long id) {
			return this.userrepos.findById(id).get();
		}
		//afficher by email
		@GetMapping("/tablebyemail")
		public Utilisateur tablebyemail(String emaillabo) {
			return this.userrepos.findByEmailAndProfil(emaillabo,"laboratoire");
		}
		
		//affiche all
		@GetMapping("/affichelist")
		public List<Utilisateur> affichlist() {
			return this.userrepos.findByProfil("laboratoire");
		}
		
	
		//CRUD message client-labo
		//ajout message

		@PreAuthorize("hasAuthority('Client')")
		  @PostMapping("/envoyermesageclientlabo")
				public String uplaodImageEleve(@RequestPart("imageFile") MultipartFile file, String message,String nomfichier, String emailclient ,Long idlabo)
						throws IOException {
			  Utilisateur uc=this.userrepos.findByEmail(emailclient);
			  Utilisateur ul=this.userrepos.findById(idlabo).get();
			  Date d=new Date(System.currentTimeMillis());
			  Laboratoire cat = new Laboratoire(message,null,d,nomfichier,compressBytes(file.getBytes()),file.getOriginalFilename(), file.getContentType(),ul,uc);

			  this.labrepos.save(cat);
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
		 					@PreAuthorize("hasAuthority('laboratoire')")
		 					@GetMapping("listmessageclientlab")
		 					public List<Laboratoire> listmessage(Long id) {
		 					 return this.labrepos.listemessageselonclient(id);
		 							
		 					}
		 					
		 		//affiche message selon id

		 					@PreAuthorize("hasAuthority('laboratoire')")
		 					@GetMapping("messageclient")
		 					public List<Laboratoire> listelmessageclient(Long id){
		 					Utilisateur uc=this.userrepos.findById(id).get();
		 					List<Laboratoire> c=this.labrepos.findByClient(uc);
		 						return c;
		 					}

		 					//affiche message selon id + image

		 					@PreAuthorize("hasAuthority('laboratoire')")
		 					@GetMapping("messageclientavecimage")
		 					public List<Laboratoire> listelmessageclientavecimage(Long id){
		 					Utilisateur uc=this.userrepos.findById(id).get();
		 					List<Laboratoire> listfinale=new ArrayList<>();		 					
		 					List<Laboratoire> c=this.labrepos.findByClientAndReponseIsNull(uc);
		 					for (int i = 0; i < c.size(); i++) {
								Laboratoire l=new Laboratoire();
								l.setId(c.get(i).getId());
								l.setMessage(c.get(i).getMessage());
								l.setDate(c.get(i).getDate());
								l.setClient(c.get(i).getClient());
								l.setLabo(c.get(i).getLabo());
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
		 					public String modifiertable(@RequestBody Laboratoire t) {
		 						Laboratoire tab = this.labrepos.findById(t.getId()).get();
		 						tab.setMessage(t.getMessage());
		 						this.labrepos.saveAndFlush(tab);
		 						return "message modiefier";
		 					}
		 					//suppression
		 					@DeleteMapping("/supprimer")
		 						public String supprimer(Long id){
		 						Laboratoire t =this.labrepos.findById(id).get();
		 						this.labrepos.delete(t);
		 						return "table effacer";
		 					}
		 					
		 					//CRUD reponse labo-client
		 					
		 					//affiche reponse selon id + image

		 					@PreAuthorize("hasAnyAuthority('laboratoire','Client')")
		 					@GetMapping("reponseclientavecimage")
		 					public List<Laboratoire> listereponselaboavecimage(Long id){
		 					Utilisateur ul=this.userrepos.findById(id).get();
		 					List<Laboratoire> listfinale=new ArrayList<>();		 					
		 					List<Laboratoire> lb=this.labrepos.findByLaboAndMessageIsNull(ul);
		 					for (int i = 0; i < lb.size(); i++) {
		 						
								Laboratoire l=new Laboratoire();
								l.setId(lb.get(i).getId());
								l.setReponse(lb.get(i).getReponse());
								l.setDate(lb.get(i).getDate());
								l.setClient(lb.get(i).getClient());
								l.setLabo(lb.get(i).getLabo());
								l.setNom(lb.get(i).getNom());
			             //image
								l.setPicByte(decompressBytes(lb.get(i).getPicByte()));
								listfinale.add(l);
							}
		 						       
		 					
		 					return listfinale;
		 						
		 					}
		 					  //affiche list reponse
		 					@PreAuthorize("hasAuthority('Client')")
		 					@GetMapping("listreponselaboclient")
		 					public List<Laboratoire> listreponse(Long id) {
		 					 return this.labrepos.listereponselaboclient(id);
		 							
		 					}
		 					
		 					//ajout reponse

		 					@PreAuthorize("hasAuthority('laboratoire')")
		 					  @PostMapping("/envoyerreponselaboclient")
		 							public String envoyerreponse(@RequestPart("imageFile") MultipartFile file, String reponse,String nomfichier, Long id ,Long idlabo)
		 									throws IOException {
		 						  Utilisateur uc=this.userrepos.findById(id).get();
		 						  Utilisateur ul=this.userrepos.findById(idlabo).get();
		 						  Date d=new Date(System.currentTimeMillis());
		 						  Laboratoire cat = new Laboratoire(null,reponse,d,nomfichier,compresBytes(file.getBytes()),file.getOriginalFilename(), file.getContentType(),ul,uc);

		 						  this.labrepos.save(cat);
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
                              }
