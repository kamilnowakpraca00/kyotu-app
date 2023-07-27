package org.example.file;

import lombok.RequiredArgsConstructor;
import org.example.dto.Temperature;
import org.example.entity.YearlyTemperature;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.batch.core.Step;
import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class YearlyTemperatureJobConfig {

    private static final String TEMPERATURE_STEP = "temperatureStep";
    private static final String TEMPERATURE_JOB = "temperatureJob";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final YearlyTemperatureItemReader yearlyTemperatureItemReader;
    private final YearlyTemperatureItemWriter yearlyTemperatureItemWriter;
    private final YearlyTemperatureProcessor yearlyTemperatureProcessor;


    @Value("classpath:data.csv")
    private Resource inputResource;

    @Bean
    public Job temperatureJob(JobLauncher jobLauncher, JobRepository jobRepository) {
        return jobBuilderFactory.get(TEMPERATURE_JOB)
            .start(temperatureStep())
            .build();
    }

    @Bean
    public Step temperatureStep() {
        return stepBuilderFactory.get(TEMPERATURE_STEP)
            .<Temperature, YearlyTemperature>chunk(10)
            .reader(yearlyTemperatureItemReader)
            .processor(yearlyTemperatureProcessor)
            .writer(this.yearlyTemperatureItemWriter) // Use the JPA Item Writer
            .build();
    }
}