package com.vkc.forms.ARCreditMemoService_GST_DebitNoteService;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
@Repository
public interface ArFormRepository extends JpaRepository<ZFormEntity, Integer> {

	boolean existsByDocumentNoAndFiscalYearAndCompanyCode(String documentNo, String fiscalYear, String companyCode);
 
	
	
}
