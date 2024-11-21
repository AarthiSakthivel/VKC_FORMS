package com.vkc.forms.ap_creditmemoservice_gst_debitnoteservice;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties({ "d:ID", "xmlns:d", "xmlns:m" })
public class APCreditMemoDAO {

	@JsonProperty("d:CityName")
	private String dCityName;
	@JsonProperty("d:StreetPrefixName1_1")
	private String dStreetPrefixName11;
	@JsonProperty("d:CompanyCodeCurrency")
	private String dCompanyCodeCurrency;
	@JsonProperty("d:StreetPrefixName2_1")
	private String dStreetPrefixName21;
	@JsonProperty("d:AccountingDocumentHeaderText")
	private String dAccountingDocumentHeaderText;
	@JsonProperty("d:AccountingDocumentItemType")
	private String dAccountingDocumentItemType;
	@JsonProperty("d:HouseNumber")
	private String dHouseNumber;
	@JsonProperty("d:AccountingDocumentType")
	private String dAccountingDocumentType;
	@JsonProperty("d:TaxNumber3")
	private String dTaxNumber3;
	@JsonProperty("d:VillageName")
	private String dVillageName;
	@JsonProperty("d:TransactionCurrency")
	private String dTransactionCurrency;
	@JsonProperty("d:PostalCode_1")
	private String dPostalCode1;
	@JsonProperty("d:CompanyCodeName")
	private String dCompanyCodeName;
	@JsonProperty("d:CompanyCode")
	private String dCompanyCode;
	@JsonProperty("d:SupplierName")
	private String dSupplierName;
	@JsonProperty("d:DocumentDate")
	private Date dDocumentDate;
	@JsonProperty("d:Country_1")
	private String dCountry1;
	@JsonProperty("d:AccountingDocument")
	private String dAccountingDocument;
	@JsonProperty("d:CityNumber_1")
	private String dCityNumber1;
	@JsonProperty("d:StreetName_1")
	private String dStreetName1;
	@JsonProperty("d:CityNumber")
	private String dCityNumber;
	@JsonProperty("d:AccountingDocumentItem")
	private String dAccountingDocumentItem;
	@JsonProperty("d:AmountInBalanceTransacCrcy")
	private Double dAmountInBalanceTransacCrcy;
	@JsonProperty("d:Street")
	private String dStreet;
	@JsonProperty("d:StreetPrefixName2")
	private String dStreetPrefixName2;
	@JsonProperty("d:StreetSuffixName2_1")
	private String dStreetSuffixName21;
	@JsonProperty("d:StreetSuffixName1_1")
	private String dStreetSuffixName11;
	@JsonProperty("d:DocumentReferenceID")
	private String dDocumentReferenceID;
	@JsonProperty("d:StreetPrefixName1")
	private String dStreetPrefixName1;
	@JsonProperty("d:Region")
	private String dRegion;
	@JsonProperty("d:Street_1")
	private String dStreet1;
	@JsonProperty("d:StreetName")
	private String dStreetName;
	@JsonProperty("d:AmountInCompanyCodeCurrency")
	private Double dAmountInCompanyCodeCurrency;
	@JsonProperty("d:PostalCode")
	private String dPostalCode;
	@JsonProperty("d:BalanceTransactionCurrency")
	private String dBalanceTransactionCurrency;
	@JsonProperty("d:RegionCode")
	private String dRegionCode;
	@JsonProperty("d:Country")
	private String dCountry;
	@JsonProperty("d:StreetSuffixName1")
	private String dStreetSuffixName1;
	@JsonProperty("d:StreetSuffixName2")
	private String dStreetSuffixName2;
	@JsonProperty("d:VATRegistration")
	private String dVATRegistration;
	@JsonProperty("d:Region_1")
	private String dRegion1;
	@JsonProperty("d:AmountInTransactionCurrency")
	private Double dAmountInTransactionCurrency;
	@JsonProperty("d:Region_2")
	private String dRegion2;
	@JsonProperty("d:FiscalYear")
	private String dFiscalYear;
	@JsonProperty("d:SupplierRegionName")
	private String dSupplierRegionName;
	@JsonProperty("d:SupplierRegionCode")
	private String dSupplierRegionCode;
	@JsonProperty("d:VillageName_1")
	private String dVillageName1;
	@JsonProperty("d:Building")
	private String dBuilding;
	@JsonProperty("d:CompanyRegionName")
	private String dCompanyRegionName;
	@JsonProperty("d:CompanyRegionCode")
	private String dCompanyRegionCode;
	
}
