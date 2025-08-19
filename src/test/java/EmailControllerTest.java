import org.example.controller.EmailController;
import org.example.model.EmailNotification;
import org.example.service.KafkaProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EmailControllerTest {

    private KafkaProducerService producer;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        producer = Mockito.mock(KafkaProducerService.class);
        EmailController controller = new EmailController(producer);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testSendEmailWithFullJsonPayload() throws Exception {
        String fullJson = """
                {
                    "to": ["recipient1@example.com", "recipient2@example.com"],
                    "cc": ["cc1@example.com"],
                    "bcc": ["bcc1@example.com"],
                    "subject": "Welcome to Our Service",
                    "body": "<p>Hello, this is a test email!</p>",
                    "templateName": "welcome_template.html",
                    "priority": "HIGH",
                    "templateId": "template123"
                }
                """;

        mockMvc.perform(post("/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(fullJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Email message sent to Kafka!"));

        // Capture and verify data sent to producer
        ArgumentCaptor<EmailNotification> captor = ArgumentCaptor.forClass(EmailNotification.class);
        verify(producer, times(1)).sendEmail(captor.capture());

        EmailNotification captured = captor.getValue();
        assertEquals("Welcome to Our Service", captured.getSubject());
        assertEquals("HIGH", captured.getPriority());
        assertEquals("recipient1@example.com", captured.getTo().get(0));
        assertEquals("cc1@example.com", captured.getCc().get(0));
        assertEquals("bcc1@example.com", captured.getBcc().get(0));
        assertEquals("welcome_template.html", captured.getTemplateName());
        assertEquals("template123", captured.getTemplateId());
    }

    @Test
    void testSendEmailWithEmptyBody() throws Exception {
        String json = "{}"; // Covers scenario with no fields

        mockMvc.perform(post("/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Email message sent to Kafka!"));

        verify(producer, times(1)).sendEmail(any(EmailNotification.class));
    }
}
