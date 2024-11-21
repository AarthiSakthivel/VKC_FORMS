package com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
public interface APFormRepository extends JpaRepository<ZFormEntity, Integer> {

	
	
}
