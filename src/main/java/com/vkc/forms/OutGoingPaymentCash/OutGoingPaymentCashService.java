package com.vkc.forms.OutGoingPaymentCash;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.vkc.forms.utils.UtilsService;

@Service
public class OutGoingPaymentCashService {
	
	@Autowired UtilsService Utils;
	
//	String HeaderApi = "https://my409401-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_OUTGOINGEXPENSEHEADER_CDS/YY1_OUTGOINGEXPENSEHEADER";
//	 String LineApi = "https://my409401-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_OUTGOINGEXPENSEBANKENT_CDS/YY1_OUTGOINGEXPENSEBANKENT";

	public OutGoingPaymentCashDAO getOutgoingInvoiceDetails (String documentNo, String fiscalYear, String companyCode) throws Exception{
		String HeaderApi = "https://" + Utils.port + "-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_OUTGOINGEXPENSEHEADER_CDS/YY1_OUTGOINGEXPENSEHEADER";
		HttpResponse<String> response = Utils.ApiCall(HeaderApi,Utils.apiUserName,Utils.apiPassword);

		ObjectMapper mapper = new ObjectMapper();
	    JsonNode entryNodeArray = Utils.XmlToJsonConversion(response.getBody().toString());
	    List<OutGoingPaymentCashDAO> data = new ArrayList<>();
	    
		if (entryNodeArray.toString() !=null || !"".equals(entryNodeArray.toString())) {
        List<OutGoingPaymentCashHeaderDAO> entryNodes = mapper.reader().forType(mapper.getTypeFactory().constructCollectionType(List.class, OutGoingPaymentCashHeaderDAO.class))
				.readValue(entryNodeArray.toString());
		data= entryNodes.stream().map(e -> e.getContent().getProperties()).collect(Collectors.toList());
		}
		List<OutGoingPaymentCashDAO> headerFilteredData = data.stream().filter(e -> e.getDAccountingDocument().equalsIgnoreCase(documentNo) && e.getDFiscalYear().equalsIgnoreCase(fiscalYear) && e.getDCompanyCode().equalsIgnoreCase(companyCode)).collect(Collectors.toList());

		return headerFilteredData.get(0) ;
	}
	
	public List<OutGoingPaymentCashLineDAO> LineAPIDetails(String documentNo, String fiscalYear, String companyCode) throws Exception     
	{
		//Passing the Document Number & Fiscal year Dynamically
		String LineApi = "https://"+Utils.port+"-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_OUTGOINGEXPENSEBANKENT_CDS/YY1_OUTGOINGEXPENSEBANKENT";
		HttpResponse<String> response = Utils.ApiCall(LineApi,Utils.apiUserName,Utils.apiPassword);
		
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode entryNodeArray = Utils.XmlToJsonConversion(response.getBody().toString());
	    List<OutGoingPaymentCashLineDAO> data = null;
		if (entryNodeArray.toString() !=null || !"".equals(entryNodeArray.toString())) {
        List<OutGoingPaymentCashHeaderLineDAO> entryNodes = mapper.reader().forType(mapper.getTypeFactory().constructCollectionType(List.class, OutGoingPaymentCashHeaderLineDAO.class))
				.readValue(entryNodeArray.toString());
			data= entryNodes.stream().map(e -> e.getContent().getProperties()).collect(Collectors.toList());
	}
	List<OutGoingPaymentCashLineDAO> lineFilteredData = data.stream().filter(e -> e.getDAccountingDocument().equalsIgnoreCase(documentNo) && e.getDFiscalYear().equalsIgnoreCase(fiscalYear) && e.getDCompanyCode().equalsIgnoreCase(companyCode)).collect(Collectors.toList());
	
	return lineFilteredData;
			
	}
}
