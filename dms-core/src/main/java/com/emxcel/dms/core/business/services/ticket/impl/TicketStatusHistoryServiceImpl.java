package com.emxcel.dms.core.business.services.ticket.impl;

import com.emxcel.dms.core.business.repositories.ticket.TicketRepository;
import com.emxcel.dms.core.business.repositories.ticket.TicketStatusHistoryRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.ticket.TicketService;
import com.emxcel.dms.core.business.services.ticket.TicketStatusHistoryService;
import com.emxcel.dms.core.model.ticket.Ticket;
import com.emxcel.dms.core.model.ticket.TicketStatusHistory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by ADMIN on 16-03-2017.
 */
@Service("ticketStatusHistoryService")
public class TicketStatusHistoryServiceImpl extends DMSEntityServiceImpl<Long, TicketStatusHistory> implements TicketStatusHistoryService {

    private TicketStatusHistoryRepository ticketStatusHistoryRepository;
    @Inject
    public TicketStatusHistoryServiceImpl(final TicketStatusHistoryRepository ticketStatusHistoryRepository) {
        super(ticketStatusHistoryRepository);
        this.ticketStatusHistoryRepository = ticketStatusHistoryRepository;
    }

    @Override
    public List<TicketStatusHistory> getTicketStatusHistoryByTicketId(Long ticketId) {
        return ticketStatusHistoryRepository.getTicketStatusHistoryByTicketId(ticketId);
    }

    @Override
    public TicketStatusHistory getClosedByUserFromTicketId(Long ticketId){
        return ticketStatusHistoryRepository.getClosedByUser(ticketId);
    }

    @Override
    public List<TicketStatusHistory> TicketStatusFromUserId(Long id){
        return ticketStatusHistoryRepository.TicketStatusFromUserId(id);
    }

    @Override
    public List<TicketStatusHistory> TicketStatusFromTicketId(Long id){
        return ticketStatusHistoryRepository.TicketStatusFromTicketId(id);
    }
}
