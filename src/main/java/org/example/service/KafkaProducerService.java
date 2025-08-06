package org.example.service;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.example.kafka.EmailNotification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, EmailNotification> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, EmailNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmail(EmailNotification notification) {
        CompletableFuture<SendResult<String, EmailNotification>> future =
                kafkaTemplate.send("email-notification", notification);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                RecordMetadata metadata = result.getRecordMetadata();
                System.out.println("Message sent successfully to topic: " + metadata.topic() +
                        ", partition: " + metadata.partition() +
                        ", offset: " + metadata.offset());
            } else {
                System.err.println("Failed to send message: " + ex.getMessage());
            }
        });
    }
}
