package com.vkc.forms.ARCreditMemoService_GST_DebitNoteService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ARSubReportGSTRepository extends JpaRepository<ARSubReportEntity, Integer>{

	boolean existsByDocumentNoAndFiscalYearAndCompanyCode(String documentNo, String fiscalYear, String companyCode);

}
