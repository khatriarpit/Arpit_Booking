package com.emxcel.dms.core.business.repositories.ticket;

/**
 * Created by ADMIN on 03-03-2017.
 */

import org.springframework.data.jpa.repository.JpaRepository;
import com.emxcel.dms.core.model.ticket.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketCommentsRepository extends JpaRepository<TicketComments,Long>{

    @Query("SELECT tc FROM TicketComments tc WHERE tc.ticket.id = :id")
    public List<TicketComments> TicketCommentsFromTicketId(@Param("id")Long id);
}

