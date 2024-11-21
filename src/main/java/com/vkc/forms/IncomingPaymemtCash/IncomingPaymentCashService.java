package com.vkc.forms.IncomingPaymemtCash;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.vkc.forms.utils.UtilsService;

@Service
public class IncomingPaymentCashService {

	@Autowired UtilsService Utils;
	
	public IncomingPaymentCashDAO getIncomingInvoiceDetails (String documentNo, String fiscalYear, String companyCode) throws Exception{
		String HeaderApi ="https://"+Utils.port+"-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_OUTGOINGEXPENSEHEADER_CDS/YY1_OUTGOINGEXPENSEHEADER";
		HttpResponse<String> response = Utils.ApiCall(HeaderApi,Utils.apiUserName,Utils.apiPassword);
		
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode entryNodeArray = Utils.XmlToJsonConversion(response.getBody().toString());
	    
	    List<IncomingPaymentCashDAO> data = new ArrayList<>();
	    if (entryNodeArray.toString() !=null || !"".equals(entryNodeArray.toString())) {
	   List<IncomingPaymentCashHeaderDAO> entryNodes = mapper.reader().forType(mapper.getTypeFactory().constructCollectionType(List.class, IncomingPaymentCashHeaderDAO.class))
				.readValue(entryNodeArray.toString());
		data= entryNodes.stream().map(e -> e.getContent().getProperties()).collect(Collectors.toList());
	    }
		List<IncomingPaymentCashDAO> headerFilteredData = data.stream().filter(e -> e.getDAccountingDocument().equalsIgnoreCase(documentNo) && e.getDFiscalYear().equalsIgnoreCase(fiscalYear) && e.getDCompanyCode().equalsIgnoreCase(companyCode)).collect(Collectors.toList());
		return headerFilteredData.get(0) ;
	}
	
	public List<IncomingPaymentCashLineDAO> LineAPIDetails(String documentNo, String fiscalYear, String companyCode) throws Exception     
	{
		String LineApi = "https://"+Utils.port+"-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_OUTGOINGEXPENSEBANKENT_CDS/YY1_OUTGOINGEXPENSEBANKENT";
		//Passing the Document Number & Fiscal year Dynamically
		HttpResponse<String> response = Utils.ApiCall(LineApi,Utils.apiUserName,Utils.apiPassword);
		
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode entryNodeArray = Utils.XmlToJsonConversion(response.getBody().toString());
	    List<IncomingPaymentCashLineDAO> data = null;
		if (entryNodeArray.toString() !=null || !"".equals(entryNodeArray.toString())) {
        List<IncomingPaymentCashHeaderLineDAO> entryNodes = mapper.reader().forType(mapper.getTypeFactory().constructCollectionType(List.class, IncomingPaymentCashHeaderLineDAO.class))
				.readValue(entryNodeArray.toString());
			data= entryNodes.stream().map(e -> e.getContent().getProperties()).collect(Collectors.toList());
	}
	List<IncomingPaymentCashLineDAO> lineFilteredData = data.stream().filter(e -> e.getDAccountingDocument().equalsIgnoreCase(documentNo) && e.getDFiscalYear().equalsIgnoreCase(fiscalYear) && e.getDCompanyCode().equalsIgnoreCase(companyCode)).collect(Collectors.toList());
	
	return lineFilteredData; 
			
	}
	
}
	
