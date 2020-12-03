package com.test;

import com.jaffa.rpc.lib.configuration.JaffaRpcConfig;
import com.jaffa.rpc.lib.spring.ClientEndpoint;
import com.jaffa.rpc.lib.spring.ServerEndpoints;
import com.test.services.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(JaffaRpcConfig.class)
@SuppressWarnings("unused")
public class MainConfig {
    @Bean
    ServerEndpoints serverEndpoints() {
        return new ServerEndpoints(PersonServiceImpl.class, ClientServiceImpl.class);
    }

    @Bean
    public ClientEndpoint clientEndpoint() {
        return new ClientEndpoint(ClientServiceClient.class, TicketProviderImpl.class);
    }

    @Bean
    public ClientEndpoint personEndpoint() {
        return new ClientEndpoint(PersonServiceClient.class);
    }
}
