package com.emxcel.dms.core.business.services.ticket.impl;

import com.emxcel.dms.core.business.services.ticket.TicketDepartmentService;
import com.emxcel.dms.core.model.ticket.TicketDepartmentType;
import javax.inject.Inject;

import org.springframework.stereotype.Service;


import com.emxcel.dms.core.business.repositories.ticket.TicketDepartmentTypeRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;


/**Created By: Pratik Soni.
 * Used For: Implementing Ticket Department Services
 *
 */
@Service("ticketDepartmentService")
public class TicketDepartmentServiceImpl extends DMSEntityServiceImpl<Long, TicketDepartmentType> implements TicketDepartmentService {

    /**Created By: Pratik Soni.
     * Used For: Binds ticketDepartmenttype Service
     */
    @Inject
    private TicketDepartmentTypeRepository ticketDepartmentTypeRepository;

    /**Created By: Pratik Soni.
     * Used For: initializing ticketDepartmenttype Service
     * @param ticketDepartmentTypeRepository **ticketDepartmentTypeRepository**
     */
    @Inject
	public TicketDepartmentServiceImpl(final TicketDepartmentTypeRepository ticketDepartmentTypeRepository) {
		super(ticketDepartmentTypeRepository);
		this.ticketDepartmentTypeRepository = ticketDepartmentTypeRepository;
	}
}
