package com.example.app.services.catalog.domain.dto;

import com.example.app.common.domain.dto.FullName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.google.common.base.Objects;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ActorDto {
    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Actor {
        @JsonProperty(Fields.actorId)
        private Integer actorId;

        @JsonUnwrapped
        @JsonTypeInfo(use = JsonTypeInfo.Id.NONE, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "fullName")
        private FullName fullName;

        @JsonProperty(Fields.lastUpdate)
        private LocalDateTime lastUpdate;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final Actor that = (Actor) o;
            return Objects.equal(actorId, that.actorId)
                    && Objects.equal(fullName, that.fullName)
                    && Objects.equal(lastUpdate, that.lastUpdate);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(actorId, fullName, lastUpdate);
        }
    }

    @Getter
    @Setter
    @ToString
    @FieldNameConstants
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActorRequest implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @JsonProperty(Fields.firstName)
        private String firstName;

        @JsonProperty(Fields.lastName)
        private String lastName;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final ActorRequest that = (ActorRequest) o;
            return Objects.equal(firstName, that.firstName)
                    && Objects.equal(lastName, that.lastName);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(firstName, lastName);
        }
    }

    @Relation(collectionRelation = "actorList", itemRelation = "actor")
    @Getter
    @Setter
    @ToString
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActorResponse extends RepresentationModel<ActorResponse> implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @JsonUnwrapped
        private Actor actor;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            final ActorResponse that = (ActorResponse) o;
            return Objects.equal(actor, that.actor);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(super.hashCode(), actor);
        }
    }
}
