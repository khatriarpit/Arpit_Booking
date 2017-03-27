package com.emxcel.dms.core.business.services.ticket;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.ticket.TicketPriority;

import java.util.List;

/**
 * Created by ADMIN on 20-03-2017.
 */
public interface TicketPriorityService extends DMSEntityService<Long, TicketPriority>{

    TicketPriority getTicketPriorityObj(String priority);
    public List<TicketPriority> getTicketPriority();
}
