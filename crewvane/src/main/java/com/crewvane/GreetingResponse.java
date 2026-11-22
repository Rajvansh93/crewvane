package com.crewvane;

public record GreetingResponse (
        String message,
        String application,
        String status
){}
