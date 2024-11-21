package com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "debit_memo_form_sub_report")
public class ApSubReportEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	 

	@Column(name = "company_code")
	String companyCode;

	@Column(name = "document_no")
	String documentNo;

	@Column(name = "fiscal_year")
	String fiscalYear;
	
	@Column(name = "cgst_amount")
	Double cgstAmount;

	@Column(name = "sgst_amount")
	Double sgstAmount;

	@Column(name = "igst_amount")
	Double igstAmount;
	
	@Column(name = "total_tax_value")
	Double totalTaxValue;
	
	@Column(name = "invoice_value")
	Double invoiceValue;
	
	@Column(name = "total_amount")
	Double totalAmount;
	
	@Column(name = "gst_Tax_Rate")
	Double gstTaxRate;
	
	@Column(name = "gst_Tax_Amount")
	private Double gstTaxAmount;

	@Column(name = "totaltax")
	private Double totalTax;

	@Column(name = "over_all_total_tax")
	private Double overAlltotalTax;

	@Column(name = "invoicetotal")
	private Double invoiceTotal;
	
	@Column(name = "heading")
	private String heading;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "debit_memo_form_id")
	ZFormEntity zFormEntity;

	
}

