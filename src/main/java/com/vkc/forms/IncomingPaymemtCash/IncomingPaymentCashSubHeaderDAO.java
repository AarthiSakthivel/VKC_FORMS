package com.vkc.forms.IncomingPaymemtCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties("type")
public class IncomingPaymentCashSubHeaderDAO {
	@JsonProperty("m:properties")
	private IncomingPaymentCashDAO properties;
	
}
