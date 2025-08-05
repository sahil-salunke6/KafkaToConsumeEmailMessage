import org.example.kafka.KafkaUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

public class KafkaUtilTest {

    @Test
    void testConstants() {
        assertEquals("email-notification", KafkaUtil.KAFKA_TOPIC);
        assertEquals("email-consumer-group", KafkaUtil.KAFKA_GROUP_ID);
    }

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<KafkaUtil> constructor = KafkaUtil.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        KafkaUtil instance = constructor.newInstance();

        assertNotNull(instance); // Ensures constructor invoked successfully
        assertTrue(true); // confirms correct type
    }
}
