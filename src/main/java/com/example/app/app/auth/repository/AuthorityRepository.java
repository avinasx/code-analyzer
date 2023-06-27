package com.example.app.app.auth.repository;

import com.example.app.app.auth.domain.entity.AuthorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<AuthorityEntity, Integer> {
    AuthorityEntity findAuthorityEntityByEmail(String email);
}
