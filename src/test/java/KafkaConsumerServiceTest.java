import org.example.kafka.EmailNotification;
import org.example.service.KafkaConsumerService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class KafkaConsumerServiceTest {

    @Test
    void testConsumeLogsAllFields() {
        KafkaConsumerService consumerService = new KafkaConsumerService();

        EmailNotification.Attachment attachment = new EmailNotification.Attachment();
        attachment.setFileName("file.pdf");
        attachment.setFileType("application/pdf");
        attachment.setFileContent("content");

        EmailNotification notification = getEmailNotification(attachment);

        consumerService.consume(notification);

        // No exceptions thrown => successful coverage
    }

    private static EmailNotification getEmailNotification(EmailNotification.Attachment attachment) {
        EmailNotification notification = new EmailNotification();
        notification.setTo(List.of("to@example.com"));
        notification.setCc(List.of("cc@example.com"));
        notification.setBcc(List.of("bcc@example.com"));
        notification.setSubject("Test Subject");
        notification.setBody("Test Body");
        notification.setTemplateName("template.html");
        notification.setAttachment(List.of(attachment));
        notification.setPriority("HIGH");
        notification.setTemplateId("templateId");
        notification.setPlaceholders(Map.of("key", "value"));
        return notification;
    }

    @Test
    void testConsumeWithoutAttachments() {
        KafkaConsumerService consumerService = new KafkaConsumerService();

        EmailNotification notification = new EmailNotification();
        notification.setTo(List.of("to@example.com"));
        notification.setCc(List.of("cc@example.com"));
        notification.setBcc(List.of("bcc@example.com"));
        notification.setSubject("Test Subject");
        notification.setBody("Test Body");
        notification.setTemplateName("template.html");
        notification.setPriority("HIGH");
        notification.setTemplateId("templateId");
        notification.setPlaceholders(Map.of("key", "value"));
        notification.setAttachment(null); // covers null check

        consumerService.consume(notification);
    }
}
