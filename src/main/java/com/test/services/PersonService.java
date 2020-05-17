package com.test.services;

import com.jaffa.rpc.lib.annotations.Api;
import com.test.model.Address;
import com.test.model.Person;

@Api
@SuppressWarnings({"squid:S1845", "squid:S106", "squid:S1214", "unused"})
public interface PersonService {

    String TEST = "TEST";

    static void test() {
        System.out.println("Test");
    }

    int add(String name, String email, Address address);

    Person get(Integer id);

    void lol();

    void lol2(String message);

    String getName();

    Person testError();
}
