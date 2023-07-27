package org.example.file;

import org.example.dto.Temperature;
import org.example.entity.YearlyTemperature;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class YearlyTemperatureProcessor implements ItemProcessor<Temperature, YearlyTemperature> {

    private static final String MINUS_SIGN = "-";
    private static final int DAYS_INDEX = 1;
    private final Map<String, YearlyTemperature> yearlyTemperatureMap = new HashMap<>();

    @Override
    public YearlyTemperature process(Temperature temperature) {
        String city = temperature.getCity();
        int year = temperature.getDateTime().getYear();

        String key = city + MINUS_SIGN + year;
        YearlyTemperature yearlyTemperature = yearlyTemperatureMap.computeIfAbsent(key, k -> {
            YearlyTemperature yt = new YearlyTemperature();
            yt.setCity(city);
            yt.setYear(year);
            return yt;
        });

        // Calculate the sum of temperatures and the number of measurements
        double sumTemperature = yearlyTemperature.getSumTemperature() + temperature.getTemperature();
        int numMeasurements = yearlyTemperature.getNumMeasurements() + DAYS_INDEX;

        yearlyTemperature.setSumTemperature(sumTemperature);
        yearlyTemperature.setNumMeasurements(numMeasurements);
        yearlyTemperature.setAverageTemperature(getAverageTemperature(sumTemperature, numMeasurements));

        return yearlyTemperature;
    }

    private double getAverageTemperature(double sumTemperature, int numMeasurements) {
        if (numMeasurements == 0) {
            return 0.0;
        }
        return sumTemperature / numMeasurements;
    }
}
