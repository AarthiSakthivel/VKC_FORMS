package com.vkc.forms.IncomingPaymemtCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties({"link","id","title","category","updated"})
public class IncomingPaymentCashHeaderDAO {
	@JsonProperty("content")
	private IncomingPaymentCashSubHeaderDAO content;
}
