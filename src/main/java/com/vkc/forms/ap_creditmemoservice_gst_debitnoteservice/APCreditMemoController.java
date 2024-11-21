package com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public class APCreditMemoController {

	private final APCreditMemoService apCreditMemoService;

	private final UtilsService utilsService;

	private final ApSubFormRepository apSubFormRepository;
	private final JdbcTemplate jdbc;

	@Value("${app.invoice.path}")
	public String EInvoicePath;

	private final UtilsService Utils;

	private final APFormRepository zformRepo;

	@Description(value = "Navigate to Z Forms Page")
	@GetMapping(value = { "/ap-creditmemo" })
	public String getReport(Model model) throws Exception {
		model.addAttribute("reportAccess", null);
		return "APCreditMemo.html";
	}

	@PostMapping(value = "/ap-creditmemo/filter")
	public String getDocumentDetailsRelatedToDebitBoteBasedOnFyAndDocNum(
			@RequestParam(defaultValue = "") String fiscalYear, @RequestParam(defaultValue = "") String documentNo,
			@RequestParam(defaultValue = "") String companyCode, Model model) throws Exception {

		APCreditMemoDAO data = null;
		try {
			// Fetching the ZForm Debit Note Details
			data = apCreditMemoService.getInvoiceDetails(documentNo, fiscalYear, companyCode);
			model.addAttribute("data", data);
			model.addAttribute("reportAccess", "yes");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return "APCreditMemo.html";
	}

	@GetMapping("/ap-creditmemo/{documentNo}/{fiscalYear}/{companyCode}/generate")
	public ResponseEntity<byte[]> GeneratePrintLayout(@PathVariable String documentNo, @PathVariable String fiscalYear,
			@PathVariable String companyCode) throws Exception {

		String deleteFromDebitMemoFormSubReport = "DELETE FROM debit_memo_form_sub_report WHERE debit_memo_form_id IN ("
				+ "SELECT id FROM debit_memo_form WHERE accounting_document = ? "
				+ "AND fiscal_year = ? AND company_code = ?)";
		jdbc.update(deleteFromDebitMemoFormSubReport, documentNo, fiscalYear, companyCode);

		String sql = "DELETE FROM debit_memo_form WHERE accounting_document = ? AND fiscal_year = ? AND company_code = ?";
		jdbc.update(sql, documentNo, fiscalYear, companyCode);

		APCreditMemoDAO headerObj = apCreditMemoService.getInvoiceDetails(documentNo, fiscalYear, companyCode);

		List<APCreditMemoLineDAO> lineFilteredList = apCreditMemoService.LineAPIDetails(documentNo, fiscalYear,
				companyCode);

		ArrayList<ZFormEntity> objList = new ArrayList<>();
		ArrayList<ApSubReportEntity> subObjList = new ArrayList();
//		double sumOfCompanyCodeCurrency = 0.0;
//		double sumOfTaxAmount = 0.0;
//		double totalInvoiceAmount = 0.0;
		double totalGstRate = 0.0;
		double cgstAmount = 0.0;
		double igstAmount = 0.0;
		double sgstAmount = 0.0;
		double sumOfCompanyCodeCurrency = 0.0;
		double sumOfTaxAmount = 0.0;
		double totalInvoiceAmount = 0.0;
		double amountInCompanyCodeCurrency = 0.0;
		double sumOfCgstAmount = 0.0;
		double sumOfSgstAmount = 0.0;
		double sumOfIgstAmount = 0.0;
		double pageLine = 0.0;
		double totalCgstAmount = 0.0;
		double totalSgstAmount = 0.0;
		double totalIgstAmount = 0.0;
		double overallTotalTax = 0.0;
		String result = "";
		long siNo = 1;
		Map<Double, Double> gstTaxRateSums = new HashMap<>();
		Map<Double, Double> sumOfCgstAmounts = new HashMap<>();
		Map<Double, Double> sumOfSgstAmounts = new HashMap<>();
		Map<Double, Double> sumOfIgstAmounts = new HashMap<>();
		Map<Double, Double> sumOfTaxAmounts = new HashMap<>();
		for (APCreditMemoLineDAO data : lineFilteredList) {
			ZFormEntity obj = new ZFormEntity();
			ApSubReportEntity subObj = new ApSubReportEntity();

			String street1 = headerObj.getDStreet1();
			String streetName1 = headerObj.getDStreetName1();
			String streetPrefixName11 = headerObj.getDStreetPrefixName11();
			String streetPrefixName21 = headerObj.getDStreetPrefixName21();
			String streetSuffixName11 = headerObj.getDStreetSuffixName11();
			String streetSuffixName21 = headerObj.getDStreetSuffixName21();
			String companyRegionName = headerObj.getDCompanyRegionName();
			String companyRegionCode = headerObj.getDPostalCode1();

			String concatenatedString = street1 + streetName1 + streetPrefixName11 + streetPrefixName21
					+ streetSuffixName11 + streetSuffixName21 + ", " + companyRegionName + ", " + companyRegionCode;

			String street = headerObj.getDStreet();
			String streetName = headerObj.getDStreetName();
			String streetPrefixName1 = headerObj.getDStreetPrefixName1();
			String streetPrefixName2 = headerObj.getDStreetPrefixName2();
			String streetSuffixName1 = headerObj.getDStreetSuffixName1();
			String streetSuffixName2 = headerObj.getDStreetSuffixName2();
			String supplierRegionName = headerObj.getDSupplierRegionName();
			String supplierRegionCode = headerObj.getDSupplierRegionCode();

			String supplierAddress = street + streetName + streetPrefixName1 + streetPrefixName2 + streetSuffixName1
					+ streetSuffixName2 + ", " + supplierRegionName + ", " + supplierRegionCode;

			obj.setSiNo(siNo);

			sumOfCompanyCodeCurrency += Math.abs(
					(data.getDAmountInCompanyCodeCurrency() != null) ? data.getDAmountInCompanyCodeCurrency() : 0.0);

			totalGstRate += Math.abs(data.getDCGSTRATE() != null ? (data.getDCGSTRATE()) : 0.0)
					+ Math.abs(data.getDSGSTRATE() != null ? (data.getDSGSTRATE()) : 0.0)
					+ Math.abs(data.getDIGSTRATE() != null ? (data.getDIGSTRATE()) : 0.0);

			// totalInvoiceAmount = Math.abs(sumOfCompanyCodeCurrency + sumOfTaxAmount);

			cgstAmount += Math.abs(data.getDCGSTAMOUNT());
			igstAmount += Math.abs(data.getDIGSTAMOUNT());
			sgstAmount += Math.abs(data.getDSGSTAMOUNT());
			sumOfTaxAmount += Math.abs(data.getDCGSTAMOUNT() != null ? data.getDCGSTAMOUNT() : 0.0)
					+ Math.abs(data.getDIGSTAMOUNT() != null ? data.getDIGSTAMOUNT() : 0.0)
					+ Math.abs(data.getDSGSTAMOUNT() != null ? data.getDSGSTAMOUNT() : 0.0);
			amountInCompanyCodeCurrency = data.getDAmountInCompanyCodeCurrency() != null
					? Math.abs(data.getDAmountInCompanyCodeCurrency())
					: 0.0;
			Double totalTaxRate = Math.abs(totalGstRate);
			Double totalAmount = Math.abs(sumOfCompanyCodeCurrency);
			Double totalTaxValue = Math.abs(sumOfTaxAmount);
			obj.setAccountingDocument(documentNo);
			obj.setSupplierName(headerObj.getDSupplierName());
			obj.setDocumentItemText(data.getDDocumentItemText());
//			obj.setIgstAmount(igstAmount);
//			obj.setCgstAmount(cgstAmount);
//			obj.setSgstAmount(sgstAmount);
			obj.setIgstRate(data.getDIGSTRATE());
			obj.setCgstRate(data.getDCGSTRATE());
			obj.setSgstRate(data.getDSGSTRATE());
			obj.setFiscalYear(fiscalYear);
			obj.setCompanyCode(companyCode);
			obj.setCompanyCodeName(headerObj.getDCompanyCodeName());
			obj.setVatRegistration(headerObj.getDVATRegistration());
			obj.setDocumentDate(headerObj.getDDocumentDate());
			obj.setDocumentReferenceID(headerObj.getDDocumentReferenceID());
			obj.setAmountInCompanyCodeCurrency(amountInCompanyCodeCurrency);
			obj.setRegion1(headerObj.getDRegion1());
			obj.setCountry1(headerObj.getDCountry1());
			obj.setStreet1(headerObj.getDStreet1());
			obj.setStreetName1(headerObj.getDStreetName1());
			obj.setStreetPrefixName11(headerObj.getDStreetPrefixName11());
			obj.setStreetPrefixName21(headerObj.getDStreetPrefixName21());
			obj.setStreetSuffixName11(headerObj.getDStreetSuffixName11());
			obj.setStreetSuffixName21(headerObj.getDStreetSuffixName21());
			obj.setConcatenatedDatas(concatenatedString);
			obj.setTaxNumber3(headerObj.getDTaxNumber3());
			obj.setRegion(headerObj.getDRegionCode());
			obj.setTotalTaxAmount(totalTaxRate);
			obj.setTotalAmount(totalAmount);
			obj.setTotalTaxValue(totalTaxValue);
			obj.setDocumentNO(headerObj.getDAccountingDocument());
			obj.setInvoiceValue(totalInvoiceAmount);

			obj.setVillageName(headerObj.getDVillageName1());
			obj.setBuildingNo(headerObj.getDBuilding());
			obj.setSupplierAddress(supplierAddress);
			obj.setPostalCode1(headerObj.getDPostalCode1());

			double gstTaxRate = data.getDGSTTAXRATE() != null ? data.getDGSTTAXRATE() : 0.0;
			cgstAmount = data.getDCGSTAMOUNT() != null ? Math.abs(data.getDCGSTAMOUNT()) : 0.0;
			sgstAmount = data.getDSGSTAMOUNT() != null ? Math.abs(data.getDSGSTAMOUNT()) : 0.0;
			igstAmount = data.getDIGSTAMOUNT() != null ? Math.abs(data.getDIGSTAMOUNT()) : 0.0;
//			sumOfCgstAmount = roundOff(cgstAmount);
//			sumOfSgstAmount = roundOff(sgstAmount);
//			sumOfIgstAmount = roundOff(igstAmount);

			subObj.setDocumentNo(documentNo);
			subObj.setCompanyCode(companyCode);
			subObj.setFiscalYear(fiscalYear);

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

			gstTaxRateSums.put(gstTaxRate, gstTaxRateSums.getOrDefault(gstTaxRate, 0.0) + amountInCompanyCodeCurrency);
			// TaxableValue Field
			subObj.setGstTaxRate(gstTaxRate);
			// //TaxableValue value
			subObj.setGstTaxAmount(gstTaxRateSums.get(gstTaxRate));

			obj.setGstTaxRate(gstTaxRate);
			
			
			
			// Dynamic Gst TaxTotal for alst pageFooter and pageFOoter
			sumOfCgstAmounts.put(gstTaxRate, sumOfCgstAmounts.getOrDefault(gstTaxRate, 0.0) + cgstAmount);
			sumOfSgstAmounts.put(gstTaxRate, sumOfSgstAmounts.getOrDefault(gstTaxRate, 0.0) + sgstAmount);
			sumOfIgstAmounts.put(gstTaxRate, sumOfIgstAmounts.getOrDefault(gstTaxRate, 0.0) + igstAmount);
			// Particular Tax Total amount // Tax Calulation
			double totalTaxForRate = sumOfCgstAmounts.get(gstTaxRate) + sumOfSgstAmounts.get(gstTaxRate)
					+ sumOfIgstAmounts.get(gstTaxRate);
			sumOfTaxAmounts.put(gstTaxRate, totalTaxForRate);

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
			obj.setOverAllTotalInvoice(totalInvoiceAmount);
			String totalInvoiceAmountInWords = NumberToWordsConverter.convertWithPaisa(totalInvoiceAmount);
			obj.setTotalInvoiceInWords(totalInvoiceAmountInWords);
			subObj.setInvoiceTotal(totalInvoiceAmount);

			objList.add(obj);
			subObj.setZFormEntity(obj);
			subObjList.add(subObj);
			siNo = siNo + 1;
		}
		Map<Double, ApSubReportEntity> mergedEntities = new HashMap<>();
		for (ApSubReportEntity entity : subObjList) {
			double gstTaxRate = entity.getGstTaxRate();
			double accumulatedGstTaxAmount = gstTaxRateSums.get(gstTaxRate);
			double accumulatedTotalTaxforAmountLeft = sumOfTaxAmounts.get(gstTaxRate);
			double totalcgstAmount = sumOfCgstAmounts.get(gstTaxRate);
			double totaligstAmount = sumOfIgstAmounts.get(gstTaxRate);
			double totalsgsAmount = sumOfSgstAmounts.get(gstTaxRate);
			entity.setGstTaxAmount(accumulatedGstTaxAmount);

			if (mergedEntities.containsKey(gstTaxRate)) {
				ApSubReportEntity existingEntity = mergedEntities.get(gstTaxRate);
				existingEntity.setCgstAmount(existingEntity.getCgstAmount() + existingEntity.getCgstAmount());
				existingEntity.setSgstAmount(existingEntity.getSgstAmount() + existingEntity.getSgstAmount());
				existingEntity.setIgstAmount(existingEntity.getIgstAmount() + existingEntity.getIgstAmount());

				existingEntity.setTotalTax(roundOff(accumulatedTotalTaxforAmountLeft));

			} else {
				mergedEntities.put(gstTaxRate, entity);
			}
		}

		System.out.println("mergedEntity" + mergedEntities);
		List<ApSubReportEntity> mergedEntitiesList = new ArrayList<>(mergedEntities.values());

		zformRepo.saveAll(objList);

		System.out.println("objList" + objList);
		System.out.println("subObjList" + subObjList);
		apSubFormRepository.saveAll(mergedEntitiesList);
//			zformRepo.saveAll(objList);
//			apSubFormRepository.saveAll(subObjList);

		HashMap<String, Object> map = new HashMap();
		map.put("documentNo", documentNo);
		map.put("fiscalYear", fiscalYear);
		map.put("companyCode", companyCode);

		JasperReport report = JasperCompileManager
				.compileReport(EInvoicePath + "\\APCreditMemoService_GST_DebitNoteService.jrxml");

		InputStream subReportStreamAPgst = getClass().getResourceAsStream("/SubReportApFormGST.jrxml");
		JasperReport subreportsAPgst = JasperCompileManager.compileReport(subReportStreamAPgst);
		map.put("Sub_AP_Report_GST", subreportsAPgst);

		InputStream taxableSubReportAPgstStream = getClass().getResourceAsStream("/TaxableSubReportApForm.jrxml");
		JasperReport taxableSubreportsAPgst = JasperCompileManager.compileReport(taxableSubReportAPgstStream);
		// Append the Taxable Sub report
		map.put("Taxable_Sub_AP", taxableSubreportsAPgst);

		Connection con;
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(Utils.databaseConnectionUrl, Utils.databaseUsername, Utils.databasePassword);
		JasperPrint jasperPrint = JasperFillManager.fillReport(report, map, con);
		byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
		con.close();

		return ResponseEntity.ok().header("Content-Type", "application/pdf")
				.header("Content-Disposition", "inline; filename=AP_CreditMemo_" + documentNo + ".pdf")
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
