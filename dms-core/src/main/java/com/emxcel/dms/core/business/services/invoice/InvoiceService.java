package com.emxcel.dms.core.business.services.invoice;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.invoice.Invoice;


/**
 * @author emxcelsolution
 *
 */
public interface InvoiceService extends DMSEntityService<Long, Invoice>  {

	
	/**
	 * @param tripid
	 * @return
	 */
	Invoice getTripByTripId(String tripid);

	/**
	 * @return
	 */
	Invoice getlastInvoiceNo();

	Invoice getByTanentID(Long tanentID);
}