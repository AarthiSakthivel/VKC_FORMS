package com.vkc.forms.OutGoingPaymentCash;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface OutGoingPaymentCashRepository extends JpaRepository<OutGoingPaymentCashEntity, Integer> {

}
