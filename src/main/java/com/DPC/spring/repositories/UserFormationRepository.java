package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Userformation;
import com.DPC.spring.entities.Utilisateur;

public interface UserFormationRepository extends JpaRepository<Userformation,Long> {

	List<Userformation> findByUser(Utilisateur user);

}
