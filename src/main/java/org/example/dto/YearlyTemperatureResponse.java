package org.example.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Builder
@Value
public class YearlyTemperatureResponse {
    private String year;

    @ToString.Exclude
    private double averageTemperature;

    public double getAverageTemperature() {
        return Math.round(averageTemperature * 10.0) / 10.0;
    }
}
