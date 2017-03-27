/*
package com.emxcel.dms.portal.controller;

import static org.junit.Assert.fail;

import java.util.List;

import com.emxcel.dms.portal.Application;
import com.emxcel.dms.portal.constants.UrlConstants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TicketControllerTest {

    @Inject
    private TicketService ticketService;

    @Inject
    private TicketDepartmentService ticketDepartmentService;

    @Inject
    private UserService userService;

    @Inject
    private TicketCommentsService ticketCommentsService;

    @Inject
    private TicketAssignmentHistoryService ticketAssignmentHistoryService;

    @Test
    private void newTicket (){

            //Get the tenantID
            User user1 = userService.getById(-1);
            ticketModel.setTicketId(generateTicketId());
            ticketModel.setTanentID(user1.getTanentID());


            ticketModel.setUser(user1);
            ticketModel.setDepartmentType(ticketDepartmentService.getById(ticketModel.getDepartmentType().getId()));

            //By default assign ticket to ticket manager ----roleId = 6

            User ticketManager = userService.getUserByRoleId(Long.valueOf("6"));
            ticketModel.setAssignedUser(ticketManager);

            ticketService.save(ticketModel);

    }

}*/
