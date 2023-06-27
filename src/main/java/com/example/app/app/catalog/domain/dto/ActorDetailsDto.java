package com.example.app.app.catalog.domain.dto;

import com.example.app.common.constant.HalRelation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;

public class ActorDetailsDto {
    /**
     * The actor detail model provides a list of films in which actors appear by category.
     */
    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActorDetails {
        /**
         * actor id
         */
        @JsonProperty(Fields.actorId)
        private Integer actorId;

        /**
         * actor first name
         */
        @JsonProperty(Fields.firstName)
        private String firstName;

        /**
         * actor last name
         */
        @JsonProperty(Fields.lastName)
        private String lastName;

        /**
         * a list of films in which the actor appears by category
         */
        @JsonProperty(Fields.filmInfo)
        private String filmInfo;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final ActorDetails that = (ActorDetails) o;
            return Objects.equal(actorId, that.actorId)
                    && Objects.equal(firstName, that.firstName)
                    && Objects.equal(lastName, that.lastName)
                    && Objects.equal(filmInfo, that.filmInfo);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(actorId, firstName, lastName, filmInfo);
        }
    }

    @Relation(collectionRelation = HalRelation.Fields.actorDetailsList,
            itemRelation = HalRelation.Fields.actorDetails)
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActorDetailsResponse extends RepresentationModel<ActorDetailsResponse> implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @JsonUnwrapped
        private ActorDetails actorDetailsModel;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            final ActorDetailsResponse that = (ActorDetailsResponse) o;
            return Objects.equal(actorDetailsModel, that.actorDetailsModel);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(super.hashCode(), actorDetailsModel);
        }
    }
}
