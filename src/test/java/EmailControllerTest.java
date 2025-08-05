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
    void testSendEmailReturnsSuccess() throws Exception {
        String json = "{ \"subject\": \"Test\", \"to\": [\"test@example.com\"] }";

        mockMvc.perform(post("/email/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Email message sent to Kafka!"));

        // Verify that producer.sendEmail() was called
        ArgumentCaptor<EmailNotification> captor = ArgumentCaptor.forClass(EmailNotification.class);
        verify(producer, times(1)).sendEmail(captor.capture());

        assertEquals("Test", captor.getValue().getSubject());
        assertEquals("test@example.com", captor.getValue().getTo().get(0));
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
