package com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice;

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
public class APCreditMemoService {

	@Autowired
	UtilsService altrocksUtils;

	@Description(value = "Get the Tax Invoice Header Details !!")
	public APCreditMemoDAO getInvoiceDetails(String documentNo, String fiscalYear, String companyCode)
			throws Exception {
		String HeaderApi = "https://" + altrocksUtils.port + "-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_ZFIFORMSHEADERKR_CDS/YY1_ZFIFORMSHEADERKR";
		// Passing the Document Number & Fiscal year Dynamically
		HttpResponse<String> response = altrocksUtils.ApiCall(HeaderApi, altrocksUtils.apiUserName, altrocksUtils.apiPassword);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode entryNodeArray = altrocksUtils.XmlToJsonConversion(response.getBody().toString());
		List<APCreditMemoDAO> data = null;
		// Validating the Blank Data
		if (entryNodeArray.toString() != null || !"".equals(entryNodeArray.toString())) {
			List<APCreditMemoHeaderDAO> entryNodes = mapper.reader()
					.forType(mapper.getTypeFactory().constructCollectionType(List.class, APCreditMemoHeaderDAO.class))
					.readValue(entryNodeArray.toString());
			data = entryNodes.stream().map(e -> e.getContent().getProperties()).collect(Collectors.toList());
		}

		List<APCreditMemoDAO> headerFilteredData = data.stream()
				.filter(e -> e.getDAccountingDocument().equalsIgnoreCase(documentNo)
						&& e.getDFiscalYear().equalsIgnoreCase(fiscalYear)
						&& e.getDCompanyCode().equalsIgnoreCase(companyCode))
				.collect(Collectors.toList());

		// List<APCreditMemoLineDAO> lineFilteredList =
		// LineAPIDetails(documentNo,fiscalYear,companyCode );

		// return lineFilteredList.size() > 0 ? headerFilteredData.get(0) : null;
		return headerFilteredData.get(0);
	}

	@Description(value = "Get the Tax Invoice Line Details !!")
	public List<APCreditMemoLineDAO> LineAPIDetails(String documentNo, String fiscalYear, String companyCode)
			throws Exception {
		String LineApi = "https://" + altrocksUtils.port + "-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_ZFIFORMSLINEDETAILSKG_CDS/YY1_ZFIFORMSLINEDETAILSKG";
		// Passing the Document Number & Fiscal year Dynamically
		HttpResponse<String> response = altrocksUtils.ApiCall(LineApi, altrocksUtils.apiUserName, altrocksUtils.apiPassword);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode entryNodeArray = altrocksUtils.XmlToJsonConversion(response.getBody().toString());
		List<APCreditMemoLineDAO> data = null;
		// Validating the Blank Data
		if (entryNodeArray.toString() != null || !"".equals(entryNodeArray.toString())) {
			List<APCreditMemoHeaderLineDAO> entryNodes = mapper.reader().forType(
					mapper.getTypeFactory().constructCollectionType(List.class, APCreditMemoHeaderLineDAO.class))
					.readValue(entryNodeArray.toString());
			data = entryNodes.stream().map(e -> e.getContent().getProperties()).collect(Collectors.toList());
		}

		List<APCreditMemoLineDAO> lineFilteredData = data.stream()
				.filter(e -> e.getDAccountingDocument().equalsIgnoreCase(documentNo)
						&& e.getDFiscalYear().equalsIgnoreCase(fiscalYear)
						&& e.getDCompanyCode().equalsIgnoreCase(companyCode))
				.collect(Collectors.toList());

		return lineFilteredData;
	}

}
