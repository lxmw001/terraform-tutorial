package services;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import constants.Aws;
import exceptions.ContactNotFoundException;
import model.Contact;

public class DynamoService {

    private static DynamoDBMapper mapper;


    private static void initDynamoDbClient() {
        if(mapper == null) {
            AmazonDynamoDB client = new AmazonDynamoDBClient();
            client.setRegion(Region.getRegion(Aws.REGION));
            mapper = new DynamoDBMapper(client);
        }
    }

    public static Contact getContact(String contactId) {
        initDynamoDbClient();

        //Get contact from dynamo
        Contact result = mapper.load(Contact.class, contactId);
        if (result == null) {
            throw new ContactNotFoundException("Contact not found for id: " + contactId);
        }

        return result;
    }

    public static Contact saveContact(Contact contact) {
        initDynamoDbClient();

        contact.setCreated();

        // Save the contact
        mapper.save(contact);

        System.out.println("Contact created!\n " + contact);
        return contact;
    }

    public static Contact updateContact(Contact contact) {
        initDynamoDbClient();

        // Update the contact
        mapper.save(contact);

        System.out.println("Contact updated!\n " + contact);
        return null;
    }
}
