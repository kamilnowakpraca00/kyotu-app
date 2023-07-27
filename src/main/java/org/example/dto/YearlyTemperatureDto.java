package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearlyTemperatureDto {
    private String city;
    private int year;
    private double sumTemperature;
    private int numMeasurements;
    private double averageTemperature;
}
