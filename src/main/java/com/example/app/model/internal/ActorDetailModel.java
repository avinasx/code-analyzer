package com.example.app.model.internal;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ActorDetailModel {
    private Integer actorId;
    private String firstName;
    private String lastName;
    private String filmInfo;
}
