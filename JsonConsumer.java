package com.expediagroup.streamcourse.hw1.json;

import org.springframework.kafka.annotation.KafkaListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class JsonConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonConsumer.class);
    private static final String INPUT_TOPIC = "output-topic-json-1";
    private final CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @KafkaListener(topics = INPUT_TOPIC)
    public void consumer(ImportantBusinessEvent importantBusinessEvent) {
        LOGGER.info("received importantBusinessEvent='{}'", importantBusinessEvent.toString());
        System.out.println(importantBusinessEvent.toString());
        latch.countDown();
    }

}
