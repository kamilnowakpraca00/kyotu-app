package org.example.file;

import org.example.entity.YearlyTemperature;
import org.example.repository.YearlyTemperatureRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
public class YearlyTemperatureItemWriter implements ItemWriter<YearlyTemperature> {

    private final YearlyTemperatureRepository yearlyTemperatureRepository;

    @Autowired
    public YearlyTemperatureItemWriter(YearlyTemperatureRepository yearlyTemperatureRepository) {
        this.yearlyTemperatureRepository = yearlyTemperatureRepository;
    }

    @Override
    public void write(List<? extends YearlyTemperature> items) {
        yearlyTemperatureRepository.saveAll(items);
    }
}