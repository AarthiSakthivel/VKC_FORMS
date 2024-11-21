package com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties({"link","id","title","category","updated"})
public class APCreditMemoHeaderDAO {

	@JsonProperty("content")
	private APCreditMemoSubHeaderDAO content;
	
}
