package org.example.config;

import org.example.mapper.YearlyTemperatureMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public YearlyTemperatureMapper yearlyTemperatureMapper() {
        return YearlyTemperatureMapper.INSTANCE;
    }
}
