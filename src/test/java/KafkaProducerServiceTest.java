import org.example.kafka.EmailNotification;
import org.example.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

public class KafkaProducerServiceTest {

    @Test
    void testSendEmail() {
//        KafkaTemplate<String, EmailNotification> kafkaTemplate = Mockito.mock(KafkaTemplate.class);
//        KafkaProducerService producerService = new KafkaProducerService(kafkaTemplate);
//
//        EmailNotification notification = new EmailNotification();
//        notification.setSubject("Test Subject");
//
//        producerService.sendEmail(notification);
//
//        verify(kafkaTemplate, times(1)).send("email-notification", notification);
    }
}
