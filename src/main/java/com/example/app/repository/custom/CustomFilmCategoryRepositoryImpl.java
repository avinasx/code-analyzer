package com.example.app.repository.custom;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
@AllArgsConstructor
public class CustomFilmCategoryRepositoryImpl implements CustomFilmCategoryRepository {
}
