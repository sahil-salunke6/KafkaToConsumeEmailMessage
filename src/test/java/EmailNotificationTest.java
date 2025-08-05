import org.example.kafka.EmailNotification;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailNotificationTest {

    @Test
    void testGettersAndSetters() {
        EmailNotification notification = getEmailNotification();

        assertEquals("to@example.com", notification.getTo().get(0));
        assertEquals("cc@example.com", notification.getCc().get(0));
        assertEquals("bcc@example.com", notification.getBcc().get(0));
        assertEquals("Subject", notification.getSubject());
        assertEquals("Body", notification.getBody());
        assertEquals("template.html", notification.getTemplateName());
        assertEquals("HIGH", notification.getPriority());
        assertEquals("templateId", notification.getTemplateId());
        assertEquals("value", notification.getPlaceholders().get("key"));

        EmailNotification.Attachment attResult = notification.getAttachment().get(0);
        assertEquals("file.pdf", attResult.getFileName());
        assertEquals("application/pdf", attResult.getFileType());
        assertEquals("content", attResult.getFileContent());
    }

    private static EmailNotification getEmailNotification() {
        EmailNotification.Attachment attachment = new EmailNotification.Attachment();
        attachment.setFileName("file.pdf");
        attachment.setFileType("application/pdf");
        attachment.setFileContent("content");

        EmailNotification notification = new EmailNotification();
        notification.setTo(List.of("to@example.com"));
        notification.setCc(List.of("cc@example.com"));
        notification.setBcc(List.of("bcc@example.com"));
        notification.setSubject("Subject");
        notification.setBody("Body");
        notification.setTemplateName("template.html");
        notification.setAttachment(List.of(attachment));
        notification.setPriority("HIGH");
        notification.setTemplateId("templateId");
        notification.setPlaceholders(Map.of("key", "value"));
        return notification;
    }
}
