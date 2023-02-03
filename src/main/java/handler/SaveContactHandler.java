package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import model.Contact;
import services.DynamoService;

public class SaveContactHandler implements RequestHandler<Contact, Contact> {

    public Contact handleRequest(
            Contact contact, Context context) {

        return DynamoService.saveContact(contact);
    }
}
