package com.cvent.dw.dwstart.model;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Created by a.srivastava on 4/24/16.
 */
public class Greeting {

    @JsonProperty
    public String greeting;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public Greeting(String greeting) {
        this.greeting = greeting;
    }
}
