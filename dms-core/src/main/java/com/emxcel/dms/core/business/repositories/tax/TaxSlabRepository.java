package com.emxcel.dms.core.business.repositories.tax;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.tax.TaxSlab;

public interface TaxSlabRepository extends JpaRepository<TaxSlab, Long>, TaxSlabRipositoryCustom {
	
	@Query("select t from TaxSlab t where t.taxcategoryid=?")
	List<TaxSlab> getlistoftax(Long taxId);
	
	@Query("select ts from TaxSlab ts where ts.invoicecatid=?")
	List<TaxSlab> getTaxSlabById(Long id);
	
	
}