package com.emxcel.dms.core.model.ticket;

/**
 * Created by ADMIN on 16-03-2017.
 */
import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.user.User;

import javax.persistence.*;

@Entity()
@Table(name = "ticket_status_history" , schema = SchemaConstant.DMS_SCHEMA)
public class TicketStatusHistory extends DMSEntity<Long, TicketStatusHistory> implements java.io.Serializable {

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
    @JoinColumn(name = "assigned_to_user")
    private User assignedToUser;

    @Column(name = "from_status")
    private int fromStatus;

    @Column(name = "to_status")
    private int toStatus;

    @Override
    public Long getId() {
        return id;
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

    public User getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(User assignedToUser) {
        this.assignedToUser = assignedToUser;
    }

    public int getFromStatus() {
        return fromStatus;
    }

    public void setFromStatus(int fromStatus) {
        this.fromStatus = fromStatus;
    }

    public int getToStatus() {
        return toStatus;
    }

    public void setToStatus(int toStatus) {
        this.toStatus = toStatus;
    }
}
