package com.emxcel.dms.core.business.services.ticket;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.ticket.TicketComments;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by: Pratik Soni.
 * USed For: Implement Ticket Commenting functionalities
 */
public interface TicketCommentsService extends DMSEntityService<Long, TicketComments> {

    public List<TicketComments> TicketCommentsFromTicketId(@Param("id")Long id);

}
