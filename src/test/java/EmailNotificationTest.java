import org.example.model.EmailNotification;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    }

    private static EmailNotification getEmailNotification() {
        EmailNotification notification = new EmailNotification();
        notification.setTo(List.of("to@example.com"));
        notification.setCc(List.of("cc@example.com"));
        notification.setBcc(List.of("bcc@example.com"));
        notification.setSubject("Subject");
        notification.setBody("Body");
        notification.setTemplateName("template.html");
        notification.setPriority("HIGH");
        notification.setTemplateId("templateId");
        return notification;
    }
}
