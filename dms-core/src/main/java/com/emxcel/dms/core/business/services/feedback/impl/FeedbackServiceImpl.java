package com.emxcel.dms.core.business.services.feedback.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.driver.Driver;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.repositories.client.ClientModelRepository;
import com.emxcel.dms.core.business.repositories.feedback.FeedbackRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.feedback.FeedbackService;
import com.emxcel.dms.core.model.feedback.Feedback;
import com.emxcel.dms.core.model.user.User;

@Service("feedbackService")
public class FeedbackServiceImpl extends DMSEntityServiceImpl<Long, Feedback> implements FeedbackService {

	@PersistenceContext
	private EntityManager manager;

	/**
	 * feedbackRepository.
	 */
	private FeedbackRepository feedbackRepository;
	/**
	 * clientModelRepository.
	 */
	@Inject
	private ClientModelRepository clientModelRepository;

	@Inject
	public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
		super(feedbackRepository);
		this.feedbackRepository = feedbackRepository;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.emxcel.dms.core.business.services.feedback.FeedbackService#
	 * getFeedbackList()
	 */
	/*
	 * @Override public final List<Feedback> getFeedbackList() { return
	 * feedbackRepository.getFeedbackList(); }
	 */

	@Override
	public List<Feedback> getFeedbackList(Long tanentID) {
		// TODO Auto-generated method stub
		return feedbackRepository.driverFeedBackByTanent(tanentID);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.emxcel.dms.core.business.services.feedback.FeedbackService#
	 * getFeedbackByTripID(java.lang.String)
	 */
	@Override
	public Feedback getFeedbackByTripID(String tripID, Long tanentID) {
		return feedbackRepository.getFeedbackByTripID(tripID, tanentID);
	}

	@Override
	public Feedback getFeedbackByTripID(String tripID) {
		return feedbackRepository.getFeedbackByTripID(tripID);
	}

	@Override
	public List<Feedback> carFeedBackByTanent(Long tanentID) {
		return feedbackRepository.carFeedBackByTanent(tanentID);
	}

	@Override
	public List<Feedback> checkFeedbackByCarId(Long id, Long tanentID, String fromDate, String toDate) {
		return manager.createNamedQuery("checkFeedbackByCarId", Feedback.class).setParameter("id", id)
				.setParameter("tanentID", tanentID).setParameter("fromDate", fromDate).setParameter("toDate", toDate)
				.getResultList();
	}

	@Override
	public List<Feedback> checkFeedbackByDriverId(Long id, Long tanentID, String fromDate, String toDate) {
		return manager.createNamedQuery("checkFeedbackByDriverId", Feedback.class).setParameter("id", id)
				.setParameter("tanentID", tanentID).setParameter("fromDate", fromDate).setParameter("toDate", toDate)
				.getResultList();
	}

	@Override
	public List<Feedback> getFeedbackList(String startDate, String endDate, Car car, Driver driver) {
		if (car != null) {
			return feedbackRepository.getCarNoList(startDate, endDate, car);
		} else {
			return feedbackRepository.getDriverList(startDate, endDate, driver);
		}
	}

	@Override
	public void setfeedback(Object object) {
		Feedback newFeedback = (Feedback) object;
		if (newFeedback != null) {
			if (newFeedback.getId() != null) {
				newFeedback.setId(newFeedback.getId());
			}
			newFeedback.setDrivingRating(newFeedback.getDrivingRating());
			newFeedback.setDriverBehaviourRating(newFeedback.getDriverBehaviourRating());
			newFeedback.setDriverTestingRating(newFeedback.getDriverTestingRating());
			newFeedback.setCarConditionRating(newFeedback.getCarConditionRating());
			newFeedback.setOverallServiceRating(newFeedback.getOverallServiceRating());
			float averageRating;
			averageRating = (newFeedback.getDrivingRating() + newFeedback.getDriverBehaviourRating()
					+ newFeedback.getDriverTestingRating() + newFeedback.getCarConditionRating()
					+ newFeedback.getOverallServiceRating()) / 5;
			newFeedback.setAverageRating(averageRating);
			newFeedback.setRemark(newFeedback.getRemark());
			newFeedback.setTripID(newFeedback.getTripID());
			newFeedback.setTanentID(newFeedback.getTanentID());
		}
		try {
			super.save(newFeedback);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setClientModelInFeedback(Feedback feedback) {
		
		ClientModel clientDetail = clientModelRepository.getTripByTripId(feedback.getTripID());
		if (clientDetail != null) {
			feedback.setClientModel(clientDetail);
		}
		try {
			super.update(feedback);
		} catch (ServiceException e) {
			e.getCause();
		}
	}

}