package com.test;

import com.test.model.Person;
import com.test.services.ClientServiceTransport;
import com.test.services.PersonCallback;
import com.test.services.PersonServiceTransport;
import com.test.services.ServiceCallback;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

public class MainTest {
    public static void main(String[] args){
        System.setProperty("zookeeper.connection", "localhost:2181");
        System.setProperty("service.root", "com.test");
        System.setProperty("service.port", "4242");
        System.setProperty("module.id", "main.server");
        System.setProperty("use.kafka.for.async", "true");
        System.setProperty("use.kafka.for.sync", "true");
        System.setProperty("bootstrap.servers", "localhost:9091,localhost:9092,localhost:9093");

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MainConfig.class);
        ctx.refresh();

        ClientServiceTransport clientService = ctx.getBean(ClientServiceTransport.class);
        PersonServiceTransport personService = ctx.getBean(PersonServiceTransport.class);

        Runnable runnable = () -> {
            Integer id = personService.add("James Carr", "james@zapier.com", null).withTimeout(15_000).onModule("main.server").executeSync();
            System.out.printf("Resulting id is %s", id);
            System.out.println();
            Person person = personService.get(id).onModule("main.server").executeSync();
            System.out.println(person);
            personService.lol().executeSync();
            personService.lol2("kek").executeSync();
            System.out.println("Name: " + personService.getName().executeSync());
            clientService.lol3("test3").onModule("main.server").executeSync();
            clientService.lol4("test4").onModule("main.server").executeSync();
            clientService.lol4("test4").onModule("main.server").executeAsync(UUID.randomUUID().toString(), ServiceCallback.class);
            personService.get(id).onModule("main.server").executeAsync(UUID.randomUUID().toString(), PersonCallback.class);
            personService.lol2("kek").executeSync();
            try {
                personService.testError().onModule("main.server").executeSync();
            } catch (Exception e) {
                System.out.println("Exception during sync call");
                e.printStackTrace();
            }
            personService.testError().onModule("main.server").executeAsync(UUID.randomUUID().toString(), PersonCallback.class);
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);

        thread1.start();
        thread2.start();
        thread3.start();

        try{
            thread1.join();
            thread2.join();
            thread3.join();
        }catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
