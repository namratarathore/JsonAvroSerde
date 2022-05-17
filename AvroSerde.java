package com.expediagroup.streamcourse.hw1.avro;


import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.storage.SchemaRegistry;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import java.util.Collections;
import java.util.Map;

public class AvroSerde<T> implements Serde<T> {
    private final Serde<Object> serdeValue;

    public AvroSerde(){
        serdeValue = Serdes.serdeFrom(new KafkaAvroSerializer(), new KafkaAvroDeserializer());
    }

    public AvroSerde(SchemaRegistryClient client) {
        this(client, Collections.emptyMap());
    }

    public <K, V> AvroSerde(SchemaRegistryClient client, Map<String, ?> props) {
        serdeValue = Serdes.serdeFrom(new KafkaAvroSerializer(client), new KafkaAvroDeserializer(client, props));
    }

    @Override
    public void configure(final Map<String, ?> serdeConfig, final boolean isSerdeForRecordKeys) {
        serdeValue.serializer().configure(serdeConfig, isSerdeForRecordKeys);
        serdeValue.deserializer().configure(serdeConfig, isSerdeForRecordKeys);
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        serdeValue.serializer().close();
        serdeValue.deserializer().close();

    }

    @SuppressWarnings("unchecked")
    @Override
    public Serializer<T> serializer() {
        Object obj = serdeValue.serializer();
        return (Serializer<T>) obj;

    }

    @SuppressWarnings("unchecked")
    @Override
    public Deserializer<T> deserializer() {
        Object obj = serdeValue.deserializer();
        return (Deserializer<T>) obj;
    }
}
