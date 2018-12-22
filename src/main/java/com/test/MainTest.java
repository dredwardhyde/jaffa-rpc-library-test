package com.test;

import com.test.model.Person;
import com.test.services.ClientServiceTransport;
import com.test.services.PersonCallback;
import com.test.services.PersonServiceTransport;
import com.test.services.ServiceCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainTest {

    private static Logger logger = LoggerFactory.getLogger(MainTest.class);

    private static final int CONCURRENCY_LEVEL = 6;

    public static void main(String[] args){
        System.setProperty("zookeeper.connection", "localhost:2181");
        System.setProperty("service.root", "com.test");
        System.setProperty("service.port", "4242");
        System.setProperty("module.id", "main.server");
        System.setProperty("use.kafka.for.async", "true");
        System.setProperty("use.kafka.for.sync", "true");
        System.setProperty("bootstrap.servers", "localhost:9091,localhost:9092,localhost:9093");

        List<Thread> threadList = new ArrayList<>();

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MainConfig.class);
        ctx.refresh();

        ClientServiceTransport clientService = ctx.getBean(ClientServiceTransport.class);
        PersonServiceTransport personService = ctx.getBean(PersonServiceTransport.class);

        Runnable runnable = () -> {
            Integer id = personService.add("James Carr", "james@zapier.com", null).withTimeout(15_000).onModule("main.server").executeSync();
            logger.info("Resulting id is " + id);
            Person person = personService.get(id).onModule("main.server").executeSync();
            logger.info(person.toString());
            personService.lol().executeSync();
            personService.lol2("kek").executeSync();
            logger.info("Name: " + personService.getName().executeSync());
            clientService.lol3("test3").onModule("main.server").executeSync();
            clientService.lol4("test4").onModule("main.server").executeSync();
            clientService.lol4("test4").onModule("main.server").executeAsync(UUID.randomUUID().toString(), ServiceCallback.class);
            personService.get(id).onModule("main.server").executeAsync(UUID.randomUUID().toString(), PersonCallback.class);
            personService.lol2("kek").executeSync();
            try {
                personService.testError().onModule("main.server").executeSync();
            } catch (Exception e) {
                logger.error("Exception during sync call:", e);
            }
            personService.testError().onModule("main.server").executeAsync(UUID.randomUUID().toString(), PersonCallback.class);
        };

        for(int i = 0; i < CONCURRENCY_LEVEL; i++){ threadList.add(new Thread(runnable)); }

        threadList.forEach(Thread::start);

        try{
            threadList.forEach(thread -> {try{thread.join();} catch (Exception e){e.printStackTrace();}});
            Thread.sleep(1000);
        }catch (Exception ignore) { }

        ctx.close();

        System.exit(0);
    }
}