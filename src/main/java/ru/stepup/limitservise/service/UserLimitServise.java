package ru.stepup.limitservise.service;

import lombok.extern.slf4j.Slf4j;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import ru.stepup.limitservise.config.properties.ApplicationProperties;
import ru.stepup.limitservise.dto.SetLimitDto;
import ru.stepup.limitservise.entity.UserLimit;
import ru.stepup.limitservise.exceptions.InsufficientFundsException;
import ru.stepup.limitservise.exceptions.NotFoundException;
import ru.stepup.limitservise.repository.UserLimitRepo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@EnableScheduling
@Service("UserLimitServise")
public class UserLimitServise/* implements CommandLineRunner*/ {

    private UserLimitRepo userLimitRepo;
    private ApplicationProperties applicationProperties;

    @Autowired
    public UserLimitServise(UserLimitRepo userLimitRepo, ApplicationProperties applicationProperties
    ) {
        this.userLimitRepo = userLimitRepo;
        this.applicationProperties = applicationProperties;
    }

    public List<UserLimit> getUserLimits() {
        return userLimitRepo.findAll();
    }

    public UserLimit getUserLimit(Integer userId) {

        // ТЗ: "Поскольку считаем, что gateway не пропустит в систему несуществующего клиента,
        // то при запросе лимита с ID, который отсутствует в БД, создаем новую запись под него со стандартным значением лимита"
        return userLimitRepo.findById(userId)
                .orElseGet(() -> {
                    UserLimit userLimit = new UserLimit(userId, new BigDecimal(this.applicationProperties.getDaylimit()));
                    userLimitRepo.save(userLimit);
                    return userLimit;
                });
    }

    // ТЗ: "При успешном проведении платежа лимит должен быть уменьшен на соответствующую сумму
    //- Если вдруг платеж по какой-то причине не прошел, необходимо иметь возможность восстановить списанный лимит
    // (тут сами выбираете стратегию уменьшения/восстановления лимитов)"
    // PS. На мой взгляд, решение на восстановление должно приниматься на стороне сервера платежей.
    @Transactional
    public void updateUserLimit(SetLimitDto setLimitDto) {

        Optional<UserLimit> optionalUserLimit = userLimitRepo.findById(setLimitDto.userid());

        if (!optionalUserLimit.isPresent()) {
            // в переданном dto передан отсутствующий id
            throw new NotFoundException("пользователь с id = " + setLimitDto.userid() + " не найден", HttpStatus.NOT_FOUND);
        } else {
            UserLimit userLimit = optionalUserLimit.get();

            if (setLimitDto.typ().getDirectionType() == 0) {   // имеет место списание -> проверим достаточность оставшегося лимита

                if (userLimit.getLimit().compareTo(setLimitDto.amount()) < 0) {
                    throw new InsufficientFundsException("Недостаточно лимита " + userLimit.getLimit(), HttpStatus.EXPECTATION_FAILED);
                }
                userLimit.setLimit(userLimit.getLimit().subtract(setLimitDto.amount()));
            }
            else {  // восстановление лимита
                userLimit.setLimit(userLimit.getLimit().add(setLimitDto.amount()));
            }

            userLimitRepo.save(userLimit);
        }
    }
}
