package com.personal.contactlist.services.impl;

import com.personal.contactlist.domain.Contact;
import com.personal.contactlist.services.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adhabriy
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactServiceImpl.class);
    private List<Contact> contacts;

    public ContactServiceImpl() {

        // test data

        Contact contact1 = new Contact(1l, "Rachel Green", "rachel.green@gmail.com", "888-123-5676");
        Contact contact2 = new Contact(2l, "Monica Geller", "monica.geller@gmail.com", "777-123-5676");
        Contact contact3 = new Contact(3l, "Joey Tribbiani", "joey.tribbiani@gmail.com", "666-123-5676");
        Contact contact4 = new Contact(4l, "Chandler Bing", "chandler.bing@gmail.com", "555-123-5676");
        Contact contact5 = new Contact(5l, "Ross Geller", "ross.geller@gmail.com", "444-123-5676");
        Contact contact6 = new Contact(6l, "Phoebe Buffay", "phoebe.buffay@gmail.com", "333-123-5676");

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact1);
        contacts.add(contact2);
        contacts.add(contact3);
        contacts.add(contact4);
        contacts.add(contact5);
        contacts.add(contact6);

        this.contacts = contacts;

    }


    public List<Contact> findAllContacts() {
        return this.contacts;
    }


    public Contact create(Contact contact) {
         contact.setId(this.contacts.size() + 1l);

        this.contacts.add(contact);
        return contact;
    }

    public void update(Contact contact) {
        log.info("contact {}, {}, {}, {}", contact.getId(), contact.getName(), contact.getEmail(), contact.getPhone());


        for(Contact c : this.contacts){
            if(c.getId() == contact.getId()){
                c.setName(contact.getName());
                c.setEmail(contact.getEmail());
                c.setPhone(contact.getPhone());
            }
        }


        this.contacts.stream().forEach((c) -> {
            log.info("contact {}, {}, {}, {}", c.getId(), c.getName(), c.getEmail(), c.getPhone());
        });
    }
}
