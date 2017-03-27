package com.emxcel.dms.portal.scheduler;



import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

import javax.inject.Inject;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.portal.model.ResponseBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.auth.UserEntityPackageService;
import com.emxcel.dms.core.business.services.auth.UserPackageService;
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.notification.AlertSchedulerService;
import com.emxcel.dms.core.business.services.notification.NotificationService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.business.utils.SMSSend;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.common.AlertScheduler;
import com.emxcel.dms.core.model.common.Notification;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.core.model.webservice.UserEntityPackage;
import com.emxcel.dms.core.model.webservice.UserPackage;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

@Component
public class AlertSchedulerListener implements ApplicationListener<ContextRefreshedEvent> {

	/**
	 * **We Inject it to use services of AlertSchedulerService **.
	 */
	@Inject
	private AlertSchedulerService alertSchedulerService;

	/**
	 * **We Inject it to use services of ClientModelService **.
	 */
	@Inject
	private ClientModelService clientModelService;

	/**
	 * We Inject it to use services of SimpMessagingTemplate **.
	 */
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	/**
	 * We Inject it to use services of NotificationService **.
	 */
	@Inject
	private NotificationService notificationService;

	/**
	 * We Inject it to use services of UserService **.
	 */
	@Inject
	private UserService userService;

	
	@Inject
	private UserPackageService userPackageService;
	
	@Inject
	private UserEntityPackageService userEntityPackageService;
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationListener#onApplicationEvent(org.
	 * springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {

		try {
			/*
			String path = this.getClass().getClassLoader().getResource("").getPath();
			String fullPath = URLDecoder.decode(path, "UTF-8");
			String pathArr[] = fullPath.split("dms-portal");
			String modelPath=pathArr[0]+ Constants.MODEL_PATH;
			this.getClasses(modelPath);
			*/
			Calendar cal1 = Calendar.getInstance();
			Date date0 = cal1.getTime();
			List<AlertScheduler> alertSchedulerList = alertSchedulerService.getAlertSchedulerList(false, date0);
			if (alertSchedulerList.size() > 0) {
				for (AlertScheduler alertScheduler : alertSchedulerList) {
					ClientModel clientModel = null;
					if (alertScheduler.getTripID() != null && !alertScheduler.getTripID().equals("")) {
						clientModel = clientModelService.getTripByTripId(alertScheduler.getTripID());
					}
					if (alertScheduler.getSchedulerType() == 1) {
						Date date1 = alertScheduler.getPickUpTime();
						Timer timer1 = new Timer(true);
						timer1.schedule(new TimerTask() {
							@Override
							public void run() {
								try {
									ClientModel clientModel = null;
									if (alertScheduler.getTripID() != null && !alertScheduler.getTripID().equals("")) {
										clientModel = clientModelService.getTripByTripId(alertScheduler.getTripID());
									}
									ClientModel clientModelNew = null;
									// 1 hour left then Notification Alert to
									// Admin
									// & DE
									int hour = 1;
									String msg = "Your Trip Has Left " + hour + " Hour to start !!!";
									if (clientModel.getDriver() != null
											&& clientModel.getDriver().getTokenID() != null) {
										CommonUtil.getTokenByContactNo(clientModel.getDriver().getTokenID(),
												msg + " ,tripID=" + clientModel.getTripId() + "," + "latlong="
														+ clientModel.getPickUpLatLong() + "",
												"driverApp");
									}
									// Sleep for 10 sec
									Thread.sleep(10000);
									clientModelNew = clientModelService.getTripByTripId(clientModel.getTripId());
									msg = getMessageForTripAlert(clientModelNew);
									getAlerts(clientModelNew, hour, msg);
									alertSchedulerService.updateAlertSchedulerReturnEntity(true, alertScheduler);
									timer1.cancel();
									timer1.purge();
								} catch (Exception e) {
									e.printStackTrace();
								}
							};
						}, date1);
					}
					if (alertScheduler.getSchedulerType() == 2) {
						Date date2 = alertScheduler.getPickUpTime();
						Timer timer2 = new Timer(true);
						timer2.schedule(new TimerTask() {
							@Override
							public void run() {
								try {
									ClientModel clientModel = null;
									if (alertScheduler.getTripID() != null && !alertScheduler.getTripID().equals("")) {
										clientModel = clientModelService.getTripByTripId(alertScheduler.getTripID());
									}
									ClientModel clientModelNew = null;

									// 45 min left then Notification Alert to
									// Admin
									// & DE
									int fortyFiveMinutes = 45;
									String msg = "Your Trip Has Left " + fortyFiveMinutes + " minutes to start !!!";
									if (clientModel.getDriver() != null
											&& clientModel.getDriver().getTokenID() != null) {
										CommonUtil.getTokenByContactNo(clientModel.getDriver().getTokenID(),
												msg + " ,tripID=" + clientModel.getTripId() + "," + "latlong="
														+ clientModel.getPickUpLatLong() + "",
												"driverApp");
									}
									// Sleep for 10 sec
									Thread.sleep(10000);
									clientModelNew = clientModelService.getTripByTripId(clientModel.getTripId());
									msg = getMessageForTripAlert(clientModelNew);
									getAlerts(clientModelNew, fortyFiveMinutes, "minutes");
									alertSchedulerService.updateAlertSchedulerReturnEntity(true, alertScheduler);
									timer2.cancel();
									timer2.purge();
								} catch (Exception e) {
									e.printStackTrace();
								}
							};
						}, date2);
					}
					if (alertScheduler.getSchedulerType() == 3) {
						Date date3 = alertScheduler.getPickUpTime();
						Timer timer3 = new Timer(true);
						timer3.schedule(new TimerTask() {
							@Override
							public void run() {
								try {
									ClientModel clientModel = null;
									if (alertScheduler.getTripID() != null && !alertScheduler.getTripID().equals("")) {
										clientModel = clientModelService.getTripByTripId(alertScheduler.getTripID());
									}
									ClientModel clientModelNew = null;
									// 30 min left then Notification Alert to
									// Admin
									// & DE
									int thrityMinutes = 30;
									String msg = "Your Trip Has Left " + thrityMinutes + " minutes to start !!!";
									if (clientModel.getDriver() != null
											&& clientModel.getDriver().getTokenID() != null) {
										CommonUtil.getTokenByContactNo(clientModel.getDriver().getTokenID(),
												msg + " ,tripID=" + clientModel.getTripId() + "," + "latlong="
														+ clientModel.getPickUpLatLong() + "",
												"driverApp");
									}
									// Sleep for 10 sec
									Thread.sleep(10000);
									clientModelNew = clientModelService.getTripByTripId(clientModel.getTripId());
									msg = getMessageForTripAlert(clientModel);
									if (clientModelNew != null && clientModelNew.getDriverDistanceMin() != null
											&& thrityMinutes < clientModelNew.getDriverDistanceMin()) {
										getAlerts(clientModelNew, thrityMinutes, msg);
										final String ACCOUNT_SID = "AC4e1f4bb93ef57dc18d1ad9cbb4acea65";
										final String AUTH_TOKEN = "c893e27cd8f65850a3e384e59de4f1d6";
										Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
										Call call = null;
										try {
											call = Call
													.creator(new PhoneNumber("+917405799970"),
															new PhoneNumber("+18583465930"),
															new URI("https://handler.twilio.com/twiml/EHa8565f08cfa0860cd2f067271a9682ce"))
													.create();
										} catch (URISyntaxException e) {
											e.printStackTrace();
										}
									}
									alertSchedulerService.updateAlertSchedulerReturnEntity(true, alertScheduler);
									timer3.cancel();
									timer3.purge();
								} catch (Exception e) {
									e.printStackTrace();
								}
							};
						}, date3);
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @param clientModel
	 * @return
	 */
	public String getMessageForTripAlert(ClientModel clientModel) {
		String msg = "";
		msg = "Driver is beyond the reach of upcoming trip.";
		msg += "<br>Trip : " + clientModel.getTripId() + ".";
		msg += "<br>Car No : " + clientModel.getCar().getCarNo() + ".";
		msg += "<br>Driver Name : " + clientModel.getDriver().getFullName() + ".";
		msg += "<br>Driver Contact Number : " + clientModel.getDriver().getContactNo() + ".";
		msg += "<br>Pick Up Location : " + clientModel.getPickUpLocation() + ".";
		msg += "<br>Please Contact to Driver or change the driver.";
		msg += "<br><a style='color:red;' href='" + UrlConstants.CHANGE_REQUEST
				+ "'>Click On Link to Change Request Page</a>";
		return msg;
	}

	/**
	 * @param clientModel
	 * @param distance
	 * @param msg
	 */
	public void getAlerts(ClientModel clientModel, int distance, String msg) {
		try {
			if (clientModel != null && clientModel.getDriverDistanceMin() != null
					&& distance < clientModel.getDriverDistanceMin()) {
				if (clientModel.getCreatedBy() != null) {
					Notification notification = notificationService.saveAlertSchedulerNotification(msg,
							clientModel.getTanentID());
					User user = userService.getUserByUsername(clientModel.getUpdatedBy());
					List<User> userList = userService.listOfUserByIdAndTanentID(true, clientModel.getTanentID());
					if (user != null) {
						if (distance == 45) {
							if (user.getContactNo() != null) {
								SMSSend.sendSms(Long.valueOf("" + user.getContactNo()), msg);
							}
							if (clientModel.getDriver() != null && clientModel.getDriver().getContactNo() != null) {
								SMSSend.sendSms(Long.valueOf(clientModel.getDriver().getContactNo()), msg);
							}
						}
					}
					if (userList != null && userList.size() > 0) {
						if (distance == 45) {
							if (userList.get(0).getContactNo() != null) {
								SMSSend.sendSms(Long.valueOf("" + userList.get(0).getContactNo()), msg);
							}
						}
						if (userList.get(0).getUsername() != null && !userList.get(0).getUsername().equals("")) {
							simpMessagingTemplate.convertAndSendToUser(userList.get(0).getUsername(), "/queue/notify",
									notification);
						}
					}
					if (user != null && user.getUsername() != null) {
						simpMessagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/notify", notification);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getClasses(String packageName)
			throws ClassNotFoundException, IOException, ServiceException {

		List<ResponseBean> listofResponseBean = null;
		listofResponseBean = findClasses(new File(packageName));
		List<UserPackage> listOfPackage = userPackageService.list();



		for (ResponseBean bean : listofResponseBean) {
				UserPackage userPkg = null;
				boolean isAllowedPackage = true;
				if (listOfPackage.isEmpty()) {
					isAllowedPackage = true;
				}

				for (UserPackage userPackage : listOfPackage) {
					if (userPackage.getPackagename().equalsIgnoreCase(bean.getPackageName())) {
						userPkg = userPackage;
						isAllowedPackage = false;
					}

				}
				if (isAllowedPackage) {
					UserPackage userPackage = new UserPackage();
					userPackage.setPackagename(bean.getPackageName());
					userPackage.setPackagepath(bean.getPath());
					try {
						userPkg = userPackageService.saveReturnEntity(userPackage);
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					List<UserEntityPackage> listOfUserEntityPackage = userEntityPackageService.list();
					for (String filename : bean.getClassName()) {
						boolean isAllowed = true;
						if (listOfUserEntityPackage.isEmpty()) {
							isAllowed = true;
						}
						for (UserEntityPackage userEntityPackage : listOfUserEntityPackage) {
							if (userEntityPackage.getEntityname().equalsIgnoreCase(filename)) {
								isAllowed = false;
							}
						}
						if (isAllowed) {

							UserEntityPackage userEntityPackageNew = new UserEntityPackage();
							userEntityPackageNew.setEntityname(filename);
							userEntityPackageNew.setUserPackage(userPkg);
							userEntityPackageService.save(userEntityPackageNew);
						}
					}

				} catch (ServiceException e) {
					e.printStackTrace();
				}

		}

		// Delete UserPackage that are not exist in current Project but it is available in DB
			List<UserPackage> listOfExtraUserPackage = filterByName(listOfPackage,listofResponseBean);
			for(UserPackage userPackage:listOfExtraUserPackage)
			{
				userPackageService.delete(userPackage);
			}

	}


	public  List<ResponseBean> findClasses(File directory) throws ClassNotFoundException {

		List<ResponseBean> listofbean = new ArrayList<>();
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				if(!(file.getName().equals("generic"))){
					ResponseBean bean = new ResponseBean();
					System.out.println("Directory name ::  " + file.getName());
					bean.setPackageName(file.getName());
					bean.setPath(file.getPath().split("classes/")[1]);
					System.out.print(file.getPath().split("classes/")[1]);
					File[] listofSubfiles = file.listFiles();
					List<String> list = new ArrayList<String>();
					for (File subfile : listofSubfiles) {
						list.add(subfile.getName().split(".class")[0]);
						bean.setClassName(list);
					}
					listofbean.add(bean);
				}
			}

		}
		return listofbean;
	}

	private static List<UserPackage> filterByName(List<UserPackage> firstGroup, List<ResponseBean> secondGroup) {
		List<UserPackage> result=new ArrayList<>();
		for (UserPackage orginal : firstGroup) {
			boolean var=false;
			for (ResponseBean responseBean : secondGroup) {
				if (orginal.getPackagename().equals(responseBean.getPackageName())) {
					var=true;
				}
			}
			if(!var){
				result.add(orginal);
			}
		}
		return result;
	}
}