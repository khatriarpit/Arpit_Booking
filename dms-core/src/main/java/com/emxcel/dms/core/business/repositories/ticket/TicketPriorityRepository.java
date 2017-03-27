package com.emxcel.dms.core.business.repositories.ticket;

import com.emxcel.dms.core.model.ticket.TicketAssignmentHistory;
import com.emxcel.dms.core.model.ticket.TicketPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ADMIN on 20-03-2017.
 */
public interface TicketPriorityRepository extends JpaRepository<TicketPriority, Long> {

    @Query("SELECT tp from TicketPriority tp where tp.ticketPriority = :priority")
    public TicketPriority getTicketPriorityObj(@Param("priority") String priority);

    @Query("SELECT tp from TicketPriority tp")
    public List<TicketPriority> getTicketPriority();
}
