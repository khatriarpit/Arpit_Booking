package com.emxcel.dms.core.business.services.ticket;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.ticket.TicketAssignmentHistory;
import com.emxcel.dms.core.model.ticket.TicketStatusHistory;
import java.util.List;
/**
 * Created by ADMIN on 16-03-2017.
 */
public interface TicketStatusHistoryService extends DMSEntityService<Long, TicketStatusHistory>{

    List<TicketStatusHistory> getTicketStatusHistoryByTicketId(Long ticketId);

    TicketStatusHistory getClosedByUserFromTicketId(Long ticketId);

    List<TicketStatusHistory> TicketStatusFromUserId(Long id);

    List<TicketStatusHistory> TicketStatusFromTicketId(Long id);
}
