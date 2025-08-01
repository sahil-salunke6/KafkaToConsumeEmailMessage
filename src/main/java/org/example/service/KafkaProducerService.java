package org.example.service;

import org.example.kafka.EmailNotification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, EmailNotification> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, EmailNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmail(EmailNotification notification) {
        kafkaTemplate.send("email-notification", notification);
    }
}
