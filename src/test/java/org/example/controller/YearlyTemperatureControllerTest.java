package org.example.controller;

import java.util.ArrayList;
import java.util.List;
import org.example.dto.YearlyTemperatureDto;
import org.example.service.YearlyTemperatureService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class YearlyTemperatureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private YearlyTemperatureService yearlyTemperatureService;


    @Test
    void testGetYearlyAverages_WithDataInService() throws Exception {
        //given
        String city = "Warszawa";
        List<YearlyTemperatureDto> yearlyAverages = new ArrayList<>();
        yearlyAverages.add(new YearlyTemperatureDto("Warszawa", 2022, 15.0, 5, 3.0));

        //when
        Mockito.when(yearlyTemperatureService.getYearlyAverages(city)).thenReturn(yearlyAverages);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/temperature/yearly-averages/{city}", city))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].year").value(2022))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].averageTemperature").value(3.0));
    }

    @Test
    void testGetYearlyAverages_WithoutDataInService() throws Exception {
        //given
        String city = "Warszawa";
        List<YearlyTemperatureDto> yearlyAverages = new ArrayList<>();

        //when
        Mockito.when(yearlyTemperatureService.getYearlyAverages(city)).thenReturn(yearlyAverages);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/temperature/yearly-averages/{city}", city))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
