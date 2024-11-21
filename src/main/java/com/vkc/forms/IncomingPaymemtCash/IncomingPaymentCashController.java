package com.vkc.forms.IncomingPaymemtCash;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vkc.forms.utils.NumberToWordsConverter;
import com.vkc.forms.utils.UtilsService;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Controller
@RequestMapping(value = "/vkc/forms")
@RequiredArgsConstructor
public class IncomingPaymentCashController {
	
	private final JdbcTemplate jdbc;
	
	@Autowired UtilsService Utils;

	private final IncomingPaymentCashService incomingPaymentCashService;
	
	private final IncomingPaymentCashRepository incomingPaymentCashRepository;
	
	@Value("${app.invoice.path}")
	public String IncomingInvoicePath;
	
	@Description(value = "Navigate to Z Forms Page")
	@GetMapping(value = { "/incoming-creditmemo" })
	public String getReport(Model model) throws Exception {
		model.addAttribute("reportAccess", null);
		return "IncomingCredit.html"; 
	}
	
	@PostMapping(value = "/incoming-creditmemo/filter")
	public String getDocumentDetailsRelatedToDebitBoteBasedOnFyAndDocNum(
			@RequestParam(defaultValue = "") String fiscalYear,@RequestParam(defaultValue = "") String documentNo, @RequestParam(defaultValue = "") String companyCode,
			Model model) throws Exception {
		IncomingPaymentCashDAO data  = null;
		try {
			// Fetching the ZForm Debit Note Details
		    data = incomingPaymentCashService.getIncomingInvoiceDetails(documentNo, fiscalYear, companyCode);
			model.addAttribute("data", data);
			model.addAttribute("reportAccess", "yes");
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e.getMessage());
	}
	return "IncomingCredit.html"; 	
	}
	

	@GetMapping("/incoming-creditmemo/{documentNo}/{fiscalYear}/{companyCode}/generate")
		public ResponseEntity<byte[]> GeneratePrintLayout(@PathVariable String documentNo,@PathVariable String fiscalYear,@PathVariable String companyCode) throws Exception {
           
			String sql = "DELETE FROM incoming_payment_form WHERE accounting_document = ? AND fiscal_year = ? AND company_code = ?";
	        jdbc.update(sql, documentNo, fiscalYear, companyCode); 
	        
	        IncomingPaymentCashDAO headerObj = incomingPaymentCashService.getIncomingInvoiceDetails(documentNo, fiscalYear, companyCode);
	        List<IncomingPaymentCashLineDAO> lineFilteredList = incomingPaymentCashService.LineAPIDetails(documentNo,fiscalYear,companyCode );
	        ArrayList<IncomingPaymentCashEntity> objList = new ArrayList<>();
	        Double sumOfCompanyCodeCurrency =0.0;
	     	 String concatenatedString ="";
	     	 String totalInvoiceAmountInWords ="";
			for (IncomingPaymentCashLineDAO data : lineFilteredList) {
				IncomingPaymentCashEntity obj = new IncomingPaymentCashEntity();
				
				sumOfCompanyCodeCurrency += (data.getDAmountInCompanyCodeCurrency() != null) ? data.getDAmountInCompanyCodeCurrency() : 0.0;
					 totalInvoiceAmountInWords = NumberToWordsConverter.convertWithPaisa(sumOfCompanyCodeCurrency.longValue());	
//					Double amount = data.getDAmountInCompanyCodeCurrency();
//					String formattedAmount = NumberFormatter.formatAmountWithThousandSeparator(amount);
//					obj.setAmountInCompanyCodeCurrency(formattedAmount);
		       String street1 = headerObj.getDStreet();
		       String streetName1= headerObj.getDStreetName();
		       String streetPrefixName11 = headerObj.getDStreetPrefixName1();
		       String streetPrefixName21 = headerObj.getDStreetPrefixName2();
		       String streetSuffixName11=headerObj.getDStreetSuffixName1();
		       String streetSuffixName21 =headerObj.getDStreetSuffixName2();
		       String postalCode = headerObj.getDPostalCode();
		        concatenatedString = street1 + streetName1 + streetPrefixName11 + streetPrefixName21 + streetSuffixName11 + streetSuffixName21+", "+postalCode;
		    //   Double amountInCompanyCodeCurrency =Math.abs(data.getDAmountInCompanyCodeCurrency());
				obj.setAccountingDocument(documentNo);
			    if(data.getDHouseBankAccount().isEmpty()) {
		   	    	obj.setHeading("Cash Receipt Voucher");
		   	    }
		   	    else {
		   	    	obj.setHeading("Bank Receipt voucher");
		   	    } 
			    obj.setAccountingDocument(checkNull(documentNo));
				obj.setAccountingDocumentHeaderText(checkNull(data.getDAccountingDocumentHeaderText()));
				obj.setAmountInCompanyCodeCurrency(checkNull(roundOff(data.getDAmountInCompanyCodeCurrency())));
				obj.setCompanyCode(checkNull(headerObj.getDCompanyCode())); 
				obj.setCompanyCodeName(checkNull(headerObj.getDCompanyCodeName()));
				obj.setCountry("India");
				obj.setFiscalYear(checkNull(headerObj.getDFiscalYear()));
				obj.setVillageName(checkNull(headerObj.getDVillageName())); 
				obj.setGlHeaderAccountName(checkNull(headerObj.getDGLAccountName()));
				obj.setGlLineAccountName(checkNull(data.getDGLAccountName()));
				obj.setPostingDate(checkNull(data.getDPostingDate()));
				obj.setRegion(checkNull(headerObj.getDRegionName()));
//				obj.setStreet1(headerObj.getDStreet());
//				obj.setStreetName1(headerObj.getDStreetName());
//				obj.setStreetPrefixName11(headerObj.getDStreetPrefixName1());
//				obj.setStreetPrefixName21(headerObj.getDStreetPrefixName2());
//				obj.setStreetSuffixName11(headerObj.getDStreetSuffixName1());
//				obj.setStreetSuffixName21(headerObj.getDStreetSuffixName2());
				obj.setConcatenatedDatas(checkNull(concatenatedString));
				obj.setTotalAmount(checkNull(sumOfCompanyCodeCurrency));
				obj.setTotalAmountInWords(checkNull(totalInvoiceAmountInWords));
				objList.add(obj);
				} 
			incomingPaymentCashRepository.saveAll(objList);
				
				HashMap<String, Object> map = new HashMap(); 
				map.put("documentNo", documentNo); 
				map.put("documentNo", documentNo); 
				map.put("companyCode", companyCode);
				map.put("fiscalYear", fiscalYear);
				map.put("sumOfCompanyCodeCurrency", sumOfCompanyCodeCurrency);
				map.put("concatenatedString", concatenatedString);
				map.put("totalInvoiceAmountInWords", totalInvoiceAmountInWords);
				
				JasperReport report = JasperCompileManager.compileReport(IncomingInvoicePath + "\\IncomingPaymentCash.jrxml");
				Connection con;
					Class.forName("com.mysql.cj.jdbc.Driver");
					//con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vkc_db", "root","admin");
					con = DriverManager.getConnection(Utils.databaseConnectionUrl, Utils.databaseUsername, Utils.databasePassword);
					JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, con);
					byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
					con.close();
					
					return ResponseEntity.ok().header("Content-Type", "application/pdf")
							.header("Content-Disposition", "inline; filename=Incoming_Credit_" + documentNo + ".pdf")
							.body(reportBytes); 
}
		
		private double roundOff(double value) {
			return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
		}
		
		private String checkNull(String value) {
			return value != null ? value : "";
		}

		private Date checkNull(Date date) {
			return date != null ? date : new Date(0);
		}

		private double checkNull(Double value) {
			return value != null ? value : 0.0;
		}
		
}

