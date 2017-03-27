package com.emxcel.dms.core.business.services.ticket.impl;

import com.emxcel.dms.core.business.repositories.ticket.TicketPriorityRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.ticket.TicketPriorityService;
import com.emxcel.dms.core.model.ticket.TicketPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by ADMIN on 20-03-2017.
 */
@Service("ticketPriorityService")
public class TicketPriorityServiceImpl extends DMSEntityServiceImpl<Long,TicketPriority> implements TicketPriorityService {

    @Inject
    private TicketPriorityRepository ticketPriorityRepository;

    @Inject
    public TicketPriorityServiceImpl(final TicketPriorityRepository ticketPriorityRepository) {
        super(ticketPriorityRepository);
        this.ticketPriorityRepository = ticketPriorityRepository;
    }

    @Override
    public TicketPriority getTicketPriorityObj(String priority) {
        return ticketPriorityRepository.getTicketPriorityObj(priority);
    }

    @Override
    public List<TicketPriority> getTicketPriority() {
        return ticketPriorityRepository.getTicketPriority();
    }


}
