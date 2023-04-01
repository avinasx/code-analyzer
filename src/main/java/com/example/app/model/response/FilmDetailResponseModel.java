package com.example.app.model.response;

import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.extra.FilmDetailsModel;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

@Relation(collectionRelation = HalRelation.Fields.filmDetailsList,
        itemRelation = HalRelation.Fields.filmDetails)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmDetailResponseModel extends RepresentationModel<FilmDetailResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private FilmDetailsModel filmDetailsModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FilmDetailResponseModel that = (FilmDetailResponseModel) o;
        return Objects.equal(filmDetailsModel, that.filmDetailsModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), filmDetailsModel);
    }
}
