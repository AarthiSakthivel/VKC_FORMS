package com.vkc.forms.IncomingPaymemtCash;

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
@Entity(name = "incoming_payment_form")
public class IncomingPaymentCashEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

	@Column(name = "company_code")
    String companyCode;

	@Column(name = "accounting_document")
    String accountingDocument;

	@Column(name = "fiscal_year")
    String fiscalYear;
    
	@Column(name = "company_code_name")
    String companyCodeName;
    
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

    @Column(name = "concatenated_datas")
    String concatenatedDatas;
    
    @Column(name = "posting_date")
    Date postingDate; 
    
	@Column(name = "gl_header_account_name")
    String glHeaderAccountName;
    
	@Column(name = "gl_line_account_name")
    String glLineAccountName;
    
	@Column(name = "accounting_document_header_text")
    String accountingDocumentHeaderText;
    
	@Column(name = "amount_in_company_code_currency")
	Double amountInCompanyCodeCurrency;
    
	@Column(name = "total_amount")
	Double totalAmount;
    
	@Column(name = "total_amount_in_words")
    String totalAmountInWords;
	
	@Column(name = "village_name")
	String villageName;
	
	@Column(name = "header_name")
	String heading;
}
