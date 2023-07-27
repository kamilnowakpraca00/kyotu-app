package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.YearlyTemperatureDto;
import org.example.dto.YearlyTemperatureResponse;
import org.example.mapper.YearlyTemperatureMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.service.YearlyTemperatureService;

import java.util.List;

@RestController
@RequestMapping("/api/temperature")
@RequiredArgsConstructor
public class YearlyTemperatureController {

    private final YearlyTemperatureService yearlyTemperatureService;
    private final YearlyTemperatureMapper yearlyTemperatureMapper;


    @GetMapping("/yearly-averages/{city}")
    public ResponseEntity<List<YearlyTemperatureResponse>> getYearlyAverages(@PathVariable String city) {
        List<YearlyTemperatureDto> yearlyAverages = yearlyTemperatureService.getYearlyAverages(city);
        if (yearlyAverages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(yearlyTemperatureMapper.toResponseList(yearlyAverages), HttpStatus.OK);
    }
}