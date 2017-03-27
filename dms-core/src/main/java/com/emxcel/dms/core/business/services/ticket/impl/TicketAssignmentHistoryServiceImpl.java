package com.emxcel.dms.core.business.services.ticket.impl;

import com.emxcel.dms.core.business.repositories.ticket.TicketAssignmentHistoryRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.ticket.TicketAssignmentHistoryService;
import com.emxcel.dms.core.model.ticket.TicketAssignmentHistory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by: Pratik Soni.
 * Used For: Implementing Ticket Assignment History
 */
@Service("ticketAssignmentHistory")
public class TicketAssignmentHistoryServiceImpl extends DMSEntityServiceImpl<Long, TicketAssignmentHistory> implements TicketAssignmentHistoryService {

    /**
     * Created By: Pratik Soni.
     * Used For: Binds TicketAssignment History
     */
    @Inject
    private TicketAssignmentHistoryRepository ticketAssignmentHistoryRepository;

    /**
     * Created by: Pratik Soni.
     * Used For: Initializing
     * @param ticketAssignmentHistoryRepository *assignment*
     */
    @Inject
    public TicketAssignmentHistoryServiceImpl(final TicketAssignmentHistoryRepository ticketAssignmentHistoryRepository) {
        super(ticketAssignmentHistoryRepository);
        this.ticketAssignmentHistoryRepository = ticketAssignmentHistoryRepository;
    }
    /**
     * Created by: Pratik Soni.
     * Used For: listing Ticket Assignment History
     * @return *list of Ticket Assignment History*
     */
    @Override
    public List<TicketAssignmentHistory> getTicketAssignmentHistory() {
        return ticketAssignmentHistoryRepository.getTicketAssignmentHistory();
    }
}
