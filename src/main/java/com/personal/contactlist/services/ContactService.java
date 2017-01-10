package com.personal.contactlist.services;

import com.personal.contactlist.domain.Contact;

import java.util.List;

/**
 * @author adhabriy
 */
public interface ContactService {

    List<Contact> findAllContacts();

    Contact create(Contact contact);

    void update(Contact contact);
}
