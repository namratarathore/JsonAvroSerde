package com.expediagroup.streamcourse.hw1.avro;

import com.expediagroup.streamcourse.hw1.json.ImportantBusinessEvent;
import lombok.extern.log4j.Log4j2;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;

@Configuration
@Log4j2
public class AvroRecordProducer {
    private static final String OUTPUT_TOPIC = "output-topic-avro";

    private final Producer<Object, Object> producer;

    public AvroRecordProducer() {
        Properties props = new Properties();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                io.confluent.kafka.serializers.KafkaAvroSerializer.class);
        props.put("schema.registry.url", "http://localhost:8081");
        producer = new KafkaProducer<>(props);
    }


    public void sendBlocking(String key, GenericRecord value) {
        ProducerRecord<Object, Object> producerRecord = new ProducerRecord<>(OUTPUT_TOPIC, key, value);
        try {
            producer.send(producerRecord);
        }catch (SerializationException e){
            log.error("Error sending record to kafka", e);
        }
        finally {
            producer.flush();
            producer.close();
        }
    }




}
