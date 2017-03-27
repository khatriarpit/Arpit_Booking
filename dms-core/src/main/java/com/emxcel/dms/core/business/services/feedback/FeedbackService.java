package com.emxcel.dms.core.business.services.feedback;

import java.util.List;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.feedback.Feedback;

public interface FeedbackService extends DMSEntityService<Long, Feedback> {

	List<Feedback> getFeedbackList(Long tanentID);

	Feedback getFeedbackByTripID(String tripID,Long tanentID);

	Feedback getFeedbackByTripID(String tripID);

	List<Feedback> carFeedBackByTanent(Long tanentID);

	List<Feedback> checkFeedbackByCarId(Long id,Long tenantID,String fromDate,String toDate);

	List<Feedback> checkFeedbackByDriverId(Long id,Long tanentID,String fromDate,String toDate);

	List<Feedback> getFeedbackList(String startDate, String endDate, Car car, Driver driver);

	void setfeedback(Object object);

	void setClientModelInFeedback(Feedback feedback);

	

	

	
}
