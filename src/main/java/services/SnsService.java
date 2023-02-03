package services;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.sns.AmazonSNS;
//import com.amazonaws.services.sns.model.PublishRequest;
//import com.amazonaws.services.sns.model.PublishResult;
import constants.Aws;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.MessageAttributeValue;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.util.Map;

public class SnsService {

    public static void send(String message, Map<String, MessageAttributeValue> messageAttributes) {
        SnsClient snsClient = SnsClient.builder()
                .region(Region.US_EAST_1)
                .build();

        pubTopic(snsClient, message, messageAttributes);
        snsClient.close();
    }

    public static void pubTopic(SnsClient snsClient, String message, Map<String, MessageAttributeValue> messageAttributes) {
        try {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .messageAttributes(messageAttributes)
                    .topicArn(Aws.TOPIC_ARN)
                    .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status was " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
