package com.test.model;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Address implements Serializable {
    private String zip;
    private String street;
    private String flat;
    private String city;
}
