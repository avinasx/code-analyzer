package com.example.app.model.response.reserved;

import com.example.app.model.enumeration.Category;
import com.example.app.model.mapping.CategoryConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.Convert;
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
public class CategoryResponseModel extends RepresentationModel<CategoryResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("categoryId")
    @JacksonXmlProperty(localName = "categoryId")
    @Convert(converter = CategoryConverter.class)
    private Category categoryId;

    @JsonProperty("name")
    @JacksonXmlProperty(localName = "name")
    private String name;

    @JsonProperty("lastUpdate")
    @JacksonXmlProperty(localName = "lastUpdate")
    private LocalDateTime lastUpdate;
}
