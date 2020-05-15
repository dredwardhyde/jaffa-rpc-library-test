package com.test.services;

import com.jaffa.rpc.lib.annotations.Api;
import com.test.model.Address;
import com.test.model.Person;

@Api
public interface PersonService {

    String TEST = "TEST";
    int add(String name, String email, Address address);
    Person get(Integer id);
    void lol();
    void lol2(String message);
    static void test(){
        System.out.println("Test");
    }
    String getName();
    Person testError();
}
