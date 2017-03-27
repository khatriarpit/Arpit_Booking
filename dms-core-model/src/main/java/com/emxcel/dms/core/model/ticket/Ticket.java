package com.emxcel.dms.core.model.ticket;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.user.User;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "ticket", schema = SchemaConstant.DMS_SCHEMA)

public class Ticket extends DMSEntity<Long, Ticket> implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1647630088457387868L;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TICKET_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private TicketDepartmentType departmentType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_to_user")
    private User assignedUser;

    @Column(name = "description")
    private String description;

    @Column(name = "topic_name")
    private String topicName;

    @Column(name = "ticket_id")
    private String ticketId;

    @Column(name = "status")
    private int status = 1;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ticket")
    private List<TicketComments> ticketComments = new ArrayList<TicketComments>();

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "company_name")
    private String companyName;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "ticket_priority_id")
    private TicketPriority ticketPriority;

    public TicketPriority getTicketPriority() {
        return ticketPriority;
    }

    public void setTicketPriority(TicketPriority ticketPriority) {
        this.ticketPriority = ticketPriority;
    }
    /*
    public void setTicketComments(Set<TicketComments> ticketComments) {
        this.ticketComments = ticketComments;
    }*/

    public TicketDepartmentType getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(TicketDepartmentType departmentType) {
        this.departmentType = departmentType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public void setTicketDexcription(String description) {
        this.description = description;
    }

    public String getTicketDescription() {
        return this.description;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketId() {
        return this.ticketId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TicketComments> getTicketComments() {
        return this.ticketComments;
    }

    public void setTicketComments(List<TicketComments> ticketComments){
        this.ticketComments = ticketComments;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedToUser) {
        this.assignedUser = assignedToUser;
    }
}