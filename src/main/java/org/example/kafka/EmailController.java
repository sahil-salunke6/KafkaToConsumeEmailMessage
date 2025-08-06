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
        producer.sendEmail(notification); // async
        return ResponseEntity.ok("Request accepted - message is being sent to Kafka.");
    }

    curl -X POST "http://localhost:8080/email/send" \
            -H "Content-Type: application/json" \
            -d '{
            "to": ["recipient1@example.com", "recipient2@example.com"],
            "cc": ["cc1@example.com"],
            "bcc": ["bcc1@example.com"],
            "subject": "Notification Service Email",
            "body": "<p>This is a sample email body.</p>",
            "templateName": "welcome_template.html",
            "attachment": [
    {
        "fileName": "example.pdf",
            "fileType": "application/pdf",
            "fileContent": "base64EncodedContentHere"
    }
    ],
            "priority": "HIGH",
            "templateId": "welcome_email_template",
            "placeholders": {
        "userName": "John Doe",
                "activationLink": "https://example.com/activate"
    }
}'



        }
