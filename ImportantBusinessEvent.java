package com.expediagroup.streamcourse.hw1.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImportantBusinessEvent {
    @JsonProperty
    private String eventName;
    @JsonProperty
    private int importantValue;

    public ImportantBusinessEvent() {
        super();
    }

    public ImportantBusinessEvent(String eventName, int importantValue) {
        super();
        this.eventName = eventName;
        this.importantValue = importantValue;
    }

    @Override
    public String toString() {
        return String.format("ImportantBusinessEvent eventName is %s and value is %d", eventName, importantValue);
    }
}
