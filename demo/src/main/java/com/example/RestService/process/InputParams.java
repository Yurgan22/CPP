package com.example.RestService.process;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@EqualsAndHashCode
@ToString
@Getter
public class InputParams {
    private final @Nullable Integer mass;

    private final @Nullable Float percent;

    private final @Nullable Integer mode;

    public InputParams(
            @Nullable Integer mass,
            @Nullable Float percent,
            @Nullable Integer mode
    ) {

        this.mass  = mass;
        this.percent = percent;
        this.mode = mode;
    }
}

