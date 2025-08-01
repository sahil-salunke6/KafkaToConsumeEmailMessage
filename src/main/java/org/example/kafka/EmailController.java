package org.example.kafka;

import org.example.service.KafkaProducerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final KafkaProducerService producer;

    public EmailController(KafkaProducerService producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailNotification notification) {
        producer.sendEmail(notification);
        return "Email message sent to Kafka!";
    }
}
