package com.vkc.forms.utils;

import org.springframework.context.annotation.Description;

public class APIConstants {

	

	@Description(value = "AP Invoice Journal Controller")
	public static class APInvoiceJournalMaster {
		public static final String APInvoiceJournal = "https://my409401-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_ZFIFORMSHEADERKR_CDS/YY1_ZFIFORMSHEADERKR";
		public static final String APInvoiceJournalLineItemOne = "https://my409401-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_ZFIFORMSLINEDETKRTDS_CDS/YY1_ZFIFORMSLINEDETKRTDS";
		public static final String APInvoiceJournalLineItemTwo = "https://my409401-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_ZFIFORMSLINEDETAILSKR_CDS/YY1_ZFIFORMSLINEDETAILSKR";
		public static final String APInvoiceUserName = "YY1_VKCFORMS";
		public static final String APInvoicePassword = "UEBdLbxGEtWlqKz2jZvisnnlXhMWfaWA~CWthMAh";
	}

	@Description(value = "Journal Entry Controller")
	public static class JournalEntry {
		public static final String JounalEntry = "https://my409401-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_ZFIFORMSHEADERDR1_CDS/YY1_ZFIFORMSHEADERDR1";
		public static final String JounalEntryLine = "https://my409401-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_ZFIFORMSLINEDETAILSDR1_CDS/YY1_ZFIFORMSLINEDETAILSDR1";
		public static final String JournalEntryUserName = "YY1_VKCFORMS";
		public static final String JournalEntryPassword = "hqxzFu5EGTbMhHlyErXvUESQWdf$vGlFUTTteajH";

	}

	@Description(value = "Incoming Payment Bank")
	public static class IncomingPaymentBank {
		public static final String IncomingPaymentBankHeader = "https://my409401-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_OUTGOINGEXPENSEHEADER_CDS/YY1_OUTGOINGEXPENSEHEADER";
		public static final String IncomingPaymentBankLine = "https://my409401-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_OUTGOINGEXPENSEBANKENT_CDS/YY1_OUTGOINGEXPENSEBANKENT";
	}
	
	@Description(value="AP Credit Memo Item GST Debit Note Item")
	public static class APCreditMemoIteGstAPI{
		public static final String APCreditMemoItemGst = "https://my409401-api.s4hana.cloud.sap/sap/opu/odata/sap/YY1_MIROFORMS_CDS/YY1_MIROFORMS";
		
		
	}
	
	

}
