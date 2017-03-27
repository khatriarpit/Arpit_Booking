package com.emxcel.dms.core.business.services.invoice;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.invoice.Invoice_Static;


/**
 * @author emxcelsolution
 *
 */
public interface Invoice_StaticService extends DMSEntityService<Long, Invoice_Static>  {

	Invoice_Static getByTanentID(Long tanentID);

}