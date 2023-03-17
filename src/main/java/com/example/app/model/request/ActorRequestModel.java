package com.example.app.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActorRequestModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("firstName")
    @JacksonXmlProperty(localName = "firstName")
    private String firstName;

    @JsonProperty("lastName")
    @JacksonXmlProperty(localName = "lastName")
    private String lastName;
}
