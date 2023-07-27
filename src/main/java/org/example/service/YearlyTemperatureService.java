package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.YearlyTemperatureDto;
import org.example.mapper.YearlyTemperatureMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;
import org.example.entity.YearlyTemperature;
import org.example.repository.YearlyTemperatureRepository;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YearlyTemperatureService {
    private static final String JOB_TIME = "time";

    private final JobLauncher jobLauncher;
    private final Job temperatureJob;
    private final YearlyTemperatureRepository yearlyTemperatureRepository;
    private final YearlyTemperatureMapper yearlyTemperatureMapper;


    public List<YearlyTemperatureDto> getYearlyAverages(String city) {
        List<YearlyTemperature> temperatures = yearlyTemperatureRepository.findByCity(city);
        if (!temperatures.isEmpty()) {
            importFile();
            temperatures = yearlyTemperatureRepository.findByCity(city);
        }
        return yearlyTemperatureMapper.toDtoList(temperatures);
    }


    public void importFile() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                .addLong(JOB_TIME, System.currentTimeMillis())
                .toJobParameters();
            jobLauncher.run(temperatureJob, jobParameters);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
