package com.example.RestService.services;

import com.example.RestService.exceptions.CalculationException;
import com.example.RestService.process.InputParams;
import com.example.RestService.responses.Response;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import com.example.RestService.Log.MyLogger;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Solution {
    private Cache cache;

    private Counter counter;

    private double calcResult;

    public void calculateResult(@NotNull InputParams inputParams) {
        double result=0;
        counter.increment();
        MyLogger.info("Bean 'cache' injected!");
        if (cache.containsKey(inputParams)) {
            MyLogger.info("Response is already in cache!");
            calcResult = cache.get(inputParams);
            return;
        }
        if(inputParams.getMass()<=0)
            throw new CalculationException("Illegal argument:mass<=0!");
        if(inputParams.getPercent()<=0)
            throw new CalculationException("Illegal arguments:percent<=0!");
        if(inputParams.getMode()<=0)
            throw new CalculationException("Illegal arguments:mode<=0");
        if(inputParams.getMode()>2){
            throw new CalculationException("Argument mode must be 1 or 2");
        }
        float temp_percent = inputParams.getPercent() / 100;
        if (inputParams.getMode() == 1) {
            result = temp_percent * inputParams.getMass();
        } else if (inputParams.getMode() == 2) {
            result = inputParams.getMass() / temp_percent;
        }
        MyLogger.info("Result calculated");
        cache.put(inputParams, result);
        calcResult = result;

    }

    @Contract(" -> new")
    public @NotNull Response getResponse() {
        cache.printMap();
        return new Response(calcResult);
    }

    public Double getResult() {
        return calcResult;
    }

    @Autowired
    public void setCache(Cache cache) {
        this.cache = cache;
    }
    @Autowired
    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    public boolean isCorrectParams(@Nullable InputParams params) {
        if(params.getMass()==null||params.getPercent()==null||params.getMode()==null)
            return false;
        return params.getMass() > 0 &&
                params.getPercent() > 0 &&
                params.getMode() == 1;
    }
}