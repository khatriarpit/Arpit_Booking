package com.emxcel.dms.core.business.repositories.ticket;

import com.emxcel.dms.core.model.ticket.Ticket;
import com.emxcel.dms.core.model.ticket.TicketStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by ADMIN on 16-03-2017.
 */
public interface TicketStatusHistoryRepository extends JpaRepository<TicketStatusHistory, Long> {

    @Query("SELECT t from TicketStatusHistory t where t.id = :id ")
    public List<TicketStatusHistory> getTicketStatusHistoryByTicketId(@Param("id") Long id);

    @Query("SELECT tsh from TicketStatusHistory tsh where tsh.ticket.id = :id and tsh.toStatus = 0")
    public TicketStatusHistory getClosedByUser(@Param("id") Long id);

    @Query("SELECT tsh FROM TicketStatusHistory tsh WHERE tsh.createdDate in  (SELECT Max(tsh.createdDate) FROM TicketStatusHistory tsh WHERE tsh.assignedToUser.id = :id GROUP BY tsh.ticket)")
    public List<TicketStatusHistory> TicketStatusFromUserId(@Param("id") Long id);

    @Query("SELECT tsh FROM TicketStatusHistory tsh WHERE tsh.ticket.id = :id")
    public List<TicketStatusHistory> TicketStatusFromTicketId(@Param("id") Long id);
}
