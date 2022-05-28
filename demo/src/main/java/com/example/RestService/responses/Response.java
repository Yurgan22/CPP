package com.example.RestService.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Response {
    private final Object response;

    public Response(Object message) {
        this.response = message;
    }
}
