package com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
@Entity(name = "debit_memo_form")
public class ZFormEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "company_code")
	String companyCode;

	@Column(name = "accounting_document")
	String accountingDocument;

	@Column(name = "fiscal_year")
	String fiscalYear;

	@Column(name = "accounting_document_item")
	String accountingDocumentItem;

	@Column(name = "company_code_name")
	String companyCodeName;

	@Column(name = "supplier_name")
	String supplierName;
	

	@Column(name = "document_date")
	Date documentDate;

	@Column(name = "document_referenceid")
	String documentReferenceID;

	@Column(name = "tax_number3")
	String taxNumber3;

	@Column(name = "region1")
	String region1;

	@Column(name = "region")
	String region;

	@Column(name = "country")
	String country;

	@Column(name = "street1")
	String street1;

	@Column(name = "street_name1")
	String streetName1;

	@Column(name = "street_prefix_name11")
	String streetPrefixName11;

	@Column(name = "street_prefix_name21")
	String streetPrefixName21;

	@Column(name = "street_suffix_name11")
	String streetSuffixName11;

	@Column(name = "street_suffix_name21")
	String streetSuffixName21;

	@Column(name = "vat_registration")
	String vatRegistration;

	@Column(name = "country1")
	String country1;

	@Column(name = "region2")
	String region2;

	@Column(name = "igst_rate")
	Double igstRate;

	@Column(name = "sgst_rate")
	Double sgstRate;

	@Column(name = "cgst_rate")
	Double cgstRate;

	@Column(name = "cgst_amount")
	Double cgstAmount;

	@Column(name = "sgst_amount")
	Double sgstAmount;

	@Column(name = "igst_amount")
	Double igstAmount;

	@Column(name = "document_item_text")
	String documentItemText;

	@Column(name = "amount_in_company_code_currency")
	Double amountInCompanyCodeCurrency;

	@Column(name = "total_amount")
	Double totalAmount;

	@Column(name = "total_tax_amount")
	Double totalTaxAmount;

	@Column(name = "total_tax_value")
	Double totalTaxValue;

	@Column(name = "invoice_value")
	Double invoiceValue;

	@Column(name = "total_invoice_in_words")
	String totalInvoiceInWords;
	
	 @Column(name = "concatenated_datas")
	 String concatenatedDatas;
	 
	 @Column(name = "document_no")
	 String documentNO;
	 
	 @Column(name = "village_name")
	 String villageName;
	 
	 @Column(name = "building_no")
	 String buildingNo;
	 
	 @Column(name = "street")
	 String street;

	 @Column(name = "street_name")
     String streetName;
	 
	 @Column(name = "street_prefix_name1")
     String streetPrefixName1;

	 @Column(name = "street_prefix_name2")
   	 String streetPrefixName2;

	 @Column(name = "street_suffix_name1")
	 String streetSuffixName1;

	 @Column(name = "street_suffix_name2")
	 String streetSuffixName2;
	 
	 @Column(name = "supplier_region_name")
	 String supplierRegionName;
	 
	 @Column(name = "supplier_region_code")
	 String supplierRegionCode;
	 
	 @Column(name = "company_region_name")
	 String companyRegionName;
	 
	 @Column(name = "company_region_code")
	 String companyRegionCode;
	 
	 @Column(name = "supplier_address")
	 String supplierAddress;
	
	 @Column(name = "postal_code_1")
	 String PostalCode1;
	 
	 @Column(name ="s_no")
	 private Long siNo;
	 
	 
		@Column(name = "overall_total_tax")
		private Double overAllTotalTax;

		@Column(name = "overall_total_invvoice")
		private Double overAllTotalInvoice;

		@Column(name = "gst_tax_rate")
		Double gstTaxRate;
}
