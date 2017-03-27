package com.emxcel.dms.portal.controller;

import com.emxcel.dms.core.business.repositories.feedback.FeedbackRepository;
import com.emxcel.dms.core.business.repositories.trip.TripStatusRepository;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.feedback.Feedback;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.Application;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by root on 1/9/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FeedbackControllerTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private TripStatusRepository tripStatusRepository;

    @Test
    public void enterFeedback() throws Exception {
        ModelAndView model = new ModelAndView();
        if (CommonUtil.checkUserLogin()) {
            model.setViewName(ViewConstants.ENTER_FEEDBACK);
        } else {
            model.setViewName("redirect:" + UrlConstants.LOGIN);
        }
    }

    /*@Test
    public void feedback() throws Exception {
        ModelAndView model = new ModelAndView();
        if (CommonUtil.checkUserLogin()) {
            User user = new User();
            Feedback feedback = new Feedback();
            if (tripStatusRepository.getTripStatusByTripID("DK10000", "end", user.getTanentID()) == null) {
            }

            feedback = feedbackRepository.getFeedbackByTripID("DK10000", user.getTanentID());
            if (feedback == null) {
                feedback = new Feedback();
            }
            feedback.setClientDetail(clientRepository.getClientdetailByTripId("DK10000"));
            model.addObject("feedbackDetail", feedback);
        } else {
        }
    }*/

    @Test
    public void feedbackSave() throws Exception {
        User user = new User();
        if (CommonUtil.checkUserLogin()) {
            Feedback feedback = new Feedback();
            if (feedback != null) {
                if (feedback.getId() != null) {
                    feedback.setId(feedback.getId());
                }
                feedback.setDrivingRating(feedback.getDrivingRating());
                feedback.setDriverBehaviourRating(feedback.getDriverBehaviourRating());
                feedback.setDriverTestingRating(feedback.getDriverTestingRating());
                feedback.setCarConditionRating(feedback.getCarConditionRating());
                feedback.setOverallServiceRating(feedback.getOverallServiceRating());
                float averageRating = 0.0f;
                averageRating = ((feedback.getDrivingRating() + feedback.getDriverBehaviourRating()
                        + feedback.getDriverTestingRating() + feedback.getCarConditionRating()
                        + feedback.getOverallServiceRating()) / 5);
                feedback.setAverageRating(averageRating);
                feedback.setRemark(feedback.getRemark());
                feedback.setTripID(feedback.getTripID());
                feedback.setTanentID(user.getTanentID());
            }
            feedbackRepository.save(feedback);
        } else {
        }
    }

	/*
	 * @Test public void enterFeedback1() throws Exception { if
	 * (CommonUtil.checkUserLogin()) { User user = new User(); Client
	 * clientDetail = clientRepository.getOnlyClientdetailByTripId("DK10000",
	 * user.getTanentID()); Feedback feedback =
	 * feedbackRepository.getFeedbackByTripID("DK10000", user.getTanentID());
	 * TripStatus tripStatus =
	 * tripStatusRepository.getTripStatusByTripID("DK10000" ,"end",
	 * user.getTanentID()); if (clientDetail == null) { } if (tripStatus ==
	 * null) { } if (feedback != null) { } else { } } else { } }
	 */

}