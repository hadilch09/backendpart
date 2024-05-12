package com.DPC.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Question;
import com.DPC.spring.entities.Reponse;

public interface ReponseRepos extends JpaRepository<Reponse, Long>{

	List<Reponse> findByQuestion(Question q);

}
