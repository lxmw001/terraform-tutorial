package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import model.Contact;
import model.ContactByIdRequest;
import services.DynamoService;

public class GetContactHandler implements RequestHandler<ContactByIdRequest, Contact> {

    @Override
    public Contact handleRequest(
            ContactByIdRequest contactByIdRequest, Context context) {

        return DynamoService.getContact(contactByIdRequest.getContactId());
    }
}
