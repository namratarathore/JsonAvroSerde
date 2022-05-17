package com.expediagroup.streamcourse.hw1.avro;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvroConsumerRunner {
    public static void main(String[] args) {
        AvroRecordConsumer avroRecordConsumer = new AvroRecordConsumer();
        avroRecordConsumer.recieve();
    }
}
