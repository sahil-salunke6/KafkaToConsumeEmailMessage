import org.example.kafka.EmailController;
import org.example.kafka.EmailNotification;
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
                    "subject": "Notification Service Email",
                    "body": "<p> This is a sample email body.</p>",
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
        assertEquals("Notification Service Email", captured.getSubject());
        assertEquals("HIGH", captured.getPriority());
        assertEquals("recipient1@example.com", captured.getTo().get(0));
        assertEquals("cc1@example.com", captured.getCc().get(0));
        assertEquals("bcc1@example.com", captured.getBcc().get(0));
        assertEquals("John Doe", captured.getPlaceholders().get("userName"));
        assertEquals("example.pdf", captured.getAttachment().get(0).getFileName());
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
