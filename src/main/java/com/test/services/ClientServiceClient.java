package com.test.services;

import com.jaffa.rpc.lib.annotations.ApiClient;
import com.jaffa.rpc.lib.request.Request;

@ApiClient
@SuppressWarnings("unused")
public interface ClientServiceClient {

    Request<Void> lol3(String message);

    Request<Void> lol4(String message);
}

