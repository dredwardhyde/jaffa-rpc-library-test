package com.test.services;

import com.jaffa.rpc.lib.annotations.ApiClient;
import com.jaffa.rpc.lib.request.Request;

@ApiClient(ticketProvider = TicketProviderImpl.class)
public interface ClientServiceClient {

    Request<Void> lol3(String message);

    Request<Void> lol4(String message);
}

