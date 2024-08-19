package ru.stepup.limitservise.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.stepup.limitservise.dto.SetLimitDto;
import ru.stepup.limitservise.dto.UserLimitDto;
import ru.stepup.limitservise.entity.UserLimit;
import ru.stepup.limitservise.service.UserLimitServise;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserLimitController {

    private UserLimitServise userLimitServise;

    @Autowired
    public void ProductController(UserLimitServise userLimitServise) {
        this.userLimitServise = userLimitServise;
    }

    // получение всех лимитов пользователей
    @GetMapping("/userlimits")
    public List<UserLimitDto> getUserLimits() {
        return userLimitServise.getUserLimits()
                .stream()
                .map(x -> new UserLimitDto(x.getUserId(), x.getLimit()))
                .toList();
    }

    @GetMapping("/userlimit")
    public UserLimitDto getUserLimitById(@RequestParam Integer userid) throws SQLException {
        // ТЗ: "Поскольку считаем, что gateway не пропустит в систему несуществующего клиента, то при запросе лимита с ID,
        // который отсутствует в БД, создаем новую запись под него со стандартным значением лимита"

        // если не находим по id, то NotFoundException не происходиит, т.к. по ТЗ заводится новый пользователь с id = userid и лимитом по умолчанию
        UserLimit userLimit = userLimitServise.getUserLimit(userid);
        return new UserLimitDto(userLimit.getUserId(), userLimit.getLimit());
    }

    // списание (SetLimitDto.typ = 0)/восстановление (SetLimitDto.typ = 1) лимита
    @PutMapping(value = "/setlimit")
    public ResponseEntity<?> setLimit(@Valid @RequestBody SetLimitDto setLimitDto,
                                       BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            this.userLimitServise.updateUserLimit(setLimitDto);
            return ResponseEntity.noContent()
                    .build();
        }
    }
}
