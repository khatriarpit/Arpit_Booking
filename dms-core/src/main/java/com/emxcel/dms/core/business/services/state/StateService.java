package com.emxcel.dms.core.business.services.state;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.state.State;

/**
 * @author emxcelsolution
 *
 */
public interface StateService extends DMSEntityService<Long, State> {
	
	/**
	 * @param countryId
	 * @return List
	 */
	List<State> getstate(Long countryId);

}
