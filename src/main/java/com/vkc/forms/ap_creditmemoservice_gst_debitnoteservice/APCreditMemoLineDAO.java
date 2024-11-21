package com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties({ "d:ID", "xmlns:d", "xmlns:m" })
public class APCreditMemoLineDAO {

	@JsonProperty("d:Country")
    private String dCountry;
    
	@JsonProperty("d:ID")
    private String dID;
    
	@JsonProperty("d:CompanyCode")
    private String dCompanyCode;
    
	@JsonProperty("d:AccountingDocument")
    private String dAccountingDocument;
    
	@JsonProperty("d:FiscalYear")
    private String dFiscalYear;
    
	@JsonProperty("d:AccountingDocumentItem")
    private String dAccountingDocumentItem;
    
	@JsonProperty("d:ChartOfAccounts")
    private String dChartOfAccounts;
    
	@JsonProperty("d:AccountingDocumentItemType")
    private String dAccountingDocumentItemType;
    
	@JsonProperty("d:FinancialAccountType")
    private String dFinancialAccountType;
    
	@JsonProperty("d:TaxCode")
    private String dTaxCode;
    
	@JsonProperty("d:TaxType")
    private String dTaxType;
    
	@JsonProperty("d:TaxItemGroup")
    private String dTaxItemGroup;
    
	@JsonProperty("d:DocumentItemText")
    private String dDocumentItemText;
    
	@JsonProperty("d:AssignmentReference")
    private String dAssignmentReference;
    
	@JsonProperty("d:CompanyCodeCurrency")
    private String dCompanyCodeCurrency;
    
	@JsonProperty("d:AmountInCompanyCodeCurrency")
    private Double dAmountInCompanyCodeCurrency;
    
	@JsonProperty("d:TaxAmountInCoCodeCrcy")
    private Double dTaxAmountInCoCodeCrcy;
    
	@JsonProperty("d:TransactionCurrency")
    private String dTransactionCurrency;
    
	@JsonProperty("d:AmountInTransactionCurrency")
    private Double dAmountInTransactionCurrency;
    
	@JsonProperty("d:AmountInBalanceTransacCrcy")
    private Double dAmountInBalanceTransacCrcy;
    
	@JsonProperty("d:TaxAmount")
    private Double dTaxAmount;
    
	@JsonProperty("d:GLAccount")
    private Double dGLAccount;
    
	@JsonProperty("d:IN_HSNOrSACCode")
    private String dINHSNOrSACCode;
    
	@JsonProperty("d:TaxBaseAmountInCoCodeCrcy")
    private Double dTaxBaseAmountInCoCodeCrcy;
    
	@JsonProperty("d:OriglTaxBaseAmountInCoCodeCrcy")
    private Double dOriglTaxBaseAmountInCoCodeCrcy;
    
	@JsonProperty("d:PostingDate")
    private String dPostingDate;
    
	@JsonProperty("d:BalanceTransactionCurrency")
    private String dBalanceTransactionCurrency;
    
	@JsonProperty("d:IGSTRATE")
    private Double dIGSTRATE;
    
	@JsonProperty("d:SGSTRATE")
    private Double dSGSTRATE;
    
	@JsonProperty("d:CGSTRATE")
    private Double dCGSTRATE;
    
	@JsonProperty("d:CGSTAMOUNT")
    private Double dCGSTAMOUNT;
    
	@JsonProperty("d:SGSTAMOUNT")
    private Double dSGSTAMOUNT;
    
	@JsonProperty("d:IGSTAMOUNT")
    private Double dIGSTAMOUNT;
    
	@JsonProperty("d:GLAccountLongName")
	private String dGLAccountLongName;
    
	@JsonProperty("d:GLAccountName")
    private String dGLAccountName;
	
    @JsonProperty("d:GSTTAXRATE")
    private Double dGSTTAXRATE;
    
    @JsonProperty("d:DocumentReferenceID")
    private String dDocumentReferenceID;
}
