package com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties("type")
public class APCreditMemoSubHeaderDAO {

	@JsonProperty("m:properties")
	private APCreditMemoDAO properties;
	
}
