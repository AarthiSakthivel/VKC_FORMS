package com.vkc.forms.ARCreditMemoService_GST_DebitNoteService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.vkc.forms.utils.UtilsService;

@Service
public class ArCreditMemoService {
	
	@Autowired
	UtilsService Utils;
	
	
	@Description(value="Get the Tax Invoice Header Details !!")
	public ArCreditMemoDAO getInvoiceDetails(String documentNo, String fiscalYear, String companyCode) throws Exception     
	{
		String HeaderApi = "https://"+Utils.port+"-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_ZFIFORMSHEADERDR_CDS/YY1_ZFIFORMSHEADERDR";
		//Passing the Document Number & Fiscal year Dynamically
		HttpResponse<String> response = Utils.ApiCall(HeaderApi,Utils.apiUserName,Utils.apiPassword);
		
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode entryNodeArray = Utils.XmlToJsonConversion(response.getBody().toString());
	    List<ArCreditMemoDAO> data = null;
	  //Validating the Blank Data
		if (entryNodeArray.toString() !=null || !"".equals(entryNodeArray.toString())) {
			List<ArCreditMemoHeaderDAO> entryNodes = mapper.reader().forType(mapper.getTypeFactory().constructCollectionType(List.class, ArCreditMemoHeaderDAO.class))
					.readValue(entryNodeArray.toString());
				data= entryNodes.stream().map(e -> e.getContent().getProperties()).collect(Collectors.toList());
		}
		
		List<ArCreditMemoDAO> headerFilteredData = data.stream().filter(e -> e.getDAccountingDocument().equalsIgnoreCase(documentNo) && e.getDFiscalYear().equalsIgnoreCase(fiscalYear) && e.getDCompanyCode().equalsIgnoreCase(companyCode)).collect(Collectors.toList());
		
		//List<APCreditMemoLineDAO> lineFilteredList = LineAPIDetails(documentNo,fiscalYear,companyCode );
		
		//return lineFilteredList.size() > 0 ? headerFilteredData.get(0) : null;
		return headerFilteredData.get(0) ;
	}
	
	

	@Description(value="Get the Tax Invoice Line Details !!")
	public List<ArCreditMemoLineDAO> LineAPIDetails(String documentNo, String fiscalYear, String companyCode) throws Exception     
	{
		 String LineApi = "https://"+Utils.port+"-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_ZFIFORMSLINEDETAILSDR_CDS/YY1_ZFIFORMSLINEDETAILSDR";
		//Passing the Document Number & Fiscal year Dynamically
		HttpResponse<String> response = Utils.ApiCall(LineApi,Utils.apiUserName,Utils.apiPassword);
		
		ObjectMapper mapper = new ObjectMapper();
	    JsonNode entryNodeArray = Utils.XmlToJsonConversion(response.getBody().toString());
	    List<ArCreditMemoLineDAO> data = null;
	  //Validating the Blank Data
		if (entryNodeArray.toString() !=null || !"".equals(entryNodeArray.toString())) {
			List<ArCreditMemoHeaderLineDAO> entryNodes = mapper.reader().forType(mapper.getTypeFactory().constructCollectionType(List.class, ArCreditMemoHeaderLineDAO.class))
					.readValue(entryNodeArray.toString());
				data= entryNodes.stream().map(e -> e.getContent().getProperties()).collect(Collectors.toList());
		}
		
		List<ArCreditMemoLineDAO> lineFilteredData = data.stream().filter(e -> e.getDAccountingDocument().equalsIgnoreCase(documentNo) && e.getDFiscalYear().equalsIgnoreCase(fiscalYear) && e.getDCompanyCode().equalsIgnoreCase(companyCode)).collect(Collectors.toList());
		
		return lineFilteredData;
	}
	
	
	
	
	
	
}
