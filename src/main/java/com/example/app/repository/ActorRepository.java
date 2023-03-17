package com.example.app.repository;

import com.example.app.model.entity.ActorEntity;
import com.example.app.repository.custom.CustomActorRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Integer>, CustomActorRepository {
}
