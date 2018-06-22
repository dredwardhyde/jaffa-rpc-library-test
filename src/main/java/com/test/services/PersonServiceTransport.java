package com.test.services;

import com.test.model.Address;
import com.test.model.Person;
import com.transport.lib.zeromq.ApiClient;
import com.transport.lib.zeromq.RequestInterface;

@ApiClient
public interface PersonServiceTransport {

    public RequestInterface<Integer> add(String name, String email, Address address);

    public RequestInterface<Person> get(Integer id);

    public RequestInterface<Void> lol();

    public RequestInterface<Void> lol2(String message);

    public RequestInterface<String> getName();
}

