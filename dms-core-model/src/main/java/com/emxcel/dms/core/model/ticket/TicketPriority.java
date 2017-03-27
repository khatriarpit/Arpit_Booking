package com.emxcel.dms.core.model.ticket;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;

import javax.persistence.*;
import java.util.*;
/**
 * Created by Pratik on 20-03-2017.
 */
@Entity
@Table(name = "ticket_priority", schema = SchemaConstant.DMS_SCHEMA)

public class TicketPriority extends DMSEntity<Long, TicketPriority> implements java.io.Serializable {

    private static final long serialVersionUID = 1647630088457387868L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TICKET_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column(name = "ticket_priority")
    private String ticketPriority;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketPriority() {
        return ticketPriority;
    }

    public void setTicketPriority(String ticketPriority) {
        this.ticketPriority = ticketPriority;
    }
}



