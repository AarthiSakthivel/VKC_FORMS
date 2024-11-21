package com.vkc.forms.IncomingPaymemtCash;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface IncomingPaymentCashRepository extends JpaRepository<IncomingPaymentCashEntity, Integer> {

	
}
