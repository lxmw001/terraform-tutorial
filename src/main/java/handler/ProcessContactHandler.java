package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import model.Contact;
import services.DynamoService;

import java.util.Map;

public class ProcessContactHandler implements RequestHandler<SNSEvent, Void> {

    public Void handleRequest(SNSEvent snsEvent, Context context) {
        Map<String, SNSEvent.MessageAttribute> attributes = snsEvent.getRecords().get(0).getSNS().getMessageAttributes();
        String id = attributes.get("id").getValue();

        Contact contact = DynamoService.getContact(id);
        contact.setProcessed();

        DynamoService.updateContact(contact);
        return null;
    }
}
