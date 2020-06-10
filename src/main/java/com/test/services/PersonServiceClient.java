package com.test.services;

import com.jaffa.rpc.lib.annotations.ApiClient;
import com.jaffa.rpc.lib.request.Request;
import com.test.model.Address;
import com.test.model.Person;

@ApiClient(ticketProvider = TicketProviderImpl.class)
public interface PersonServiceClient {

    Request<Integer> add(String name, String email, Address address);

    Request<Person> get(Integer id);

    Request<Void> lol();

    Request<Void> lol2(String message);

    Request<String> getName();

    Request<Person> testError();
}

