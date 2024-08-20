package ru.stepup.limitservise.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.stepup.limitservise.config.properties.ApplicationProperties;
import ru.stepup.limitservise.repository.UserLimitRepo;

import java.math.BigDecimal;

@Slf4j
@Component
public class ScheduledTasks {

    private UserLimitRepo userLimitRepo;
    private ApplicationProperties applicationProperties;

    @Autowired
    public ScheduledTasks(UserLimitRepo userLimitRepo, ApplicationProperties applicationProperties
    ) {
        this.userLimitRepo = userLimitRepo;
        this.applicationProperties = applicationProperties;
    }

//    @Scheduled(fixedRateString = "${service.fixedRate}")   // тестовый прогон - 10 сек
    @Scheduled(cron = "0 0 0 * * ?")   // запускается каждый день в полнось
    public void scheduleTaskWithFixedRate() {
        log.info("##########");
        // восстановление дневного лимита на сумму из пропертей
        userLimitRepo.restoreDayLimits(new BigDecimal(applicationProperties.getDaylimit()));
    }
}
