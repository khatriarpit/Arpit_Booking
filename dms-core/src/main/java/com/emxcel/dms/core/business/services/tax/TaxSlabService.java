package com.emxcel.dms.core.business.services.tax;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.tax.TaxSlab;

/**
 * @author swami
 *
 */
public interface TaxSlabService extends DMSEntityService<Long, TaxSlab>{
	
	/**created by : Jimit Patel Date: 26-1-2017.
	 * @param taxId **taxId**
	 * @return List<TaxSlab>
	 */
	List<TaxSlab> getlistoftax(Long taxId);

	/**created by : Jimit Patel Date: 26-1-2017.
	 * @param id **id**
	 * @return List<TaxSlab>
	 */
	List<TaxSlab> getTaxSlabById(Long id);
}
