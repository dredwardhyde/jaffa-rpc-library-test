package com.test;

import com.test.model.Person;
import com.test.services.ClientServiceClient;
import com.test.services.PersonCallback;
import com.test.services.PersonServiceClient;
import com.test.services.ServiceCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MainTest {

    private static final int CONCURRENCY_LEVEL = 6;
    private static final String TARGET_MODULE_ID = "main.server";

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("jaffa-rpc-config", "./config.properties");
        ExecutorService executor = Executors.newFixedThreadPool(CONCURRENCY_LEVEL);

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MainConfig.class);
        ctx.refresh();

        ClientServiceClient clientService = ctx.getBean(ClientServiceClient.class);
        PersonServiceClient personService = ctx.getBean(PersonServiceClient.class);

        Runnable runnable = () -> {
            Integer id = personService.add("Test", "test@test.com", null)
                    .withTimeout(TimeUnit.MILLISECONDS.toMillis(15000))
                    .onModule(TARGET_MODULE_ID)
                    .executeSync();
            log.info("Resulting id is {}", id);
            Person person = personService.get(id)
                    .onModule(TARGET_MODULE_ID)
                    .executeSync();
            log.info(String.valueOf(person));
            personService.lol().executeSync();
            personService.lol2("kek").executeSync();
            String personName = personService.getName().executeSync();
            log.info("Name: {}", personName);
            clientService.lol3("test3")
                    .onModule(TARGET_MODULE_ID)
                    .executeSync();
            clientService.lol4("test4")
                    .onModule(TARGET_MODULE_ID)
                    .executeSync();
            clientService.lol4("test4")
                    .onModule(TARGET_MODULE_ID)
                    .executeAsync(UUID.randomUUID().toString(), ServiceCallback.class);
            personService.get(id)
                    .onModule(TARGET_MODULE_ID)
                    .executeAsync(UUID.randomUUID().toString(), PersonCallback.class);
            personService.lol2("kek").executeSync();
            try {
                personService.testError().onModule(TARGET_MODULE_ID).executeSync();
            } catch (Exception e) {
                log.error("Exception during sync call:", e);
            }
            personService.testError()
                    .onModule(TARGET_MODULE_ID)
                    .executeAsync(UUID.randomUUID().toString(), PersonCallback.class);
        };

        for(int i = 0; i < CONCURRENCY_LEVEL; i++) executor.execute(runnable);
        executor.awaitTermination(1, TimeUnit.MINUTES);
        executor.shutdown();
        ctx.close();
        System.exit(0);
    }
}