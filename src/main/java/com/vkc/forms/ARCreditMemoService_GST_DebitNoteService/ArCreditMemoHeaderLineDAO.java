package com.vkc.forms.ARCreditMemoService_GST_DebitNoteService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties({"link","id","title","category","updated"})
public class ArCreditMemoHeaderLineDAO {

	@JsonProperty("content")
	private ArCreditMemoSubHeaderLineDAO content;
	
}
