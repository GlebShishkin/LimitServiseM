package ru.stepup.limitservise.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name="user_limits")
@AllArgsConstructor
@NoArgsConstructor
public class UserLimit {

    @Id
    @Column(name="userid")
    private Integer userId;
    @Column(name="limit_sum")
    @NotNull(message = "поле 'limit' не может быть пустым")
    private BigDecimal limit;

}
