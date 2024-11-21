package com.vkc.forms.OutGoingPaymentCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vkc.forms.ARCreditMemoService_GST_DebitNoteService.ArCreditMemoLineDAO;
import com.vkc.forms.ARCreditMemoService_GST_DebitNoteService.ArCreditMemoSubHeaderLineDAO;

import lombok.Data;

@Data
@JsonIgnoreProperties("type")
public class OutGoingPaymentCashSubHeaderLineDAO {
	
	@JsonProperty("m:properties")
	private OutGoingPaymentCashLineDAO properties;
	
}
