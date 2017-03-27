package com.emxcel.dms.core.business.services.ticket.impl;

import com.emxcel.dms.core.business.repositories.ticket.TicketCommentsRepository;
import com.emxcel.dms.core.business.repositories.ticket.TicketRepository;
import com.emxcel.dms.core.business.repositories.user.UserRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.ticket.TicketCommentsService;
import com.emxcel.dms.core.model.ticket.TicketComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Pratik Soni.
 * Used For: Implementing Ticket Comment Service
 */
@Service("ticketCommentsService")
public class TicketCommentsServiceImpl extends DMSEntityServiceImpl<Long, TicketComments> implements TicketCommentsService {

    /** Created By: Pratik Soni.
     * Used For: Binds user repository
     */
    @Inject
    private UserRepository userRepository;

    /**Created By: Pratik Soni.
     * Used For: Binds Ticket Comment repository
     */
    @Inject
    private TicketCommentsRepository ticketCommentsRepository;

    /**Created By: Pratik Soni.
     * Used For: Initializing ticket comment repository
     * @param ticketCmtsRepository **ticket comment repository**
     */
    @Inject
    public TicketCommentsServiceImpl(final TicketCommentsRepository ticketCmtsRepository) {
        super(ticketCmtsRepository);
        this.ticketCommentsRepository = ticketCmtsRepository;
    }

    @Override
    public List<TicketComments> TicketCommentsFromTicketId(Long id){
        return ticketCommentsRepository.TicketCommentsFromTicketId(id);
    }

}
