package com.personal.contactlist.web.controllers.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.personal.contactlist.domain.Contact;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author adhabriy
 */
public class ContactSerializer extends StdSerializer<Contact> {

    public ContactSerializer(Class t) {
        super(t);
    }

    @Override
    public void serialize(Contact contact, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", Long.toString(contact.getId()));
        jsonGenerator.writeStringField("name", contact.getName() != null ? contact.getName() : null);
        jsonGenerator.writeObjectField("email" , contact.getEmail() != null ? contact.getEmail() : null);
        jsonGenerator.writeObjectField("phone" , contact.getPhone() != null ? contact.getPhone() : null);
        jsonGenerator.writeEndObject();
    }
}
