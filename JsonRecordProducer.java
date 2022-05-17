package com.expediagroup.streamcourse.hw1.json;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import lombok.extern.log4j.Log4j2;
import java.util.Properties;
import java.util.concurrent.Future;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Log4j2
public class JsonRecordProducer {

    private static final String OUTPUT_TOPIC = "output-topic-json-1";

    private final Producer<String, ImportantBusinessEvent> producer;

    public JsonRecordProducer() {
        Properties props = new Properties();
        props.put("acks", "all");

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        producer = new KafkaProducer<>(props);
    }


    public void sendBlocking(String key, ImportantBusinessEvent value) {
        ProducerRecord<String, ImportantBusinessEvent> producerRecord = new ProducerRecord<String, ImportantBusinessEvent>(OUTPUT_TOPIC, key, value);
        Future<RecordMetadata> sentRecord = producer.send(producerRecord);
        try {
            RecordMetadata recordMetadata = sentRecord.get();
            log.warn(recordMetadata.toString());
        } catch (Exception e) {
            log.error("Error sending record to kafka", e);
        }
    }



}
