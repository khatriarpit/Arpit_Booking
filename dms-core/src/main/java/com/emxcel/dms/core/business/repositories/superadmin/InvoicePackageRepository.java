package com.emxcel.dms.core.business.repositories.superadmin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.superadmin.InvoicePackage;

public interface InvoicePackageRepository extends JpaRepository<InvoicePackage, Long>{
	/**
	 *  Created By Johnson Chunara Used For-getInvoiceTypeDBStatus
	 * Date-26-01-2017
	 * @param categoryname **Category Name**
	 * @param id **Invoice Package Id**
	 * @return List
	 */
	@Query("select ip from InvoicePackage ip where ip.categoryname=? and ip.id!=?")
	List<InvoicePackage> getInvoiceTypeDBStatus(String categoryname, Long id);

}
