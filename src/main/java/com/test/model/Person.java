package com.test.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Person {
    private int id;
    private String name;
    private String email;
    private String twitter;
    private Address address;
}