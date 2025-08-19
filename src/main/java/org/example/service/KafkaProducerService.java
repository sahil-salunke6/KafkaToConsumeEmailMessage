package org.example.service;

import org.example.model.EmailNotification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, EmailNotification> kafkaTemplate;
    private static final Logger log = Logger.getLogger(KafkaProducerService.class.getName());


    public KafkaProducerService(KafkaTemplate<String, EmailNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmail(EmailNotification notification) {
        try {
            kafkaTemplate.send("email-notification", notification);
            log.info("Sent");
        } catch (Exception e) {
            log.info("Exception");
        }

    }
}
