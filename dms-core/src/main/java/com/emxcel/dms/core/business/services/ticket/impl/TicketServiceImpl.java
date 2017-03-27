package com.emxcel.dms.core.business.services.ticket.impl;

import com.emxcel.dms.core.business.repositories.ticket.TicketDepartmentTypeRepository;
import com.emxcel.dms.core.business.repositories.ticket.TicketRepository;
import com.emxcel.dms.core.business.repositories.user.UserRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.business.services.ticket.TicketService;
import com.emxcel.dms.core.model.superadmin.Tenant;
import com.emxcel.dms.core.model.ticket.Ticket;
import com.emxcel.dms.core.model.ticket.TicketDepartmentType;
import com.emxcel.dms.core.model.user.User;

import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**Created By: Pratik Soni.
 * Used For: Ticket Service
 */
@Service("ticketService")
public class TicketServiceImpl extends DMSEntityServiceImpl<Long, Ticket> implements TicketService {

    /**Created By: Pratik Soni.
     * Used For: Binds ticket department type repository
     */
    @Inject
    private TicketDepartmentTypeRepository  ticketDepartmentTypeRepository;

    /**Created By: Pratik Soni.
     * Used For: Binds Tenant repository
     */
    @Inject
    private TenantService tenantService;
    /**Created By: Pratik Soni.
     * Used For: Binds user repository
     */
    @Inject
    private UserRepository userRepository;

    /**Created By: Pratik Soni.
     * Used For: Binds ticket repository
     */
    private TicketRepository ticketRepository;

    /**Created By: Pratik Soni.
     * Used For: Initializing ticket repository
     * @param ticketRepository **ticket repository**
     */
    @Inject
    public TicketServiceImpl(final TicketRepository ticketRepository) {
        super(ticketRepository);
        this.ticketRepository = ticketRepository;
    }

    /**Created By: Pratik Soni.
     * Used For: Get all the tickets of user
     * @param userId **user id**
     */
    @Override
    public List<Ticket> getTickets(final Long userId) {
        List<Ticket> tickets = ticketRepository.getTickets(userId);
        for (Ticket ticket:tickets)
        {
            System.out.println(ticket.getId());
            User user = ticket.getUser();
            Tenant tenant = tenantService.getById(user.getTanentID());
            System.out.println("TenantID::::::::"+user.getTanentID());
            //System.out.println("TenantID:::::::"+user.getTanentID());
            String companyName = tenant.getCompanyname();
            ticket.setCompanyName(companyName);

        }
        return tickets;
    }
    /**Created By: Pratik Soni.
     * Used For: Get the tickets from the id
     * @param id **ticket id**
     */
    @Override
    public Ticket getTicketById(final Long id) {
        return ticketRepository.getTicketById(id);
    }
    /**Created By: Pratik Soni.
     * Used For: Get All the tickets
     */
    @Override
    public List<Ticket> getAllTickets() {
        List<Ticket> tickets = ticketRepository.getALLTickets();

        for (Ticket ticket:tickets)
        {
            User user = ticket.getUser();
            Tenant tenant = tenantService.getById(user.getTanentID());
            System.out.println("TenantID::::::::"+user.getTanentID());
            //System.out.println("TenantID:::::::"+user.getTanentID());
            String companyName = tenant.getCompanyname();
            ticket.setCompanyName(companyName);

        }
        return tickets;
     }
    /**Created By: Pratik Soni.
     * Used For: Get Tickets associated with user
     * @param user **user**
     */
    @Override
    public List<Ticket> getTicketsByRole(final User user) {
        return ticketRepository.getTicketsByRole(user);
    }
    /**Created By: Pratik Soni.
     * Used For: Get All the closed tickets
     */
    @Override
    public List<Ticket> getClosedTickets() {
        return ticketRepository.getClosedTickets();
    }
    /**Created By: Pratik Soni.
     * Used For: Get the Ticket By Department Type
     */
    @Override
    public List<Ticket> getTicketByDepartmentType(final TicketDepartmentType ticketDepartmentType) {
        return ticketRepository.getTicketByDepartmentType(ticketDepartmentType);
    }

    @Override
    public int getNumberOfTicketsByTenantId(Long tanentID) {
        return ticketRepository.getNumberOfTicketsByTenantId(tanentID);
    }
}
