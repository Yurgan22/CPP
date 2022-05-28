package com.example.RestService.services;

import com.example.RestService.responses.Response;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class Counter {
    public static long count;

    public synchronized void increment(){
        count++;
    }
    @Contract(" -> new")
    public static @NotNull Response getResponse() {
        return new Response(count);
    }

    public static long getCount(){
        return count;
    }
}