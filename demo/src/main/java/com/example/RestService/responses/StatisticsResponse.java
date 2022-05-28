package com.example.RestService.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Collections;
import java.util.Comparator;
import java.util.function.BinaryOperator;

import static com.example.RestService.controllers.MainController.list;

@Getter
public class StatisticsResponse {
    @JsonProperty("Amount of requests")
    int requestsAmount;
    @JsonProperty("Min result")
    double minResult;
    @JsonProperty("Max result")
    double maxResult;
    @JsonProperty("Most common response")
    double mostCommonResponse;

    public StatisticsResponse(int requestsAmount, double minResult,
                              double maxResult, double mostCommon){
        this.requestsAmount = requestsAmount;
        this.minResult = minResult;
        this.maxResult = maxResult;
        this.mostCommonResponse = mostCommon;
    }

    public static Object getResponse() {
            Response mostCommonResponseTemp= list
                    .stream()
                    .reduce(
                            BinaryOperator.maxBy(Comparator.comparingDouble(o -> Collections.frequency(list, o)))
                    ).orElse(new Response(0));
            double mostCommonResponse = (double)mostCommonResponseTemp.getResponse();
            double maxResult = list.stream()
                    .mapToDouble((value) -> (double) value.getResponse())
                    .summaryStatistics()
                    .getMax();
           double minResult = list.stream()
                    .mapToDouble((value) -> (double) value.getResponse())
                    .summaryStatistics()
                    .getMin();

           int requestsAmount = list.size();

        return new StatisticsResponse(requestsAmount,minResult,maxResult,mostCommonResponse);
    }
}
