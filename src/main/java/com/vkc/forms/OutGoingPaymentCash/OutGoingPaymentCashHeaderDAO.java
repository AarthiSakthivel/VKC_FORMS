package com.vkc.forms.OutGoingPaymentCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vkc.forms.ARCreditMemoService_GST_DebitNoteService.ArCreditMemoHeaderDAO;
import com.vkc.forms.ARCreditMemoService_GST_DebitNoteService.ArCreditMemoSubHeaderDAO;

import lombok.Data;

@Data
@JsonIgnoreProperties({"link","id","title","category","updated"})
public class OutGoingPaymentCashHeaderDAO {

	@JsonProperty("content")
	private OutGoingPaymentCashSubHeaderDAO content;
	
}
