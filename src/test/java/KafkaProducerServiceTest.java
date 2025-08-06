import org.example.kafka.EmailNotification;
import org.example.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class KafkaProducerServiceTest {

    @Test
    void testSendEmailSuccessAndFailure() {
        KafkaTemplate<String, EmailNotification> kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        KafkaProducerService producerService = new KafkaProducerService(kafkaTemplate);

        EmailNotification notification = new EmailNotification();

        // Success future
        CompletableFuture<SendResult<String, EmailNotification>> successFuture =
                CompletableFuture.completedFuture(Mockito.mock(SendResult.class));

        // Failure future
        CompletableFuture<SendResult<String, EmailNotification>> failureFuture =
                new CompletableFuture<>();
        failureFuture.completeExceptionally(new RuntimeException("Kafka send failed"));

        // Need to mock send() separately for each call
        when(kafkaTemplate.send(eq("email-notification"), any(EmailNotification.class)))
                .thenReturn(successFuture) // First call
                .thenReturn(failureFuture); // Second call

        // First call (success)
        producerService.sendEmail(notification);
        // Second call (failure)
        producerService.sendEmail(notification);

        // Verify send called twice
        verify(kafkaTemplate, times(2)).send("email-notification", notification);
    }
}
