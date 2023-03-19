package com.example.app.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
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
public class StoreDetailResponseModel extends RepresentationModel<StoreDetailResponseModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    @JacksonXmlProperty(localName = "id")
    private Integer id;

    @JsonProperty("store")
    @JacksonXmlProperty(localName = "store")
    private String store;

    @JsonProperty("manager")
    @JacksonXmlProperty(localName = "manager")
    private String manager;

    @JsonProperty("address")
    @JacksonXmlProperty(localName = "address")
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StoreDetailResponseModel that = (StoreDetailResponseModel) o;
        return Objects.equal(id, that.id)
                && Objects.equal(store, that.store)
                && Objects.equal(manager, that.manager)
                && Objects.equal(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, store, manager, address);
    }
}
