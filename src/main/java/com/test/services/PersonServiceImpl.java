package com.test.services;

import com.test.model.Address;
import com.test.model.Person;
import com.transport.lib.common.ApiServer;
import com.transport.lib.common.TransportContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ApiServer
public class PersonServiceImpl implements PersonService{

    private static Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    private List<Person> people = new ArrayList<>();

    private AtomicInteger idProvider = new AtomicInteger(1);

    private void logMeta() {
        logger.info("SOURCE MODULE ID: {} MY MODULE ID: {}", TransportContext.getSourceModuleId(), System.getProperty("module.id"));
        logger.info("TICKET: {}", TransportContext.getTicket());
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

    public void lol(){
        logMeta();
        logger.info("Lol");
    }

    public void lol2(String message){
        logger.info(message);
    }

    public String getName(){
        return null;
    }

    public String testError(){
        throw new RuntimeException("very bad in " + System.getProperty("module.id"));
    }
}