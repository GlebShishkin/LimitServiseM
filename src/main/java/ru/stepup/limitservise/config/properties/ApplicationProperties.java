package ru.stepup.limitservise.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

// bean общих данных данных приложения
@Getter
@ConfigurationProperties(prefix = "service")
public class ApplicationProperties {

    // сумма ежедневного лимита устанавливаемая по умолчанию
    private final Integer daylimit;
    private final Integer fixedRate;

    public ApplicationProperties(Integer daylimit
                                , Integer fixedRate
                                )
    {
        this.daylimit = daylimit;
        this.fixedRate = fixedRate;
    }
}
