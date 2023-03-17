package com.example.app.model.response.reserved;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FilmTextResponseModel extends RepresentationModel<FilmTextResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("filmId")
    @JacksonXmlProperty(localName = "filmId")
    private Integer filmId;

    @JsonProperty("title")
    @JacksonXmlProperty(localName = "title")
    private String title;

    @JsonProperty("description")
    @JacksonXmlProperty(localName = "description")
    private String description;
}
