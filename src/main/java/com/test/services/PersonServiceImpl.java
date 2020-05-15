package com.test.services;

import com.jaffa.rpc.lib.annotations.ApiServer;
import com.jaffa.rpc.lib.entities.RequestContext;
import com.test.model.Address;
import com.test.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ApiServer
@Slf4j
@Component
public class PersonServiceImpl implements PersonService {

    private final List<Person> people = new ArrayList<>();

    private final AtomicInteger idProvider = new AtomicInteger(1);

    private void logMeta() {
        log.info("SOURCE MODULE ID: {} MY MODULE ID: {}", RequestContext.getSourceModuleId(), System.getProperty("jaffa.rpc.module.id"));
        log.info("TICKET: {}", RequestContext.getTicket());
    }

    public int add(String name, String email, Address address) {
        logMeta();
        Person p = new Person();
        p.setEmail(email);
        p.setName(name);
        p.setId(idProvider.addAndGet(1));
        p.setAddress(address);
        people.add(p);
        return p.getId();
    }

    public Person get(final Integer id) {
        logMeta();
        for (Person p : this.people) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public void lol() {
        logMeta();
        log.info("Lol");
    }

    public void lol2(String message) {
        log.info(message);
    }

    public String getName() {
        return null;
    }

    public Person testError() {
        throw new RuntimeException("very bad in " + System.getProperty("jaffa.rpc.module.id"));
    }
}