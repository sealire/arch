package org.leesia.kafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: leesia
 * @Date: 2018/9/14 08:50
 * @Description:
 */
@Component
public class KafkaListener {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListener.class);

    @org.springframework.kafka.annotation.KafkaListener(topics = "${app.topic.common}")
    public void receive(ConsumerRecord<?, ?> consumer) {
        logger.info("{} - {} : {}", consumer.topic(), consumer.key(), consumer.value());
    }
}
