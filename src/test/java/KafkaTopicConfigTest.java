import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.model.EmailNotification;
import org.example.config.KafkaTopicConfig;
import org.junit.jupiter.api.Test;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

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

    @Test
    void testConsumerFactoryBean() {
        KafkaTopicConfig config = new KafkaTopicConfig();
        ConsumerFactory<String, EmailNotification> factory = config.consumerFactory();

        assertNotNull(factory, "ConsumerFactory should not be null");

        Map<String, Object> configMap = factory.getConfigurationProperties();
        assertEquals("localhost:9092", configMap.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals(StringDeserializer.class, configMap.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
        assertEquals(JsonDeserializer.class, configMap.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
    }
}
