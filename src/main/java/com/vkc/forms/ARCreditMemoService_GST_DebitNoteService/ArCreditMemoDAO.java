package com.vkc.forms.ARCreditMemoService_GST_DebitNoteService;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties({ "d:ID", "xmlns:d", "xmlns:m" })
public class ArCreditMemoDAO {

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
	    @JsonProperty("d:DocumentDate")
	    private Date dDocumentDate;
	    @JsonProperty("d:DocumentReferenceID")
	    private String dDocumentReferenceID;
	    @JsonProperty("d:AccountingDocumentHeaderText")
	    private String dAccountingDocumentHeaderText;
	    @JsonProperty("d:AmountInCompanyCodeCurrency")
	    private Double dAmountInCompanyCodeCurrency;
	    @JsonProperty("d:CompanyCodeCurrency")
	    private String dCompanyCodeCurrency;
	    @JsonProperty("d:AmountInTransactionCurrency")
	    private Double dAmountInTransactionCurrency;
	    @JsonProperty("d:AmountInBalanceTransacCrcy")
	    private Double dAmountInBalanceTransacCrcy;
	    @JsonProperty("d:TransactionCurrency")
	    private String dTransactionCurrency;
	    @JsonProperty("d:BalanceTransactionCurrency")
	    private String dBalanceTransactionCurrency;
	    @JsonProperty("d:AccountingDocumentType")
	    private String dAccountingDocumentType;
	    @JsonProperty("d:AccountingDocumentItemType")
	    private String dAccountingDocumentItemType;
	    @JsonProperty("d:Customer")
	    private String dCustomer;
	    @JsonProperty("d:CustomerName")
	    private String dCustomerName;
	    @JsonProperty("d:CustomerFullName")
	    private String dCustomerFullName;
	    @JsonProperty("d:VATRegistration")
	    private String dVATRegistration;
	    @JsonProperty("d:TaxNumber3")
	    private String dTaxNumber3;
	    @JsonProperty("d:CityName")
	    private String dCityName;
	    @JsonProperty("d:CityNumber")
	    private String dCityNumber;
	    @JsonProperty("d:VillageName")
	    private String dVillageName;
	    @JsonProperty("d:AddresseeFullName")
	    private String dAddresseeFullName;
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
	    @JsonProperty("d:Country")
	    private String dCountry;
	    @JsonProperty("d:Region")
	    private String dRegion;
	    @JsonProperty("d:CompanyCodeName")
	    private String dCompanyCodeName;
	    @JsonProperty("d:CityNumber_1")
	    private String dCityNumber1;
	    @JsonProperty("d:CityName_1")
	    private String dCityName1;
	    @JsonProperty("d:PostalCode_1")
	    private String dPostalCode1;
	    @JsonProperty("d:Street_1")
	    private String dStreet1;
	    @JsonProperty("d:StreetName_1")
	    private String dStreetName1;
	    @JsonProperty("d:StreetPrefixName1_1")
	    private String dStreetPrefixName11;
	    @JsonProperty("d:StreetPrefixName2_1")
	    private String dStreetPrefixName21;
	    @JsonProperty("d:StreetSuffixName1_1")
	    private String dStreetSuffixName11;
	    @JsonProperty("d:StreetSuffixName2_1")
	    private String dStreetSuffixName21;
	    @JsonProperty("d:VATRegistration_1")
	    private String dVATRegistration1;
	    @JsonProperty("d:Country_1")
	    private String dCountry1;
	    @JsonProperty("d:RegionCode")
	    private String dRegionCode;
	    @JsonProperty("d:VillageName_1")
		private String dVillageName1;
		@JsonProperty("d:Building")
		private String dBuilding;
		@JsonProperty("d:CompanyRegionName")
		private String dCompanyRegionName;
		@JsonProperty("d:CompanyRegionCode")
		private String dCompanyRegionCode;


}

