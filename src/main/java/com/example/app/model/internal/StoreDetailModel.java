package com.example.app.model.internal;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoreDetailModel {
    private Integer id;
    private String store;
    private String manager;
    private String address;
}
