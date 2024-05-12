package com.DPC.spring.controllers;

import com.DPC.spring.services.IUtilisateurService;
import com.DPC.spring.services.MailService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.DPC.spring.entities.Autority;
import com.DPC.spring.entities.Formation;
import com.DPC.spring.entities.Specialite;
import com.DPC.spring.entities.Userform;
import com.DPC.spring.entities.Userformation;
import com.DPC.spring.entities.Utilisateur;
import com.DPC.spring.entities.ville;
import com.DPC.spring.repositories.AuthorityRepository;
import com.DPC.spring.repositories.FormationRepository;
import com.DPC.spring.repositories.Specialiterepos;
import com.DPC.spring.repositories.UserFormationRepository;
import com.DPC.spring.repositories.UtilisateurRepository;
import com.DPC.spring.repositories.villerepos;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.crypto.NoSuchPaddingException;


@CrossOrigin("*")
@RestController
@RequestMapping("users")
public class UtilisateurController {
	

	// crud  Utilisateur

	@Autowired
     private IUtilisateurService iUtilisateurService;
	@Autowired
	villerepos tabrepos ; 
	@Autowired
	Specialiterepos specialiterepos;
	
	//security login 
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	UtilisateurRepository userrepos ; 
	@Autowired
	AuthorityRepository authrepos ;
	
	
	
 	@PreAuthorize("hasAuthority('admin')")
	@DeleteMapping("/delete")
	@ApiOperation(value = "supprimer Utilisateur ")
	public void deleteUtilisateur(long idUtilisateur)  {

		iUtilisateurService.deleteUtilisateur(idUtilisateur);
	}

	@PutMapping("/update")
	@ResponseBody
	//@ApiOperation(value = " update personne ")
	public void updatePersonne(@RequestBody Utilisateur utilisateur) {
		iUtilisateurService.updateUtilisateur(utilisateur);
	}

	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/GetAllUser")
	public List<Utilisateur> GetUtilisateur() {
		return iUtilisateurService.GetUtilisateur();
	}


	
	
	
	@PostMapping("/comptclient")
	public String comptclient(@RequestBody Utilisateur user) {
		Utilisateur u=this.userrepos.findByEmail(user.getEmail());
		if(u==null) {
		Autority auth = this.authrepos.findByName("Client"); //bech ychouf role mawjoud ou nn
		String pass = encoder.encode(user.getPassword());
		user.setProfil("Client");//hadedna l profil
		user.setValider(true);
		user.setPassword(pass);
		user.setDatecreation(new Date(System.currentTimeMillis()));	
		user.setArchiver(false);
		user.setAuthorities(auth);
		this.userrepos.save(user);
		return "true";
	}
		else {
			return "false";
		}
		
	}

	@GetMapping("/getbyid")  
	public Utilisateur getbyid(Long id) {
	Utilisateur u =this.userrepos.findById(id).get();
	return u ;
	}
	@Autowired
	MailService mailservice ;
	@PostMapping("/testmailing")
	
	public String testmailing(String email,String objet ,String description ) throws NoSuchAlgorithmException, NoSuchPaddingException {
		this.mailservice.testmail(email,objet,description);
		
		return "true" ;
	}
	
	@PostMapping("/envoyeremail")
	public String email(String email) throws NoSuchAlgorithmException, NoSuchPaddingException {
		this.mailservice.RenisialiserMotdepasse(email);
		return "true";
	}
	
		@GetMapping("/getbyemail")
		public Utilisateur userbyemail(String email) {
			Utilisateur u = this.userrepos.findByEmail(email);
			return u ;
		}
		@PostMapping("/changemp")
		public Utilisateur changemp(Long id,String password) {
			Utilisateur u = this.userrepos.findById(id).get();
			String pass = encoder.encode(password);
			u.setPassword(pass);
			this.userrepos.saveAndFlush(u);
			return u ;
}





@PostMapping("/update")
public String update(@RequestBody Utilisateur user ) {
	
	Utilisateur u =this.userrepos.findById(user.getId()).get();

	user.setDatecreation(u.getDatecreation());
	user.setAuthorities(u.getAuthorities());
	user.setArchiver(u.getArchiver());
	user.setPassword(u.getPassword());
	u=this.userrepos.save(user);
	return "true";

}



//afficher liste selon ville
	@GetMapping("/afficherlistbyville")
	public List<Utilisateur> afficherlist (String ville)
	{
		ville v=this.tabrepos.findByVille(ville);
		List<Utilisateur> Listutil= this.userrepos.findByVille(v);
		return Listutil;
		
	}
		
		//afficher liste selon specialite 
		@GetMapping("affichelistbyspecialite")
		public List<Utilisateur> afficherlistbyspec (String specialite)
		{
			Specialite S=this.specialiterepos.findBySpecialite(specialite);
			List<Utilisateur> Listutil= this.userrepos.findBySpecialite(S);
			return Listutil;
			
		}
		
		@GetMapping ("affichepagehome")
		public List<Utilisateur> affichelisthome (String ville , String specialite)
		{
			ville v=this.tabrepos.findByVille(ville);
			Specialite S=this.specialiterepos.findBySpecialite(specialite);
			List<Utilisateur> Listhome=this.userrepos.findByVilleAndSpecialiteAndProfilAndValiderIsTrue(v,S,"Docteur");
			return Listhome;
		}

		
		@PreAuthorize("hasAuthority('Admin')")
		@GetMapping ("affichebyprofil")
		public List<Utilisateur> affichelistbyprofil (String profil)
		{
			List<Utilisateur> Listhome=this.userrepos.findByProfilAndValiderIsTrue(profil);
			return Listhome;
		}
	
	@Autowired
	UserFormationRepository userformrepos ; 
	@Autowired
	FormationRepository formrepos ; 
	
	
	//listutilbyprofil
	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping ("affichebyprofilclient")
	public List<Utilisateur> affichelistbyprofilclient (String profil)
	{
		List<Utilisateur> Listclient=this.userrepos.findByProfil(profil);
		return Listclient;
	}


	@GetMapping("/afficherformationbyuser")
	public List<Userformation> listuserformation(String email){
		Utilisateur user = this.userrepos.findByEmail(email);
		return this.userformrepos.findByUser(user);
	}
	
	@GetMapping("/afficherformationbyiduser")
	public List<Userformation> listuserformationbyid(Long id){
		Utilisateur user = this.userrepos.findById(id).get();
		return this.userformrepos.findByUser(user);
	}
	
	//ajouter V2
	
	@PostMapping ("/ajouterV2")
		public String ajoutav2 (@RequestBody Userform userform) {
		
		ville V=this.tabrepos.findByVille(userform.getVille());
		Specialite S=this.specialiterepos.findBySpecialite(userform.getSpecialite());
		Utilisateur u=this.userrepos.findByEmail(userform.getU().getEmail());
		if(V == null || S==null ) 
		 {
			 return "n'existe pas";
		 }
		
		else if(u!=null) {
			return "email existe";
		  }
		
		 else {
			Autority auth = this.authrepos.findByName("Docteur");
			String pass = encoder.encode(userform.getU().getPassword());
			userform.getU().setValider(false);
			userform.getU().setPassword(pass);
			userform.getU().setDatecreation(new Date(System.currentTimeMillis()));	
			userform.getU().setVille (V);
			userform.getU().setProfil("Docteur");
			userform.getU().setArchiver(false);
			userform.getU().setAuthorities(auth);
			userform.getU().setSpecialite(S);
		     this.userrepos.save(userform.getU());
		    for (int i = 0; i < userform.getListformation().size(); i++) {
		    	Formation f1 = this.formrepos.save(userform.getListformation().get(i));
		   
		    	////insere une ligne 
		    	Userformation userformation = new Userformation();
		    	userformation.setFormation(f1);
		    	userformation.setUser(userform.getU());
		    	this.userformrepos.save(userformation);
		    	
			} 
		     return "table ajoute";
		     
		 }
		}
		
		
		
	//ajouter avec relation 
		@PostMapping ("/ajouter")
		public String ajoutavecrelation (@RequestBody Utilisateur U, String ville , String Specialite) {
		
		ville V=this.tabrepos.findByVille(ville);
		Specialite S=this.specialiterepos.findBySpecialite(Specialite);
		
		if(V == null || S==null ) 
		 {
			 return "n'existe pas";
		 }
		 else {
			Autority auth = this.authrepos.findByName(U.getProfil());
			String pass = encoder.encode(U.getPassword());
			U.setValider(false);
			U.setPassword(pass);
			U.setDatecreation(new Date(System.currentTimeMillis()));	
			U.setVille (V);
			U.setArchiver(false);
			U.setAuthorities(auth);
			U.setSpecialite(S);
		     this.userrepos.save(U);
		     return "table ajoute";
		     
		 }
		} 
	@PreAuthorize("hasAuthority('Admin')")
		@PutMapping("/valider")
		public String valider(Long id) {
			Utilisateur U=this.userrepos.findById(id).get();
			U.setValider(true);
			this.userrepos.saveAndFlush(U);
			return "true";
		}
	@PreAuthorize("hasAuthority('Admin')")
		//afficher valider false
		@GetMapping("/afficherfalse")
		public List<Utilisateur> listspecial(){
			return this.userrepos.findByValider(false);
		}
	

		//afficher valider true
				@GetMapping("/affichertrue")
				public List<Utilisateur> listspecialtrue(){
					return this.userrepos.findByValider(true);
				}
	
		 
  



}
