package com.emxcel.dms.core.business.repositories.invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.emxcel.dms.core.model.invoice.TermsConditions;

import java.util.List;

/**
 * @author emxcelsolution
 *
 */
@Transactional
public interface TermsConditionsRepository extends JpaRepository<TermsConditions, Long> {

    @Query("select tc from TermsConditions tc where tc.invoice_static_id=:invoiceID")
	public List<TermsConditions> getListOfTermConditionsByTenantID(@Param("invoiceID") Long invoiceID);
}