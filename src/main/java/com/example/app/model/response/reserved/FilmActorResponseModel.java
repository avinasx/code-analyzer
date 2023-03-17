package com.example.app.model.response.reserved;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilmActorResponseModel extends RepresentationModel<FilmActorResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("actorId")
    @JacksonXmlProperty(localName = "actorId")
    private Integer actorId;

    @JsonProperty("filmId")
    @JacksonXmlProperty(localName = "filmId")
    private Integer filmId;

    @JsonProperty("lastUpdate")
    @JacksonXmlProperty(localName = "lastUpdate")
    private LocalDateTime lastUpdate;
}
