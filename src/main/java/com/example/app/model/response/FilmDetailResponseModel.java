package com.example.app.model.response;

import com.example.app.model.internal.extra.FilmDetailModel;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

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
    private FilmDetailModel filmDetailModel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        FilmDetailResponseModel that = (FilmDetailResponseModel) o;
        return Objects.equal(filmDetailModel, that.filmDetailModel);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), filmDetailModel);
    }
}
