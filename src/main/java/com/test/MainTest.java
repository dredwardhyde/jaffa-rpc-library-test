package com.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest {
    public static void main(String[] args){
        System.setProperty("zookeeper.connection", "localhost:2181");
        System.setProperty("service.root", "com.test");
        System.setProperty("service.port", "4242");
        System.setProperty("module.id", "main.server");

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MainConfig.class);
        ctx.refresh();

        ClientServiceTransport clientServiceTransport = ctx.getBean(ClientServiceTransport.class);
        PersonServiceTransport personServiceTransport = ctx.getBean(PersonServiceTransport.class);

        Integer id = personServiceTransport.add("James Carr", "james@zapier.com", null).withTimeout(10_000).onModule("main.server").execute();
        System.out.printf("Resulting id is %s", id);
        System.out.println();
        Person person = personServiceTransport.get(id).onModule("main.server").execute();
        System.out.println(person);
        personServiceTransport.lol().execute();
        personServiceTransport.lol2("kek").execute();
        System.out.println("Name: "  + personServiceTransport.getName().execute());

        clientServiceTransport.lol3("test3").onModule("main.server").execute();
        clientServiceTransport.lol4("test4").onModule("main.server").execute();
    }

}
