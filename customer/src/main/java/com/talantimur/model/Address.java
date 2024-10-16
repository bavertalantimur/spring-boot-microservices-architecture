package com.talantimur.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Validated
public class Address {
    public String street;
    private String houseNumber;
    private  String zipCode;
}
