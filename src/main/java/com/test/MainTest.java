package com.test;

import com.test.model.Person;
import com.test.services.ClientServiceClient;
import com.test.services.PersonCallback;
import com.test.services.PersonServiceClient;
import com.test.services.ServiceCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class MainTest {

    private static final int CONCURRENCY_LEVEL = 6;

    public static void main(String[] args) {
        System.setProperty("jaffa-rpc-config", "./jaffa-rpc-config-test-server.properties");

        List<Thread> threadList = new ArrayList<>();

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MainConfig.class);
        ctx.refresh();

        ClientServiceClient clientService = ctx.getBean(ClientServiceClient.class);
        PersonServiceClient personService = ctx.getBean(PersonServiceClient.class);

        Runnable runnable = () -> {
            Integer id = personService.add("James Carr", "james@zapier.com", null).withTimeout(15_000).onModule("main.server").executeSync();
            log.info("Resulting id is {}", id);
            Person person = personService.get(id).onModule("main.server").executeSync();
            log.info(String.valueOf(person));
            personService.lol().executeSync();
            personService.lol2("kek").executeSync();
            String personName = personService.getName().executeSync();
            log.info("Name: {}", personName);
            clientService.lol3("test3").onModule("main.server").executeSync();
            clientService.lol4("test4").onModule("main.server").executeSync();
            clientService.lol4("test4").onModule("main.server").executeAsync(UUID.randomUUID().toString(), ServiceCallback.class);
            personService.get(id).onModule("main.server").executeAsync(UUID.randomUUID().toString(), PersonCallback.class);
            personService.lol2("kek").executeSync();
            try {
                personService.testError().onModule("main.server").executeSync();
            } catch (Exception e) {
                log.error("Exception during sync call:", e);
            }
            personService.testError().onModule("main.server").executeAsync(UUID.randomUUID().toString(), PersonCallback.class);
        };

        for (int i = 0; i < CONCURRENCY_LEVEL; i++) {
            threadList.add(new Thread(runnable));
        }

        threadList.forEach(Thread::start);

        try {
            threadList.forEach(thread -> {
                try {
                    thread.join();
                } catch (Exception e) {
                    log.error("Error occurred while stopping threads", e);
                }
            });
            Thread.sleep(1000);
        } catch (Exception ignore) {
        }

        ctx.close();

        System.exit(0);
    }
}