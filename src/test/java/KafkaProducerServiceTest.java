import org.example.kafka.EmailNotification;
import org.example.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class KafkaProducerServiceTest {

    @Test
    void testSendEmailSuccessAndFailure() {
        KafkaTemplate<String, EmailNotification> kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        KafkaProducerService producerService = new KafkaProducerService(kafkaTemplate);

        EmailNotification notification = new EmailNotification();

        // Success future
        CompletableFuture<SendResult<String, EmailNotification>> successFuture = CompletableFuture.completedFuture(Mockito.mock(SendResult.class));
        when(kafkaTemplate.send(eq("email-notification"), any(EmailNotification.class)))
                .thenReturn(successFuture)
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("Kafka send failed")));

        // Call once for success
        producerService.sendEmail(notification);
        // Call again for failure
        producerService.sendEmail(notification);

        verify(kafkaTemplate, times(2)).send("email-notification", notification);
    }
}
