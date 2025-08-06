package org.example.service;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.example.kafka.EmailNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaProducerService.class);
    private static final String TOPIC_NAME = "email-notification";

    private final KafkaTemplate<String, EmailNotification> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, EmailNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEmail(EmailNotification notification) {
        CompletableFuture<SendResult<String, EmailNotification>> future =
                kafkaTemplate.send(TOPIC_NAME, notification);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("EmailNotification sent successfully to topic={} partition={} offset={}",
                        metadata.topic(), metadata.partition(), metadata.offset());
            } else {
                log.error("Failed to send EmailNotification to Kafka topic={}", TOPIC_NAME, ex);
            }
        });
    }
}
