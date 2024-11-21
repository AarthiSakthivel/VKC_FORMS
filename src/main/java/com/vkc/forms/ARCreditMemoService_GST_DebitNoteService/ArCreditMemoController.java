package com.vkc.forms.ARCreditMemoService_GST_DebitNoteService;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.jfree.util.ObjectUtilities;
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

import com.vkc.forms.utils.UtilsService;

import lombok.RequiredArgsConstructor;

import com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice.APFormRepository;
import com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice.ApSubReportEntity;
import com.vkc.forms.utils.NumberToWordsConverter;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/vkc/forms")
public class ArCreditMemoController {

	private final ArCreditMemoService arCreditMemoService;

	private final UtilsService Utils;

	private final JdbcTemplate jdbc;

	private final ArFormRepository zformRepo;

	private final ARSubReportGSTRepository aRSubReportGSTRepository;

	@Value("${app.invoice.path}")
	public String EInvoicePath;

	@Description(value = "Navigate to Z Forms Page")
	@GetMapping(value = { "/ar-creditmemo" })
	public String getReport(Model model) throws Exception {
		model.addAttribute("reportAccess", null);
		return "ArCreditMemo.html";
	}

	@PostMapping(value = "/ar-creditmemo/filter")
	public String getDocumentDetailsRelatedToDebitBoteBasedOnFyAndDocNum(
			@RequestParam(defaultValue = "") String fiscalYear, @RequestParam(defaultValue = "") String documentNo,
			@RequestParam(defaultValue = "") String companyCode, Model model) throws Exception {

		ArCreditMemoDAO data = null;
		try {
			// Fetching the ZForm Debit Note Details
			data = arCreditMemoService.getInvoiceDetails(documentNo, fiscalYear, companyCode);
			model.addAttribute("data", data);
			model.addAttribute("reportAccess", "yes");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return "ArCreditMemo.html";
	}

	@GetMapping("/ar-creditmemo/{documentNo}/{fiscalYear}/{companyCode}/generate")
	public ResponseEntity<byte[]> GeneratePrintLayout(@PathVariable String documentNo, @PathVariable String fiscalYear,
			@PathVariable String companyCode) throws Exception {

		String deleteFromDebitMemoEntityARSubReport = "DELETE FROM debit_memo_entity_sub_report WHERE debit_memo_entity_id IN ("
				+ "SELECT id FROM debit_memo_entity WHERE accounting_document = ? "
				+ "AND fiscal_year = ? AND company_code = ?)";
		jdbc.update(deleteFromDebitMemoEntityARSubReport, documentNo, fiscalYear, companyCode);

		String sql = "DELETE FROM debit_memo_entity WHERE accounting_document = ? AND fiscal_year = ? AND company_code = ?";
		jdbc.update(sql, documentNo, fiscalYear, companyCode);

		ArCreditMemoDAO headerObj = arCreditMemoService.getInvoiceDetails(documentNo, fiscalYear, companyCode);

		List<ArCreditMemoLineDAO> lineFilteredList = arCreditMemoService.LineAPIDetails(documentNo, fiscalYear,
				companyCode);

		ArrayList<ZFormEntity> objList = new ArrayList<>();
		ArrayList<ARSubReportEntity> subObjList = new ArrayList<>();

		double sumOfCompanyCodeCurrency = 0.0;
		double sumOfTaxAmount = 0.0;
		double totalInvoiceAmount = 0.0;
		double totalGstRate = 0.0;
		long siNo = 1;
//		Double cgstAmount = 0.0;
//		Double igstAmount = 0.0;
//		Double sgstAmount = 0.0;
		double totalCgstAmount = 0.0;
		double totalSgstAmount = 0.0;
		double totalIgstAmount = 0.0;
		double overallTotalTax = 0.0;
		double amountInCompanyCodeCurrency = 0.0;
		String result = "";
		Map<Double, Double> gstTaxRateSums = new HashMap<>();
		Map<Double, Double> sumOfCgstAmounts = new HashMap<>();
		Map<Double, Double> sumOfSgstAmounts = new HashMap<>();
		Map<Double, Double> sumOfIgstAmounts = new HashMap<>();
		Map<Double, Double> sumOfTaxAmounts = new HashMap<>();

		for (ArCreditMemoLineDAO data : lineFilteredList) {
			ZFormEntity obj = new ZFormEntity();
			ARSubReportEntity subObj = new ARSubReportEntity();

			// Calculate the necessary values

			String street1 = headerObj.getDStreet1();
			String streetName1 = headerObj.getDStreetName1();
			String streetPrefixName11 = headerObj.getDStreetPrefixName11();
			String streetPrefixName21 = headerObj.getDStreetPrefixName21();
			String streetSuffixName11 = headerObj.getDStreetSuffixName11();
			String streetSuffixName21 = headerObj.getDStreetSuffixName21();
			String region1 = headerObj.getDCompanyRegionName();
			String country1 = headerObj.getDCompanyRegionCode();
			String postalCode = headerObj.getDPostalCode1();

			String concatenatedString = street1 + streetName1 + streetPrefixName11 + streetPrefixName21
					+ streetSuffixName11 + streetSuffixName21 + ", " + region1 + ", " + postalCode;

			String street = headerObj.getDStreet();
			String streetName = headerObj.getDStreetName();
			String streetPrefixName1 = headerObj.getDStreetPrefixName1();
			String streetPrefixName2 = headerObj.getDStreetPrefixName2();
			String streetSuffixName1 = headerObj.getDStreetSuffixName1();
			String streetSuffixName2 = headerObj.getDStreetSuffixName2();
			String supplierRegionName = headerObj.getDCompanyRegionName();
			String supplierRegionCode = headerObj.getDCompanyRegionCode();

			String supplierAddress = street + streetName + streetPrefixName1 + streetPrefixName2 + streetSuffixName1
					+ streetSuffixName2 + ", " + supplierRegionName + ", " + supplierRegionCode;

			sumOfCompanyCodeCurrency += Math
					.abs(data.getDAmountInCompanyCodeCurrency() != null ? data.getDAmountInCompanyCodeCurrency() : 0.0);
			sumOfTaxAmount += Math.abs(data.getDCGSTAMOUNT() != null ? data.getDCGSTAMOUNT() : 0.0)
					+ Math.abs(data.getDSGSTAMOUNT() != null ? data.getDSGSTAMOUNT() : 0.0)
					+ Math.abs(data.getDIGSTAMOUNT() != null ? data.getDIGSTAMOUNT() : 0.0);

			totalGstRate += Math.abs(data.getDCGSTRATE() != null ? (data.getDCGSTRATE()) : 0.0)
					+ Math.abs(data.getDSGSTRATE() != null ? (data.getDSGSTRATE()) : 0.0)
					+ Math.abs(data.getDIGSTRATE() != null ? (data.getDIGSTRATE()) : 0.0);

			totalInvoiceAmount = Math.abs(sumOfCompanyCodeCurrency + sumOfTaxAmount);

			Double totalTaxRate = Math.abs(totalGstRate);
			Double totalAmount = Math.abs(sumOfCompanyCodeCurrency);
			amountInCompanyCodeCurrency = Math.abs(data.getDAmountInCompanyCodeCurrency());

			// Set values to obj
			obj.setSiNo(siNo);
			Double totalTaxValue = Math.abs(checkNull(sumOfTaxAmount));
			obj.setAccountingDocument(checkNull(documentNo));
			obj.setDocumentItemText(checkNull(data.getDDocumentItemText()));

			obj.setIgstRate(checkNull(data.getDIGSTRATE()));
			obj.setCgstRate(checkNull(data.getDCGSTRATE()));
			obj.setSgstRate(checkNull(data.getDSGSTRATE()));
			obj.setFiscalYear(checkNull(fiscalYear));
			obj.setConcatenatedDatas(checkNull(concatenatedString));
			obj.setCompanyCode(checkNull(companyCode));
			obj.setCompanyCodeName(checkNull(headerObj.getDCompanyCodeName()));
			obj.setVatRegistration(checkNull(headerObj.getDVATRegistration1()));
			obj.setDocumentDate(checkNull(headerObj.getDDocumentDate()));
			obj.setDocumentReferenceID(checkNull(headerObj.getDDocumentReferenceID()));
			obj.setAmountInCompanyCodeCurrency(checkNull(amountInCompanyCodeCurrency));
			obj.setRegion1(checkNull(headerObj.getDRegionCode()));
			obj.setCountry1(checkNull(headerObj.getDCountry1()));
			obj.setStreet1(checkNull(headerObj.getDStreet1()));
			obj.setStreetName1(checkNull(headerObj.getDStreetName1()));
			obj.setStreetPrefixName11(checkNull(headerObj.getDStreetPrefixName11()));
			obj.setStreetPrefixName21(headerObj.getDStreetPrefixName21());
			obj.setStreetSuffixName11(headerObj.getDStreetSuffixName11());
			obj.setStreetSuffixName21(headerObj.getDStreetSuffixName21());
			obj.setTaxNumber3(headerObj.getDTaxNumber3());
			obj.setRegion(headerObj.getDRegionCode());
			obj.setDocumentNo(headerObj.getDAccountingDocument());
			obj.setTotalTaxAmount(totalTaxRate);
			obj.setTotalAmount(totalAmount);
			obj.setTotalTaxValue(totalTaxValue);
			obj.setInvoiceValue(totalInvoiceAmount);
			obj.setVillageName(headerObj.getDVillageName1());
			obj.setBuildingNo(headerObj.getDBuilding());

			obj.setSupplierAddress(supplierAddress);
			obj.setSupplierName(headerObj.getDCustomerName());

			// Check for duplicates before adding to the list

			// Subreport Entity population
			double gstTaxRate = data.getDGSTTAXRATE() != null ? data.getDGSTTAXRATE() : 0.0;
			double cgstAmount = data.getDCGSTAMOUNT() != null ? Math.abs(data.getDCGSTAMOUNT()) : 0.0;
			double sgstAmount = data.getDSGSTAMOUNT() != null ? Math.abs(data.getDSGSTAMOUNT()) : 0.0;
			double igstAmount = data.getDIGSTAMOUNT() != null ? Math.abs(data.getDIGSTAMOUNT()) : 0.0;

			subObj.setAccountingDocument(documentNo);
			subObj.setCompanyCode(companyCode);
			subObj.setFiscalYear(fiscalYear);
			gstTaxRateSums.put(gstTaxRate, gstTaxRateSums.getOrDefault(gstTaxRate, 0.0) + amountInCompanyCodeCurrency);
			subObj.setGstTaxAmount(gstTaxRateSums.get(gstTaxRate));
			subObj.setGstTaxRate(gstTaxRate);
			obj.setGstTaxRate(gstTaxRate);

			String taxCode = checkNull(data.getDTaxCode());

			Set<String> sgstCgstCodes = new HashSet<>();
			sgstCgstCodes.add("I0");
			sgstCgstCodes.add("I1");
			sgstCgstCodes.add("I2");
			sgstCgstCodes.add("I3");
			sgstCgstCodes.add("I4");
			Set<String> igstCodes = new HashSet<>();
			igstCodes.add("I5");
			igstCodes.add("I6");
			igstCodes.add("I7");
			igstCodes.add("I8");
			igstCodes.add("I9");
			Set<String> rcmD_SgstCgstCodes = new HashSet<>();
			rcmD_SgstCgstCodes.add("R0");
			rcmD_SgstCgstCodes.add("R1");
			rcmD_SgstCgstCodes.add("R2");
			rcmD_SgstCgstCodes.add("R3");
			rcmD_SgstCgstCodes.add("R4");
			Set<String> rcmD_IgstCodes = new HashSet<>();
			rcmD_IgstCodes.add("R5");
			rcmD_IgstCodes.add("R6");
			rcmD_IgstCodes.add("R7");
			rcmD_IgstCodes.add("R8");
			rcmD_IgstCodes.add("R9");
			Set<String> op_SgstCgstCodes = new HashSet<>();
			op_SgstCgstCodes.add("O0");
			op_SgstCgstCodes.add("O1");
			op_SgstCgstCodes.add("O2");
			op_SgstCgstCodes.add("O3");
			op_SgstCgstCodes.add("O4");
			Set<String> op_IgstCodes = new HashSet<>();
			op_IgstCodes.add("O5");
			op_IgstCodes.add("O6");
			op_IgstCodes.add("O7");
			op_IgstCodes.add("O8");
			op_IgstCodes.add("O9");

			if (sgstCgstCodes.contains(taxCode)) {
				result = "GST@";
			} else if (igstCodes.contains(taxCode)) {
				result = "IGST@";
			} else if (rcmD_SgstCgstCodes.contains(taxCode)) {
				result = "RGST@";
			} else if (rcmD_IgstCodes.contains(taxCode)) {
				result = "RIGST@";
			} else if (op_SgstCgstCodes.contains(taxCode)) {
				result = "GST@";
			} else if (op_IgstCodes.contains(taxCode)) {
				result = "IGST@";
			} else {
				result = "Unknown tax code";
			}

			subObj.setHeading(result);

			// Dynamic Gst TaxTotal for last pageFooter and pageFooter
//
//			if ("GST@".equals(result)) {
//				// Update CGST and SGST sums
//				sumOfCgstAmounts.put(gstTaxRate, sumOfCgstAmounts.getOrDefault(gstTaxRate, 0.0) + cgstAmount);
//				sumOfSgstAmounts.put(gstTaxRate, sumOfSgstAmounts.getOrDefault(gstTaxRate, 0.0) + sgstAmount);
//				sumOfIgstAmounts.put(gstTaxRate, 0.0); // Set IGST amount to 0.0 for GST case
//			} else if ("IGST@".equals(result)) {
//				// Update IGST sum
//				sumOfIgstAmounts.put(gstTaxRate, sumOfIgstAmounts.getOrDefault(gstTaxRate, 0.0) + igstAmount);
//				sumOfCgstAmounts.put(gstTaxRate, 0.0); // Set CGST and SGST amounts to 0.0 for IGST case
//				sumOfSgstAmounts.put(gstTaxRate, 0.0);
//			}
//			else {
//				throw new IllegalArgumentException("Unknown tax code: " + taxCode);
//			}

			sumOfCgstAmounts.put(gstTaxRate, sumOfCgstAmounts.getOrDefault(gstTaxRate, 0.0) + cgstAmount);
			sumOfSgstAmounts.put(gstTaxRate, sumOfSgstAmounts.getOrDefault(gstTaxRate, 0.0) + sgstAmount);
			sumOfIgstAmounts.put(gstTaxRate, sumOfIgstAmounts.getOrDefault(gstTaxRate, 0.0) + igstAmount);
			double totalTaxForRate = sumOfCgstAmounts.get(gstTaxRate) + sumOfSgstAmounts.get(gstTaxRate)
					+ sumOfIgstAmounts.get(gstTaxRate);
			sumOfTaxAmounts.put(gstTaxRate, totalTaxForRate);
			subObj.setDocumentNo(documentNo);
			subObj.setCgstAmount(sumOfCgstAmounts.get(gstTaxRate));
			subObj.setSgstAmount(sumOfSgstAmounts.get(gstTaxRate));
			subObj.setIgstAmount(sumOfIgstAmounts.get(gstTaxRate));
			subObj.setTotalTax(sumOfTaxAmounts.get(gstTaxRate));
			totalCgstAmount += cgstAmount;
			totalSgstAmount += sgstAmount;
			totalIgstAmount += igstAmount;
			overallTotalTax = totalCgstAmount + totalSgstAmount + totalIgstAmount;
			obj.setOverAllTotalTax(overallTotalTax);
			subObj.setOverAlltotalTax(overallTotalTax);

			totalInvoiceAmount = roundOff(Math.abs(sumOfCompanyCodeCurrency + overallTotalTax));
			String totalInvoiceAmountInWords = NumberToWordsConverter.convertWithPaisa((totalInvoiceAmount));
			obj.setTotalInvoiceInWords(totalInvoiceAmountInWords);
			obj.setOverAllTotalInvoice(totalInvoiceAmount);
			subObj.setInvoiceTotal(totalInvoiceAmount);

			objList.add(obj);
			subObj.setZFormEntity(obj);
			subObjList.add(subObj);

			siNo = siNo + 1;
		}

		Map<Double, ARSubReportEntity> mergedEntities = new HashMap<>();
		for (ARSubReportEntity entity : subObjList) {
			double gstTaxRate = entity.getGstTaxRate();
			double accumulatedGstTaxAmount = gstTaxRateSums.get(gstTaxRate);
			double accumulatedTotalTaxforAmountLeft = sumOfTaxAmounts.get(gstTaxRate);
			double totalcgstAmount = sumOfCgstAmounts.get(gstTaxRate);
			double totaligstAmount = sumOfIgstAmounts.get(gstTaxRate);
			double totalsgsAmount = sumOfSgstAmounts.get(gstTaxRate);
			entity.setGstTaxAmount(accumulatedGstTaxAmount);

			if (mergedEntities.containsKey(gstTaxRate)) {
				ARSubReportEntity existingEntity = mergedEntities.get(gstTaxRate);
				existingEntity.setCgstAmount(existingEntity.getCgstAmount() + existingEntity.getCgstAmount());
				existingEntity.setSgstAmount(existingEntity.getSgstAmount() + existingEntity.getSgstAmount());
				existingEntity.setIgstAmount(existingEntity.getIgstAmount() + existingEntity.getIgstAmount());

				existingEntity.setTotalTax(roundOff(accumulatedTotalTaxforAmountLeft));

			} else {
				mergedEntities.put(gstTaxRate, entity);
			}
		}

		System.out.println("mergedEntity" + mergedEntities);
		List<ARSubReportEntity> mergedEntitiesList = new ArrayList<>(mergedEntities.values());

		zformRepo.saveAll(objList);

		System.out.println("objList" + objList);
		System.out.println("subObjList" + subObjList);
		aRSubReportGSTRepository.saveAll(mergedEntitiesList);

		HashMap<String, Object> map = new HashMap<>();
		map.put("documentNo", documentNo);
		map.put("fiscalYear", fiscalYear);
		map.put("companyCode", companyCode);

		JasperReport report = JasperCompileManager
				.compileReport(EInvoicePath + "\\ArCreditMemoService_GST_DebitNoteService.jrxml");

		InputStream subReportStreamARgst = getClass().getResourceAsStream("/SubARformGST.jrxml");
		JasperReport subreportsARgst = JasperCompileManager.compileReport(subReportStreamARgst);
		map.put("Sub_AR_Report_GST", subreportsARgst);

		InputStream taxableSubReportARgstStream = getClass().getResourceAsStream("/TaxableSubARGST.jrxml");
		JasperReport taxableSubreportsARgst = JasperCompileManager.compileReport(taxableSubReportARgstStream);
		map.put("Taxable_Sub_AR", taxableSubreportsARgst);

		Connection con;
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(Utils.databaseConnectionUrl, Utils.databaseUsername, Utils.databasePassword);
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, con);
		byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
		con.close();

		return ResponseEntity.ok().header("Content-Type", "application/pdf")
				.header("Content-Disposition", "inline; filename=Ar_CreditMemo_" + documentNo + ".pdf")
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
