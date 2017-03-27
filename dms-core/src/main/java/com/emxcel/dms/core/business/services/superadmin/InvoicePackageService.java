package com.emxcel.dms.core.business.services.superadmin;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.car.CarType;
import com.emxcel.dms.core.model.superadmin.InvoicePackage;

/**
 * @author Emxcel Solutions
 *
 */
public interface InvoicePackageService extends DMSEntityService<Long, InvoicePackage> {
	
	
	/**
	 *  Created By-Johnson Chunara Date-26-01-2017.
	 * Used For-getInvoiceTypeDBStatus
	 * @param categoryname **categoryname**
	 * @param id **id**
	 * @return List 
	 */
	List<InvoicePackage> getInvoiceTypeDBStatus(String categoryname, Long id);
	
	
	
	

}
