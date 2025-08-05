import org.example.kafka.KafkaTopicConfig;
import org.junit.jupiter.api.Test;
import org.apache.kafka.clients.admin.NewTopic;

import static org.junit.jupiter.api.Assertions.*;

public class KafkaTopicConfigTest {

    @Test
    void testEmailNotificationTopicBean() {
        KafkaTopicConfig config = new KafkaTopicConfig();
        NewTopic topic = config.emailNotificationTopic();

        assertNotNull(topic);
        assertEquals("email-notification", topic.name());
        assertEquals(3, topic.numPartitions());
        assertEquals(1, topic.replicationFactor());
    }
}
