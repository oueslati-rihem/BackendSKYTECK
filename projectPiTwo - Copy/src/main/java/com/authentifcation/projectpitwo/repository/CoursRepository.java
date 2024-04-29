package com.authentifcation.projectpitwo.repository;

import com.authentifcation.projectpitwo.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
    List<Cours> findByUser_id(Integer userId);
}
