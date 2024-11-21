package com.vkc.forms.IncomingPaymemtCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vkc.forms.OutGoingPaymentCash.OutGoingPaymentCashLineDAO;
import com.vkc.forms.OutGoingPaymentCash.OutGoingPaymentCashSubHeaderLineDAO;

import lombok.Data;

@Data
@JsonIgnoreProperties("type")
public class IncomingPaymentCashSubHeaderLineDAO {

	@JsonProperty("m:properties")
	private IncomingPaymentCashLineDAO properties;
	
}
