package com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApSubFormRepository  extends JpaRepository<ApSubReportEntity, Integer> {

}
