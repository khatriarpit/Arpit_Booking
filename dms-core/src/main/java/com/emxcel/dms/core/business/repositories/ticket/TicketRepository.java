package com.emxcel.dms.core.business.repositories.ticket;

import java.util.List;

import com.emxcel.dms.core.model.ticket.TicketDepartmentType;
import com.emxcel.dms.core.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.emxcel.dms.core.model.ticket.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
	//USER id IN PLACE OF TANENT ID\
	@Query("SELECT t from Ticket t where t.user.id	 = :id order by t.createdDate desc")
	public List<Ticket> getTickets(@Param("id") Long id);

	@Query("SELECT t from Ticket t where t.id = :id")
	public Ticket getTicketById(@Param("id") Long id);

	@Query("SELECT t from Ticket t order by t.status desc, t.createdDate desc")
	public List<Ticket> getALLTickets();

	@Query("SELECT t from Ticket t where t.assignedUser = :user")
	public List<Ticket> getTicketsByRole(@Param("user") User user);

	@Query("SELECT t from Ticket t where t.status = 0")
	public List<Ticket> getClosedTickets();

	@Query("SELECT t from Ticket t where t.departmentType = :departmentType")
	public List<Ticket>getTicketByDepartmentType(@Param("departmentType") TicketDepartmentType departmentType);

	@Query("SELECT count(t) from Ticket t where t.tanentID = :tanentID")
	public int getNumberOfTicketsByTenantId(@Param("tanentID") Long tanentID);

}
