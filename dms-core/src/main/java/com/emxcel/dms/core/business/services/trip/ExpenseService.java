package com.emxcel.dms.core.business.services.trip;

import java.sql.Timestamp;
import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.trip.Expense;

/**
 * @author emxcelsolution
 *
 */
public interface ExpenseService extends DMSEntityService<Long, Expense>  {

    /**
     * @return
     */
    List<Expense> getTotalCharges(Timestamp date);

}
