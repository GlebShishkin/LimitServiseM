package ru.stepup.limitservise.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.stepup.limitservise.config.properties.ApplicationProperties;

@Configuration
// читаем настройки из пропертей (yml)
@EnableConfigurationProperties({
        ApplicationProperties.class    // service:
})
public class ApplicationConfig {

}
