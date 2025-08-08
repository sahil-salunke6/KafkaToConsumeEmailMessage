import org.example.kafka.EmailNotification;
import org.example.service.KafkaProducerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

public class KafkaProducerServiceTest {

    @Test
    @DisplayName("Should send email notification using KafkaTemplate")
    void testSendEmailSuccess() {
        // Arrange
        KafkaTemplate<String, EmailNotification> kafkaTemplate = mock(KafkaTemplate.class);
        KafkaProducerService producerService = new KafkaProducerService(kafkaTemplate);

        EmailNotification notification = new EmailNotification();
        notification.setSubject("Test Subject");

        // Act
        producerService.sendEmail(notification);

        // Assert
        verify(kafkaTemplate, times(1)).send("email-notification", notification);
    }

    @Test
    @DisplayName("Should handle exception during Kafka send")
    void testSendEmailException() {
        // Arrange
        KafkaTemplate<String, EmailNotification> kafkaTemplate = mock(KafkaTemplate.class);
        KafkaProducerService producerService = new KafkaProducerService(kafkaTemplate);

        EmailNotification notification = new EmailNotification();
        notification.setSubject("Fail Subject");

        // Simulate Kafka exception
        doThrow(new RuntimeException("Kafka send failed"))
                .when(kafkaTemplate).send("email-notification", notification);

        // Act
        producerService.sendEmail(notification);

        // Assert
        verify(kafkaTemplate, times(1)).send("email-notification", notification);
        // No exception should propagate, handled gracefully
    }
}
