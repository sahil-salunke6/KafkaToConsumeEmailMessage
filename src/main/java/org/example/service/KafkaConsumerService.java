package org.example.service;

import org.example.model.EmailNotification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class KafkaConsumerService {

    private static final Logger log = Logger.getLogger(KafkaConsumerService.class.getName());

    @KafkaListener(topics = "email-notification", groupId = "email-consumer-group")
    public void consume(EmailNotification notification) {
        log.info("Consumed Email Notification:");
        log.info("To: " + notification.getTo());
        log.info("CC: " + notification.getCc());
        log.info("BCC: " + notification.getBcc());
        log.info("Subject: " + notification.getSubject());
        log.info("Body: " + notification.getBody());
        log.info("Template: " + notification.getTemplateName());
        log.info("Priority: " + notification.getPriority());
        log.info("TemplateId: " + notification.getTemplateId());
    }
}
