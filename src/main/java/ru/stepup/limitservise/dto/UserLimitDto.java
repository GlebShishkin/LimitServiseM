package ru.stepup.limitservise.dto;

import java.math.BigDecimal;

public record UserLimitDto(Integer userid, BigDecimal limit) {
}
