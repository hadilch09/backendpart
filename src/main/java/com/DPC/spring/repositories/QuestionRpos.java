package com.DPC.spring.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Question;

public interface QuestionRpos extends JpaRepository<Question, Long> {

	Question findByQuestion(String question);

}
