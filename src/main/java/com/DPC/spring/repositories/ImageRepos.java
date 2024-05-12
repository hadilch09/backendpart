package com.DPC.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.DPC.spring.entities.Image;

public interface ImageRepos extends JpaRepository<Image, Long> {

}
