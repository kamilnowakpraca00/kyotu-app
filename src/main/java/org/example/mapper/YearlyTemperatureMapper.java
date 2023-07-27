package org.example.mapper;

import org.example.dto.YearlyTemperatureDto;
import org.example.dto.YearlyTemperatureResponse;
import org.example.entity.YearlyTemperature;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import java.util.List;

@Mapper(componentModel = "spring")
public interface YearlyTemperatureMapper {

    YearlyTemperatureMapper INSTANCE = Mappers.getMapper(YearlyTemperatureMapper.class);

    @Mapping(target = "averageTemperature", source = "averageTemperature")
    @Mapping(target = "year", source = "year")
    List<YearlyTemperatureResponse> toResponseList(List<YearlyTemperatureDto> yearlyTemperatureList);

    @Mapping(target = "averageTemperature", source = "averageTemperature")
    @Mapping(target = "sumTemperature", source = "sumTemperature")
    @Mapping(target = "numMeasurements", source = "numMeasurements")
    List<YearlyTemperatureDto> toDtoList(List<YearlyTemperature> yearlyTemperatureList);

    // Mapping method to convert YearlyTemperatureDto to YearlyTemperatureResponse
    YearlyTemperatureResponse toResponse(YearlyTemperatureDto yearlyTemperatureDto);

    // Mapping method to convert YearlyTemperatureResponse to YearlyTemperatureDto
    YearlyTemperatureDto toDto(YearlyTemperature yearlyTemperatureResponse);
}
