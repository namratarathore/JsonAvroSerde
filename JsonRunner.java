package com.expediagroup.streamcourse.hw1.json;


public class JsonRunner {
    // class to populate the topic using JSONRecordProducer
    public static void main(String[] args) {
        // Sample producer to get you going
        JsonRecordProducer jsonRecordProducer = new JsonRecordProducer();
        ImportantBusinessEvent imp1 = new ImportantBusinessEvent("event1", 1);
        ImportantBusinessEvent imp2 = new ImportantBusinessEvent("event2", 2);
        ImportantBusinessEvent imp3 = new ImportantBusinessEvent("event3", 3);
        jsonRecordProducer.sendBlocking("key1", imp1);
        jsonRecordProducer.sendBlocking("key2", imp2);
        jsonRecordProducer.sendBlocking("key3", imp3);
    }

}
