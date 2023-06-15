package com.example.app.app.location.repository;

import com.example.app.app.location.domain.entity.LanguageEntity;
import com.example.app.app.location.repository.custom.CustomLanguageRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer>, CustomLanguageRepository {
}
