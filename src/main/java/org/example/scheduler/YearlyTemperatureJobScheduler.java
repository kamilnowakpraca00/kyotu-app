package org.example.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.service.YearlyTemperatureService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YearlyTemperatureJobScheduler {

    private final YearlyTemperatureService yearlyTemperatureService;

    @Scheduled(cron = "${batch.job.schedule}")
    public void runTemperatureJob() {
        yearlyTemperatureService.importFile();
    }
}

