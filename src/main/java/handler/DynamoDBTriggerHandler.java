package handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import enums.Status;
import model.Contact;
import services.SnsService;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;

import java.util.HashMap;
import java.util.Map;

public class DynamoDBTriggerHandler implements RequestHandler<DynamodbEvent, Void> {
    @Override
    public Void handleRequest(DynamodbEvent dynamodbEvent, Context context) {
        if (dynamodbEvent.getRecords() == null || dynamodbEvent.getRecords().isEmpty()) {
            System.out.println("No records to process!");
            return null;
        }

        for (DynamodbEvent.DynamodbStreamRecord record : dynamodbEvent.getRecords()) {
            if (record.getEventName().equals("INSERT")) {
                 String id = record.getDynamodb().getNewImage().get("id").getS();
                 String firstName = record.getDynamodb().getNewImage().get("firstName").getS();
                 String lastName = record.getDynamodb().getNewImage().get("lastName").getS();
                 String status = record.getDynamodb().getNewImage().get("status").getS();

                Contact contact = new Contact(id, firstName, lastName, Status.valueOf(status));

                String message = "Contact created: \n" + contact;

                Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
                messageAttributes.put("id", MessageAttributeValue.builder().dataType("String").stringValue(id).build());

                SnsService.send(message, messageAttributes);
            }
        }
        return null;
    }
}
