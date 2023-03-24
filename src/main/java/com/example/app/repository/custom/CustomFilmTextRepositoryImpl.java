package com.example.app.repository.custom;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@AllArgsConstructor
public class CustomFilmTextRepositoryImpl implements CustomFilmTextRepository {
}
