package com.example.RestService.controllers;

import com.example.RestService.Log.MyLogger;
import com.example.RestService.SpringConfig;
import com.example.RestService.services.Counter;
import com.example.RestService.exceptions.CalculationException;
import com.example.RestService.process.InputParams;
import com.example.RestService.services.Solution;
import com.example.RestService.responses.Response;
import com.example.RestService.responses.StatisticsResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class MainController {
    public static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
    public static List<Response> list = new ArrayList<>();

    public static Solution solution = context.getBean("solution",Solution.class);
    @GetMapping("/solve")
    public ResponseEntity<Response> GetAnswer(
            @RequestParam(value="mass")   @Nullable Integer mass,
            @RequestParam(value="percent")  @Nullable Float percent,
            @RequestParam(value="mode")  @Nullable Integer mode
    ) throws CalculationException {
        var params   = new InputParams(mass,percent,mode);
        solution.calculateResult(params);
        MyLogger.info("Counter:"+Counter.count);
        //list.add(solution.getResponse());
        return new ResponseEntity<>(solution.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/counter")
    public ResponseEntity<Response> GetCounter(){
        return new ResponseEntity<>(Counter.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/solve_bulk")
    public ResponseEntity<Object> solveBulkJson(
                @RequestBody @NotNull List<InputParams> params
    ) throws CalculationException {
        list.clear();
        list = params
                .stream()
                .filter(solution::isCorrectParams)
                .peek(solution::calculateResult)
                .map(e->solution.getResponse())
                .collect(Collectors.toList());
        MyLogger.info("Counter:"+ Counter.count);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    @PostMapping("/last_stat")
    public ResponseEntity<Object> lastStatistics() {
        return new ResponseEntity<>(StatisticsResponse.getResponse(), HttpStatus.OK);
    }
}