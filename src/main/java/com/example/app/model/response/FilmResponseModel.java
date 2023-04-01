package com.example.app.model.response;

import com.example.app.model.constant.HalRelation;
import com.example.app.model.internal.core.FilmModel;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

@Relation(collectionRelation = HalRelation.Fields.filmList,
        itemRelation = HalRelation.Fields.film)
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmResponseModel extends RepresentationModel<FilmResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private FilmModel filmModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FilmResponseModel that = (FilmResponseModel) o;
        return Objects.equal(filmModel, that.filmModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), filmModel);
    }
}
