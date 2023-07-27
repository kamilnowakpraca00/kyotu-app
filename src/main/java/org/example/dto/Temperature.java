package org.example.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Temperature {
    private String city;
    private LocalDateTime dateTime;
    private double temperature;
}
