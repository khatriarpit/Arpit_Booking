package com.emxcel.dms.core.model.ticket;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.user.User;

import javax.persistence.*;

/**
 * Created by ADMIN on 04-03-2017.
 */
@Entity()
@Table(name = "ticket_assignment_history" , schema = SchemaConstant.DMS_SCHEMA)
public class TicketAssignmentHistory  extends DMSEntity<Long, TicketAssignmentHistory> implements java.io.Serializable {


    private static final long serialVersionUID = 1647630088457387868L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TICKET_ASSIGNMENT_HISTORY_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_by_user")
    private User assignedByuser;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_to_user")
    private User assignedToUser;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getAssignedByuser() {
        return assignedByuser;
    }

    public void setAssignedByuser(User assignedByuser) {
        this.assignedByuser = assignedByuser;
    }

    public User getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(User assignedToUser) {
        this.assignedToUser = assignedToUser;
    }
}
