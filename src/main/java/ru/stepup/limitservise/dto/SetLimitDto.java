package ru.stepup.limitservise.dto;

import ru.stepup.limitservise.enumerator.DirectionType;

import java.math.BigDecimal;

public record SetLimitDto(Integer userid, BigDecimal amount, DirectionType typ) {
}
