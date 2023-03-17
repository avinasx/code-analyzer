package com.example.app.model.internal;

import com.example.app.model.enumeration.Country;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StaffDetailModel {
    private Integer id;
    private String name;
    private String address;
    private String zipCode;
    private String phone;
    private String city;
    private Country country;
    private Integer sid;
}
