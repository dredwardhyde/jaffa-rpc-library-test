package com.test.services;

import com.jaffa.rpc.lib.annotations.ApiServer;
import com.jaffa.rpc.lib.entities.RequestContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@ApiServer
@Component
public class ClientServiceImpl implements ClientService {

    private void logMeta() {
        log.info("SOURCE MODULE ID: {} MY MODULE ID: {}", RequestContextHelper.getSourceModuleId(), System.getProperty("module.id"));
        log.info("TICKET: {}", RequestContextHelper.getTicket());
    }

    @Override
    public void lol3(String message) {
        logMeta();
        log.info("lol3 {}", message);
    }

    @Override
    public void lol4(String message) {
        logMeta();
        log.info("lol4 {}", message);
    }
}
