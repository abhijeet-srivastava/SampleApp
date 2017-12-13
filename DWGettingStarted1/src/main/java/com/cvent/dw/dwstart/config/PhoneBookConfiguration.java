package com.cvent.dw.dwstart.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

/**
 * Created by a.srivastava on 4/24/16.
 */
public class PhoneBookConfiguration  extends Configuration {

    @JsonProperty
    private String message;

    @JsonProperty
    private int messageRepetitions;

    public String getMessage() {
        return message;
    }

    public int getMessageRepetitions() {
        return messageRepetitions;
    }
}
