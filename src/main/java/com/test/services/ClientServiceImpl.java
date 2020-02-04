package com.test.services;

import com.transport.lib.common.ApiServer;
import com.transport.lib.common.TransportContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApiServer
public class ClientServiceImpl implements ClientService {

    private static Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    private void logMeta() {
        logger.info("SOURCE MODULE ID: {} MY MODULE ID: {}", TransportContext.getSourceModuleId(), System.getProperty("module.id"));
        logger.info("TICKET: {}", TransportContext.getTicket());
    }

    @Override
    public void lol3(String message) {
        logMeta();
        logger.info("lol3 {}", message);
    }

    @Override
    public void lol4(String message) {
        logMeta();
        logger.info("lol4 {}", message);
    }
}
