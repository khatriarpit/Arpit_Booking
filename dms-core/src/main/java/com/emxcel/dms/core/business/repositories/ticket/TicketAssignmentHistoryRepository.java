package com.emxcel.dms.core.business.repositories.ticket;

/**
 * Created by ADMIN on 06-03-2017.
 */
import com.emxcel.dms.core.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.emxcel.dms.core.model.ticket.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketAssignmentHistoryRepository extends JpaRepository<TicketAssignmentHistory,Long>{

    @Query("SELECT t from TicketAssignmentHistory t")
    public List<TicketAssignmentHistory> getTicketAssignmentHistory();
}