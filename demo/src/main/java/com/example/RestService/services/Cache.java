package com.example.RestService.services;

import com.example.RestService.Log.MyLogger;
import com.example.RestService.process.InputParams;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Cache {
    public static ConcurrentHashMap<InputParams, Double> map = new ConcurrentHashMap<>();

    public void put(@NotNull InputParams params, double result) {
            if (!map.containsKey(params)) {
                map.put(params, result);
                MyLogger.info("Key: " + params + " Value: " + result + " is put in cache");
            }
            else {
                MyLogger.info("Response is already in cache!");
            }
    }

    public Double get(InputParams vector) {

        var results = map.get(vector);
        MyLogger.info("Value: " + results + " is got from cache with Key: " + vector);
        return results;
    }

    public boolean containsKey(InputParams vector){
        return map.containsKey(vector);
    }

    public void printMap(){
        System.out.println("Cache:"+map);
    }
}

