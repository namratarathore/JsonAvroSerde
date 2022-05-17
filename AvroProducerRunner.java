package com.expediagroup.streamcourse.hw1.avro;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.GenericData;

public class AvroProducerRunner {
    public static void main(String[] args) {
        // Sample producer to get you going
        AvroRecordProducer avroRecordProducer = new AvroRecordProducer();
        String key = "key1";
        String importantBusinessEventSchema = "{\"type\":\"record\"," +
                "\"name\":\"importantBusinessEventRecord\"," +
                "\"fields\":[{\"name\":\"eventName\",\"type\":\"string\"}]}";
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse(importantBusinessEventSchema);
        GenericRecord avroRecord = new GenericData.Record(schema);
        avroRecord.put("eventName", "event1");
        avroRecordProducer.sendBlocking("key1",avroRecord);
    }
}
