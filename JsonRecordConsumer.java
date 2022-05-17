package com.expediagroup.streamcourse.hw1.json;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.annotation.EnableKafka;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class JsonRecordConsumer {

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "json");

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaJsonDeserializer");
//        props.put(KafkaJsonDeserializerConfig.JSON_VALUE_TYPE, ImportantBusinessEvent.class);
        props.put("acks", "all");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

    @Bean
    public ConsumerFactory<String, ImportantBusinessEvent> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfigs(),
                new StringDeserializer()
                , new JsonDeserializer<>(ImportantBusinessEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ImportantBusinessEvent> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, ImportantBusinessEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }

    @Bean
    public JsonConsumer consumer() {
        return new JsonConsumer();
    }

}
