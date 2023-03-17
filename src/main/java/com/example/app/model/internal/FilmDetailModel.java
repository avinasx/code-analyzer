package com.example.app.model.internal;

import com.example.app.model.enumeration.Category;
import com.example.app.model.enumeration.FilmRating;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FilmDetailModel {
    private Integer fid;
    private String title;
    private String description;
    private Category category;
    private BigDecimal price;
    private Integer length;
    private FilmRating rating;
    private String actors;
}
