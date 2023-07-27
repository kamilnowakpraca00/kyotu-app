package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.example.dto.YearlyTemperatureDto;
import org.example.entity.YearlyTemperature;
import org.example.mapper.YearlyTemperatureMapper;
import org.example.repository.YearlyTemperatureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;

@ExtendWith(MockitoExtension.class)
class YearlyTemperatureServiceTest {

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job temperatureJob;

    @Mock
    private YearlyTemperatureRepository yearlyTemperatureRepository;

    @Mock
    private YearlyTemperatureMapper yearlyTemperatureMapper;

    @InjectMocks
    private YearlyTemperatureService yearlyTemperatureService;

    @Test
    void testGetYearlyAverages_WithDataInRepository() {
        // given
        String city = "Warszawa";
        List<YearlyTemperature> temperatures = Arrays.asList(
            new YearlyTemperature(1L, "Warszawa", 2022, 15.0, 5, 3.0),
            new YearlyTemperature(2L, "Warszawa", 2021, 14.5, 3, 4.83)
        );
        Mockito.when(yearlyTemperatureRepository.findByCity(city)).thenReturn(temperatures);

        // and
        List<YearlyTemperatureDto> dtoList = Arrays.asList(
            new YearlyTemperatureDto("Warszawa", 2022, 15.0, 5, 3.0),
            new YearlyTemperatureDto("Warszawa", 2021, 14.5, 3, 4.83)
        );
        Mockito.when(yearlyTemperatureMapper.toDtoList(temperatures)).thenReturn(dtoList);

        // when
        List<YearlyTemperatureDto> result = yearlyTemperatureService.getYearlyAverages(city);

        // then
        assertEquals(dtoList, result);
    }

    @Test
    void testGetYearlyAverages_WithoutDataInRepository() {
        // given
        String city = "Gdansk";
        Mockito.when(yearlyTemperatureRepository.findByCity(city)).thenReturn(Collections.emptyList());

        // when
        List<YearlyTemperatureDto> result = yearlyTemperatureService.getYearlyAverages(city);

        // then
        assertTrue(result.isEmpty());
    }
}
