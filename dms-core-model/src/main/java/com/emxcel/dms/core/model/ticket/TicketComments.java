package com.emxcel.dms.core.model.ticket;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.user.User;

import javax.persistence.*;

/**
 * Created by ADMIN on 02-03-2017.
 */

@Entity
@Table(name = "ticket_comments", schema = SchemaConstant.DMS_SCHEMA)
public class TicketComments extends DMSEntity<Long, TicketComments> implements java.io.Serializable {

    private static final long serialVersionUID = 1647630088457387868L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TICKET_COMMENTS_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;



    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "comment")
    private String comment;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
