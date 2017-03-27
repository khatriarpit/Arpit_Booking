package com.emxcel.dms.core.business.services.invoice;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.invoice.TermsConditions;

import java.util.List;


/**
 * @author emxcelsolution
 *
 */
public interface TermsConditionsService extends DMSEntityService<Long, TermsConditions>  {

    List<TermsConditions> getListOfTermConditionsByTenantID(Long invoiceId);
	
}