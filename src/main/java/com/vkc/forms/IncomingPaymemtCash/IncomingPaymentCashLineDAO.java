package com.vkc.forms.IncomingPaymemtCash;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties({ "d:ID", "xmlns:d", "xmlns:m" })
public class IncomingPaymentCashLineDAO {

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
	    
	    @JsonProperty("d:PostingKey")
	    private String dPostingKey;
	    
	    @JsonProperty("d:DebitCreditCode")
	    private String dDebitCreditCode;
	    
	    @JsonProperty("d:DocumentItemText")
	    private String dDocumentItemText;
	    
	    @JsonProperty("d:AssignmentReference")
	    private String dAssignmentReference;
	    
	    @JsonProperty("d:GLAccount")
	    private String dGLAccount;
	    
	    @JsonProperty("d:AmountInCompanyCodeCurrency")
	    private Double dAmountInCompanyCodeCurrency;
	    
	    @JsonProperty("d:TaxAmountInCoCodeCrcy")
	    private Double dTaxAmountInCoCodeCrcy;
	    
	    @JsonProperty("d:AmountInFunctionalCurrency")
	    private Double dAmountInFunctionalCurrency;
	    
	    @JsonProperty("d:TaxBaseAmountInCoCodeCrcy")
	    private Double dTaxBaseAmountInCoCodeCrcy;
	    
	    @JsonProperty("d:AmountInTransactionCurrency")
	    private Double dAmountInTransactionCurrency;
	    
	    @JsonProperty("d:TaxAmount")
	    private Double dTaxAmount;
	    
	    @JsonProperty("d:CompanyCodeCurrency")
	    private String dCompanyCodeCurrency;
	    
	    @JsonProperty("d:FunctionalCurrency")
	    private String dFunctionalCurrency;
	    
	    @JsonProperty("d:TransactionCurrency")
	    private String dTransactionCurrency;
	    
	    @JsonProperty("d:GLAccountLongName")
	    private String dGLAccountLongName;
	    
	    @JsonProperty("d:GLAccountName")
	    private String dGLAccountName;
	    
	    @JsonProperty("d:HouseBank")
	    private String dHouseBank;
	    
	    @JsonProperty("d:BPBankAccountInternalID")
	    private String dBPBankAccountInternalID;
	    
	    @JsonProperty("d:HouseBankAccount")
	    private String dHouseBankAccount;
	    
	    @JsonProperty("d:CompanyCodeName")
	    private String dCompanyCodeName;
	    
	    @JsonProperty("d:CityNumber")
	    private String dCityNumber;
	    
	    @JsonProperty("d:PostalCode")
	    private String dPostalCode;
	    
	    @JsonProperty("d:Street")
	    private String dStreet;
	    
	    @JsonProperty("d:StreetName")
	    private String dStreetName;
	    
	    @JsonProperty("d:StreetPrefixName1")
	    private String dStreetPrefixName1;
	    
	    @JsonProperty("d:StreetPrefixName2")
	    private String dStreetPrefixName2;
	    
	    @JsonProperty("d:StreetSuffixName1")
	    private String dStreetSuffixName1;
	    
	    @JsonProperty("d:StreetSuffixName2")
	    private String dStreetSuffixName2;
	    
	    @JsonProperty("d:Region")
	    private String dRegion;
	    
	    @JsonProperty("d:Country")
	    private String dCountry;
	    
	    @JsonProperty("d:VATRegistration")
	    private String dVATRegistration;
	    
	    @JsonProperty("d:PostingDate")
	    private Date dPostingDate;
	    
	    @JsonProperty("d:DocumentDate")
	    private String dDocumentDate;
	    
	    @JsonProperty("d:AccountingDocumentHeaderText")
	    private String dAccountingDocumentHeaderText;
	    
	    @JsonProperty("d:Ledger")
	    private String dLedger;
}
