package com.vkc.forms.OutGoingPaymentCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vkc.forms.ARCreditMemoService_GST_DebitNoteService.ArCreditMemoHeaderLineDAO;
import com.vkc.forms.ARCreditMemoService_GST_DebitNoteService.ArCreditMemoSubHeaderLineDAO;

import lombok.Data;

@Data
@JsonIgnoreProperties({"link","id","title","category","updated"})
public class OutGoingPaymentCashHeaderLineDAO {
	
	@JsonProperty("content")
	private OutGoingPaymentCashSubHeaderLineDAO content;
	
}
