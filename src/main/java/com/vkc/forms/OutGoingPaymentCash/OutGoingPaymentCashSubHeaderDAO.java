package com.vkc.forms.OutGoingPaymentCash;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vkc.forms.ARCreditMemoService_GST_DebitNoteService.ArCreditMemoDAO;
import com.vkc.forms.ARCreditMemoService_GST_DebitNoteService.ArCreditMemoSubHeaderDAO;

import lombok.Data;

@Data
@JsonIgnoreProperties("type")
public class OutGoingPaymentCashSubHeaderDAO {

	@JsonProperty("m:properties")
	private OutGoingPaymentCashDAO properties;
}
