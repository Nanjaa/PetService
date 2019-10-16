package com.stephanieolfert.petservice.util;

import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;

public class Response {
    private final Date timestamp;
    private final HttpStatus status;
    private final Map<String, String> errors;
    private final Map<String, Object> response;
    
    public Response(Date timestamp, HttpStatus status, Map<String, String> errors, Map<String, Object> response) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.errors = errors;
        this.response = response;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    
    public HttpStatus getStatus() {
        return status;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public Map<String, Object> getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "PetResponse [timestamp=" + timestamp + ", status=" + status + ", errors=" + errors + ", response="
                + response + "]";
    }

}
