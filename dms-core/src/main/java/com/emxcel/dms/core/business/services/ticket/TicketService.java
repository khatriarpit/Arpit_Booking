package com.emxcel.dms.core.business.services.ticket;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.ticket.Ticket;
import com.emxcel.dms.core.model.ticket.TicketDepartmentType;
import com.emxcel.dms.core.model.user.User;
/**
 * Created By: Pratik Soni.
 * Used For: Implement Ticket Related Services
 * @author ADMIN
 *
 */
public interface TicketService extends DMSEntityService<Long, Ticket> {

    /**
     * Created By: Pratik Soni.
     * Used For: Get all the tickets of a tenant
     * @param tenantId **id assigned to tenant**
     * @return *list of tickets assigned to particular tenant*
     */
    List<Ticket> getTickets(Long tenantId);

    /**
     * Created By: Pratik Soni.
     * Used For: get the ticket details from ticket id
     * @param id **ticket id**
     * @return *ticket*
     */
    Ticket getTicketById(Long id);

    /**
     * Created By: Pratik Soni.
     * Used For: List all the tickets
     * @return *list of all ticket*
     */
    List<Ticket> getAllTickets();

    /**
     * Created By: Pratik Soni.
     * Used For: get the ticket details assigned to particular user
     * @param user **user details**
     * @return *list of tickets assigned to user*
     */
    List<Ticket> getTicketsByRole(User user);

    /**
     * Created By: Pratik Soni.
     * Used For: get the all closed tickets
     * @return *list of all closed tickets*
     */
    List<Ticket> getClosedTickets();

    List<Ticket> getTicketByDepartmentType(TicketDepartmentType ticketDepartmentType);

    int getNumberOfTicketsByTenantId(Long tanentID);
}
