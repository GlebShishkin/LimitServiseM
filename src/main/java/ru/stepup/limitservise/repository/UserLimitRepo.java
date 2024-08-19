package ru.stepup.limitservise.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.stepup.limitservise.entity.UserLimit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserLimitRepo extends JpaRepository<UserLimit, Integer> {

    // найти лимит по id
    Optional<UserLimit> findById(Integer userId);

    @Transactional
    @Modifying
    @Query(value = "update user_limits set limit_sum = ?1", nativeQuery = true)
    void restoreDayLimits(BigDecimal limit_sum);
}
