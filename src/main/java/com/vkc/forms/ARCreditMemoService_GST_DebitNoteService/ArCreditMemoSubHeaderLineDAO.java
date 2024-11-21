package com.vkc.forms.ARCreditMemoService_GST_DebitNoteService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties("type")
public class ArCreditMemoSubHeaderLineDAO {

	@JsonProperty("m:properties")
	private ArCreditMemoLineDAO properties;
	
}
