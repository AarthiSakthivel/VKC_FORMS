package com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableJpaRepositories
public interface ZFormRepository extends JpaRepository<ZFormEntity, Integer> {

	
	
}
