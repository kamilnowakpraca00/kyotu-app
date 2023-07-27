package org.example.file;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.example.dto.Temperature;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class YearlyTemperatureItemReader implements ItemReader<Temperature>, ItemStream {

    private static final String TEMPERATURE_READER_NAME = "temperatureReader";
    private static final String DELIMITER = ";";
    private static final String CITY = "city";
    private static final String DATE_TIME = "dateTime";
    private static final String TEMPERATURE = "temperature";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    private final Resource inputResource;
    private final FlatFileItemReader<Temperature> reader;

    private boolean initialized = false;

    public YearlyTemperatureItemReader(@Value("classpath:file/data.csv") Resource inputResource) {
        this.inputResource = inputResource;
        this.reader = createTemperatureReader();
    }

    private FlatFileItemReader<Temperature> createTemperatureReader() {
        return new FlatFileItemReaderBuilder<Temperature>()
            .name(TEMPERATURE_READER_NAME)
            .resource(inputResource)
            .linesToSkip(1)
            .delimited()
            .delimiter(DELIMITER)
            .names(CITY, DATE_TIME, TEMPERATURE)
            .fieldSetMapper(this::mapToTemperature)
            .build();
    }

    private Temperature mapToTemperature(FieldSet fieldSet) {
        Temperature temperature = new Temperature();
        temperature.setCity(fieldSet.readString(CITY));
        String dateString = fieldSet.readString(DATE_TIME);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
        temperature.setDateTime(dateTime);
        temperature.setTemperature(fieldSet.readDouble(TEMPERATURE));
        return temperature;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (!initialized) {
            reader.open(executionContext);
            initialized = true;
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        reader.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        reader.close();
        initialized = false;
    }

    @Override
    public Temperature read() throws Exception {
        return reader.read();
    }
}

