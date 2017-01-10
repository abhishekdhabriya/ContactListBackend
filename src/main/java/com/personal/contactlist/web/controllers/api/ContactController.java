package com.personal.contactlist.web.controllers.api;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.personal.contactlist.domain.Contact;
import com.personal.contactlist.services.ContactService;
import com.personal.contactlist.web.controllers.util.ContactSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * @author adhabriy
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactController {

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    ContactService contactService;

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public ResponseEntity<String> contacts() throws ParseException, IOException {
        List<Contact> contacts = contactService.findAllContacts();

        SimpleModule module = new SimpleModule();
        module.addSerializer(new ContactSerializer(Contact.class));
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.registerModule(module).writer(new DefaultPrettyPrinter()).writeValueAsString(contacts);
        final ResponseEntity<String> entity = new ResponseEntity<>(jsonResult, HttpStatus.OK);

        return entity;
    }


    @RequestMapping(value = "/contacts", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody Contact contact, Errors errors) throws ParseException, IOException {

        Contact savedContact = contactService.create(contact);

        // fromCurrentServletMapping
        final URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("api/contacts/{id}").build().expand(savedContact.getId()).toUri();
//        final URI location = MvcUriComponentsBuilder.fromMethodName(BookController.class, "findBookById", book.getId()).build().toUri();

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        headers.setContentType(MediaType.APPLICATION_JSON);
        final ResponseEntity<Void> entity = new ResponseEntity<>(headers, HttpStatus.CREATED);
        return entity;
    }

    @RequestMapping(value = "/contacts/{Id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@PathVariable Long Id, @RequestBody Contact contact, Errors errors) throws ParseException, IOException {

        //Simulate that we are going to first read the Contact with the given Id
        // then we need to update the contact object with the incoming modified field.
        // We do this because request might have incomplete object or updating some fields which aren't allowed.

//        Contact preUpdateContact = contactService.findOne(Id);
//        this.updateContactFields(preUpdateContact, contact);
        log.info("contact {}, {}, {}, {}", contact.getId(), contact.getName(), contact.getEmail(), contact.getPhone());

        contactService.update(contact);
        final ResponseEntity<Void> entity = new ResponseEntity<>(HttpStatus.OK);
        return entity;
    }
}
