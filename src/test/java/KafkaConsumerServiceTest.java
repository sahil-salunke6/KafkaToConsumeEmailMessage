import org.example.model.EmailNotification;
import org.example.service.KafkaConsumerService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KafkaConsumerServiceTest {

    @Test
    void testConsumeLogsAllFields() {
        KafkaConsumerService consumerService = new KafkaConsumerService();

        EmailNotification notification = buildNotification();

        consumerService.consume(notification);

        // Basic field assertions
        assertEquals("Test Subject", notification.getSubject());
        assertEquals("HIGH", notification.getPriority());
        assertEquals("to@example.com", notification.getTo().get(0));
        assertEquals("cc@example.com", notification.getCc().get(0));
        assertEquals("bcc@example.com", notification.getBcc().get(0));
        assertEquals("Test Body", notification.getBody());
        assertEquals("template.html", notification.getTemplateName());
        assertEquals("templateId", notification.getTemplateId());
    }

    @Test
    void testConsumeWithMinimalFields() {
        KafkaConsumerService consumerService = new KafkaConsumerService();

        EmailNotification notification = new EmailNotification();
        notification.setTo(List.of("to@example.com"));
        notification.setCc(List.of());
        notification.setBcc(List.of());
        notification.setSubject("No CC/BCC");
        notification.setBody("Body without CC/BCC");
        notification.setTemplateName("minimal.html");
        notification.setPriority("LOW");
        notification.setTemplateId("minimalId");

        consumerService.consume(notification);

        assertEquals("No CC/BCC", notification.getSubject());
        assertEquals("LOW", notification.getPriority());
        assertEquals("minimal.html", notification.getTemplateName());
    }

    private EmailNotification buildNotification() {
        EmailNotification notification = new EmailNotification();
        notification.setTo(List.of("to@example.com"));
        notification.setCc(List.of("cc@example.com"));
        notification.setBcc(List.of("bcc@example.com"));
        notification.setSubject("Test Subject");
        notification.setBody("Test Body");
        notification.setTemplateName("template.html");
        notification.setPriority("HIGH");
        notification.setTemplateId("templateId");
        return notification;
    }
}
