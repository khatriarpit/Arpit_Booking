package com.emxcel.dms.core.business.services.ticket;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.ticket.TicketAssignmentHistory;

import java.util.List;

/**
 * Created by: Pratik Soni.
 * Used For: Managing Ticket Assignment History
 */
public interface TicketAssignmentHistoryService extends DMSEntityService<Long, TicketAssignmentHistory> {

    /**
     * Created by: Pratik Soni.
     * Used For: listing Ticket Assignment History
     * @return *list of Ticket Assignment History*
     */
    List<TicketAssignmentHistory> getTicketAssignmentHistory();
}
