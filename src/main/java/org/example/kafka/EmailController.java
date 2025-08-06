package org.example.kafka;

import org.example.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final KafkaProducerService producer;

    public EmailController(KafkaProducerService producer) {
        this.producer = producer;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailNotification notification) {
        producer.sendEmail(notification);
        return ResponseEntity.ok("Request accepted - message is being sent.");
    }
}
