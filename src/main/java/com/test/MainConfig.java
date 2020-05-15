package com.test;

import com.jaffa.rpc.lib.configuration.JaffaRpcConfig;
import com.jaffa.rpc.lib.spring.ClientEndpoints;
import com.jaffa.rpc.lib.spring.ServerEndpoints;
import com.test.services.ClientServiceClient;
import com.test.services.ClientServiceImpl;
import com.test.services.PersonServiceClient;
import com.test.services.PersonServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan
@Import(JaffaRpcConfig.class)
public class MainConfig {
    @Bean
    ServerEndpoints serverEndpoints() {
        return new ServerEndpoints(PersonServiceImpl.class, ClientServiceImpl.class);
    }

    @Bean
    ClientEndpoints clientEndpoints() {
        return new ClientEndpoints(ClientServiceClient.class, PersonServiceClient.class);
    }
}
