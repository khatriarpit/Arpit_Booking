package com.emxcel.dms.core.business.services.client.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import com.emxcel.dms.core.business.repositories.companymaster.CompanyMasterRepository;
import com.emxcel.dms.core.business.services.companymaster.CompanyMasterService;
import com.emxcel.dms.core.model.companymaster.CompanyMaster;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.repositories.car.CarRepository;
import com.emxcel.dms.core.business.repositories.city.CityRepository;
import com.emxcel.dms.core.business.repositories.client.ClientModelRepository;
import com.emxcel.dms.core.business.repositories.client.PreBookingRepository;
import com.emxcel.dms.core.business.repositories.client.TripDetailsRepository;
import com.emxcel.dms.core.business.repositories.driver.DriverRepository;
import com.emxcel.dms.core.business.repositories.feedback.FeedbackRepository;
import com.emxcel.dms.core.business.repositories.guest.GuestRepository;
import com.emxcel.dms.core.business.repositories.superadmin.InvoicePackageRepository;
import com.emxcel.dms.core.business.repositories.superadmin.RateofContractRepository;
import com.emxcel.dms.core.business.repositories.tax.TaxSlabRepository;
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.client.PreBookingService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.guest.GuestService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.business.utils.OTPData;
import com.emxcel.dms.core.business.utils.SMSSend;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.client.PreBooking;
import com.emxcel.dms.core.model.client.TripDetails;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.feedback.Feedback;
import com.emxcel.dms.core.model.guest.Guest;
import com.emxcel.dms.core.model.superadmin.InvoicePackage;
import com.emxcel.dms.core.model.superadmin.RateOfContract;
import com.emxcel.dms.core.model.tax.TaxSlab;
import com.emxcel.dms.core.model.user.User;

@Service("clientModelService")
public class ClientModelServiceImpl extends DMSEntityServiceImpl<Long, ClientModel> implements ClientModelService {

	@Inject
	private ClientModelRepository clientModelRepository;

	@Inject
	private TripDetailsRepository tripDetailsRepository;

	@Inject
	private FeedbackRepository feedbackRepository;

	@Inject
	private TaxSlabRepository taxSlabRepository;
	
	@Inject
	private DriverRepository driverRepository;
	
	@Inject
	private CarRepository carRepository;
	
	@Inject
	private CityRepository cityRepository;
	
	@Inject
	private InvoicePackageRepository invoicePackageRepository;
	
	@Inject
	private RateofContractRepository rateofContractRepository;
	
	@Inject
	private GuestRepository guestRepository;
	
	@Inject
	private PreBookingRepository preBookingRepository;
	
	@Inject
	private PreBookingService preBookingService;
	
	@Inject
	private GuestService guestService;

	@Inject
	private CompanyMasterService companyMasterService;

	@Inject
	private CompanyMasterRepository companyMasterRepository;

	@PersistenceContext
	private EntityManager manager;

	@Inject
	public ClientModelServiceImpl(ClientModelRepository clientModelRepository) {
		super(clientModelRepository);
		this.clientModelRepository = clientModelRepository;
	}

	@Override
	public List<ClientModel> getByTripStatusByCar(Car car) {
		return clientModelRepository.getByTripStatusByCar(car);

	}

	@Override
	public List<ClientModel> getByTripStatusByCarDriver(Car car,Driver driver) {
		return clientModelRepository.getByTripStatusByCarDriver(car,driver);

	}
	@Override
	public List<ClientModel> getByTripStatusByDriver(Driver driver) {
		return clientModelRepository.getByTripStatusByDriver(driver);

	}

	@Override
	public ClientModel getTripByTripId(String tripid) {
		return clientModelRepository.getTripByTripId(tripid);
	}

	@Override
	public ClientModel getPreviousTripForCar(Long id, String picUpDateTime) {
		return clientModelRepository.getPreviousTripForCar(id, picUpDateTime);
		
	}

	@Override
	public ClientModel getNextTripForCar(Long id, String dropDateTime) {
       return clientModelRepository.getNextTripForCar(id, dropDateTime);
	}


	@Override
	public List<ClientModel> getClientAndGuestModel(Long tanentID) {
		return clientModelRepository.getClientModelListByTenantIDAndCreated(tanentID);
	}

	/**
	 * Created By: Jimit Patel Date: 1-2-2017 Use: To calculate total amount of
	 * minimum kms.
	 * 
	 * @param client
	 * @return Double
	 */
	@Override
	public Double countMinimumKm(final ClientModel clientModel, final TripDetails trDetails) {
		Double totalDaysAmount = 0.0;
		Double extrakmAmount = 0.0;
		Double startKM = trDetails.getStartKm();
		Double endKM = trDetails.getEndKm();
		Double totalKM = endKM - startKM;
		Timestamp startDate = trDetails.getStartDate();
		Timestamp endDate = trDetails.getEndDate();
		Timestamp pickupDate = clientModel.getPickUpDateTime();
		Timestamp dropDate = clientModel.getDropDateTime();
		int day = endDate.getDate() - startDate.getDate();
		long milliseconds = endDate.getTime() - dropDate.getTime();
		int seconds = (int) milliseconds / 1000;
		int hours = seconds / 3600;
		Double minimumKm = clientModel.getMinkms();
		Long actualKm = (long) (day * minimumKm);
		Integer minimumRate = clientModel.getMinrate();
		Integer graceHours = clientModel.getGraceHours();
		Double perDayAmount = (minimumKm * minimumRate);
		Integer remainingHours = hours - graceHours;
		if (hours > graceHours) {
			totalDaysAmount = ((day + 1) * perDayAmount);
		} else if (totalKM > actualKm) {
			totalDaysAmount = (double) (totalKM * minimumRate);
		} else {
			totalDaysAmount = (day * perDayAmount);
		}

		Long invoicecategoryId = clientModel.getInvoicePackage().getId();
		List<TaxSlab> listOfTaxSlab = taxSlabRepository.getTaxSlabById(invoicecategoryId);
		Double taxRate = 0.0;
		for (TaxSlab taxSlab : listOfTaxSlab) {
			taxRate += taxSlab.getRate();
		}
		Double totaltax = (totalDaysAmount * taxRate) / 100;
		return totalDaysAmount + totaltax + trDetails.getPrkingCharges() + trDetails.getTolltax();
	}

	/**
	 * Created By: Jimit Patel Date: 1-2-2017 Use: To calculate total amount of
	 * hours and kms.
	 * 
	 * @param client
	 * @return Double
	 */
	@Override
	public Double countHoursandKm(final ClientModel clientModel, final TripDetails tripDetails) {
		Double totalHoursAmount = 0.0;
		Double preDefineAmount = 0.0;
		Double startKM = tripDetails.getStartKm();
		Double endKM = tripDetails.getEndKm();
		Double totalKM = endKM - startKM;
		Timestamp startDate = tripDetails.getStartDate();
		Timestamp endDate = tripDetails.getEndDate();
		long milliseconds = endDate.getTime() - startDate.getTime();
		int seconds = (int) milliseconds / 1000;
		int actualHours = seconds / 3600;
		Double actualKm = endKM - startKM;
		Double hours = clientModel.getHnkHours();
		Double km = clientModel.getHnkKms();
		Double oldAmount = Double.parseDouble(clientModel.getHnkAmount().toString());
		Double additionalkm = clientModel.getAdditionalKms();
		Double additionalhours = clientModel.getAdditionalHours();
		Double extraKmAmount = 0.0;
		Double extraHoursAmount = 0.0;
		int isExceed = 0;
		if (actualHours > hours) {
			Double extraHours = actualHours - hours;
			extraHoursAmount = extraHours * additionalhours;
			totalHoursAmount += extraHoursAmount;
			isExceed++;
		} else {
			isExceed++;
		}
		if (actualKm > km) {
			Double extraKm = actualKm - km;
			extraKmAmount = extraKm * additionalkm;
			totalHoursAmount += extraKmAmount;
			isExceed++;
		} else {
			isExceed++;
		}
		if (isExceed > 0) {
			totalHoursAmount += oldAmount;
		}
		Long categoryId = clientModel.getInvoicePackage().getId();
		List<TaxSlab> listOfTaxSlab = taxSlabRepository.getTaxSlabById(categoryId);
		if (listOfTaxSlab.size() > 0) {
			Double taxRate = 0.0;
			for (TaxSlab taxSlab : listOfTaxSlab) {
				taxRate += taxSlab.getRate();
			}
			Double totaltax = (totalHoursAmount * taxRate) / 100;

			return totalHoursAmount + totaltax + tripDetails.getPrkingCharges() + tripDetails.getTolltax();
		}

		return totalHoursAmount + tripDetails.getPrkingCharges() + tripDetails.getTolltax();
	}

	@Override
	public List<ClientModel> getDriverByDriverId(Driver driver) {
		return manager.createNamedQuery("getDriverByDriverId", ClientModel.class)
				.setParameter("driverId", driver.getId()).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emxcel.dms.core.business.services.client.ClientModelService#
	 * getTripByTripIdTenant(java.lang.String, java.lang.Long)
	 */
	@Override
	public ClientModel getTripByTripIdTenant(String tripID, Long tenantID) {
		return clientModelRepository.getTripByTripIdTenant(tripID, tenantID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emxcel.dms.core.business.services.client.ClientModelService#
	 * getSortByType(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.Long)
	 */
	@Override
	public List<ClientModel> getSortByType(String type, String startDate, String endDate, String carNo, Long tenantID) {
		// Get List Client Model
		List<ClientModel> clientDetailList = null;
		if (type != null && !type.equals("")) {
			if (type.equals("CarNo") && startDate.equals("CurrentDate")) {
				clientDetailList = manager.createNamedQuery("getTripDetailByCarnoByCurrentDate", ClientModel.class)
						.setParameter("carNo", carNo).setParameter("tenantID", tenantID).getResultList();
			} else {
				clientDetailList = manager.createNamedQuery("getTripDetailByCarno", ClientModel.class)
						.setParameter("carNo", carNo).setParameter("tenantID", tenantID).getResultList();
			}
		}
		return clientDetailList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emxcel.dms.core.business.services.tripstatus.TripStatusService#
	 * getTripStatusByTripID(java.lang.String, java.lang.String)
	 */
	@Override
	public ClientModel getClientModelTripStatusByTripID(String tripID, int status, Long tanentID) {
		if ((tripID != null && status == 0) && tanentID != null) {
			return clientModelRepository.getTripByTripIdTenant(tripID, tanentID);
		} else {
			return clientModelRepository.getClientModelTripStatusByTripID(tripID, status, tanentID);
		}
	}

	@Override
	public List<ClientModel> getPastTrips() {
		return clientModelRepository.getPastTrips();
	}

	@Override
	public List<ClientModel> getPendingTrip(Long tanentID) {
		return clientModelRepository.getPendingTrip(tanentID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emxcel.dms.core.business.services.client.ClientModelService#
	 * getTripsByStatusID(java.lang.String)
	 */
	@Override
	public List<ClientModel> getTripsByStatusID(int status) {
		return clientModelRepository.getTripsByStatusID(status);
	}

	@Override
	public List<ClientModel> getDriverCancelList(Long tanentID) {
		return clientModelRepository.getDriverCancelList(tanentID);
	}

	@Override
	public List<ClientModel> getTripsByStatus(Guest guest) {

		return clientModelRepository.getTripsByStatus(guest);
	}

	@Override
	public List<ClientModel> getTripsBypendingStatus(Guest guest) {
		return clientModelRepository.getTripsBypendingStatus(guest);
	}

	public List<Car> getCarAvailableListByParamTenant(List<String> listoftenant, String fromdatetime, String todatetime,
			String cartype) {
		List<Car> carList;
		carList = manager.createNamedQuery("getCarAvailableListByParamTenant", Car.class)
				.setParameter("picUpDateTime", fromdatetime).setParameter("dropDate", todatetime)
				.setParameter("tanentID", listoftenant).setParameter("carType", cartype).getResultList();
		return carList;
	}

	@Override
	public ClientModel checkTripId(String tripID) {
		return clientModelRepository.checkTripId(tripID);
	}

	@Override
	public List<ClientModel> getDriverByClient(Driver driver) {
		return clientModelRepository.getDriverByClient(driver);
	}

	public List<ClientModel> getEndTrips(Guest guest) {
		List<ClientModel> client = clientModelRepository.getEndTrips(guest);
		List<ClientModel> trips = new ArrayList<ClientModel>();
		for (ClientModel clientModel : client) {
			Feedback feedBack = feedbackRepository.getFeedbackByTripID(clientModel.getTripId());
			if (feedBack == null) {
				trips.add(clientModel);
			}
		}
		return trips;
	}

	@Override
	public List <ClientModel> getPendingtripsofCar(Car car){
       return clientModelRepository.getPendingtripsofCar(car);
	}


	@Override
	public List<ClientModel> getCancelTripByDriver(Long tenantId) {
		return clientModelRepository.getCancelTripByDriver(tenantId);
	}

	@Override
	public List<ClientModel> getCancelTripByCustomer(Long tenantId) {
		return clientModelRepository.getCancelTripByCustomer(tenantId);
	}

	@Override
	public List<ClientModel> getWeekTrips(Long tenantId) {
		List<ClientModel> listOfBooking = null;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		String currentDate = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		try {
			c.setTime(dateFormat.parse(currentDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(Calendar.DATE, 7);
		String nextWeekDate = dateFormat.format(c.getTime());

		listOfBooking = clientModelRepository.getWeekTrips(currentDate, nextWeekDate, tenantId);
		return listOfBooking;
	}

	@Override
	public List<ClientModel> getCustomerCancelList(Long tanentID) {
		return clientModelRepository.getCustomerCancelList(tanentID);
	}
	@Override
	public ClientModel getTripsByStatusByGuest(Guest guest,int status){
		return clientModelRepository.getTripsByStatusByGuest(guest,status);
	}

	@Override
	public List<ClientModel> getCanceledTrips(Long tenantId) {
		return clientModelRepository.getCanceledTrips(tenantId);
	}

	@Override
	public List<ClientModel> getEndTrips(Long tenantId) {
		return clientModelRepository.getEndTrips(tenantId);
	}

	@Override
	public List<ClientModel> getCurrentTrips(Long tenantId) {
		return clientModelRepository.getCurrentTrips(tenantId);
	}
	@Override
	public List<ClientModel> getwholeTrips(Driver driver){
		return clientModelRepository.getwholeTrips(driver);

	}
	@Override
	public List<ClientModel> getTotalTrips(Driver driver){
		return clientModelRepository.getTotalTrips(driver);
	}

	@Override
	public ClientModel getByTanentID(Long tanentID,String tripId) {
		return clientModelRepository.getByTanentID(tanentID,tripId);
	}

	@Override
	public void saveClientDetails(ClientModel clientModel, HttpSession session, String pickUpDateTime,
			String dropDate,String preBookingId) {
		User user = (User) session.getAttribute("user");
		try{
		String pickup = clientModel.getPickUpLocation();
		String pickupEncode = Base64.getEncoder().encodeToString(pickup.getBytes("utf-8"));
		clientModel.setPickUpLocation(pickupEncode);
		String drop = clientModel.getDrop_location();
		String dropEncode = Base64.getEncoder().encodeToString(drop.getBytes("utf-8"));
		clientModel.setDrop_location(dropEncode);
		clientModel.setTanentID(user.getTanentID());
		clientModel.setPickUpDateTime(CommonUtil.convertStringToTimestamp(pickUpDateTime));
		clientModel.setStatusID(CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_PENDING));
		clientModel.setDropDateTime(CommonUtil.convertStringToTimestamp(dropDate));
		// Driver Details Come from Param
		Driver driver = driverRepository.findOne(clientModel.getDriver().getId());
		clientModel.setDriver(driver);
		// Car Details Come From Request Param
		Car car = carRepository.findOne(clientModel.getCar().getId());
		clientModel.setCar(car);

		City city = cityRepository.findOne(clientModel.getCity().getId());
		clientModel.setCity(city);

		InvoicePackage invoice = invoicePackageRepository.findOne(clientModel.getInvoicePackage().getId());
		clientModel.setInvoicePackage(invoice);

		RateOfContract rateOfContract = rateofContractRepository.findOne(clientModel.getRateOfContract().getId());
		clientModel.setRateOfContract(rateOfContract);

		if(!clientModel.getCompanyMaster().getComapanyName().equals(""))
		{
			CompanyMaster companyMaster=companyMasterService.getCompanyMasterByName(clientModel.getCompanyMaster().getComapanyName(), user.getTanentID());
			if(companyMaster !=null){
				clientModel.setCompanyMaster(companyMaster);
			}
		}
		Guest guestDtls=guestRepository.getGuestDetailByContactNo(clientModel.getGuest().getContactNo());
		if(guestDtls==null) {
			Long guestId = guestService.saveGuest(clientModel.getGuest());
			clientModel.setGuest(guestRepository.findOne(guestId));
		}else {
			clientModel.setGuest(guestRepository.findOne(guestDtls.getId()));
		}

		clientModel.setTripId(getVoucherNo(clientModel));

		if(preBookingId!=null && !preBookingId.equals("")) {
            PreBooking preBooking = preBookingRepository.findOne(Long.valueOf(preBookingId));
            clientModel.setInvoiceMode(preBooking.getInvoiceMode());
            clientModel.setRequestId(preBooking.getRequestedBookingId());
            super.save(clientModel);
            preBooking.setStatusID(Constants.REQUEST_CONFIRMED);
            preBookingService.update(preBooking);
        }else {
            super.save(clientModel);
            int otptemp = OTPData.generateOtp();
            Guest guest = clientModel.getGuest();
            SMSSend.sendSms(guest, otptemp);
        }
		if (clientModel.getDriver() != null && clientModel.getDriver().getTokenID() != null) {
			String message = "One Trip has been Booked !! ./n";
			CommonUtil.getTokenByContactNo(clientModel.getDriver().getTokenID(), message, "driverApp");
		}
		if (clientModel.getGuest() != null && clientModel.getGuest().getTokenID() != null) {
			String message = "Your Trip has been Booked !! ./n";
			CommonUtil.getTokenByContactNo(clientModel.getGuest().getTokenID(), message, "feedApp");
		}
	}catch (Exception e) {
		e.getCause();
	}
	}
		
		
	
	public final String getVoucherNo(final ClientModel clientDetailBean) {
			String sortName = "";
			if (clientDetailBean.getDriver() != null) {
				Driver dd = clientDetailBean.getDriver();
				String fname = dd.getFirstName();
				String mname = dd.getMiddleName();
				String lname = dd.getLastName();
				String licenceNoStaring=dd.getLicenseNo();
				int length=licenceNoStaring.length();
				String licenceNo=licenceNoStaring.substring(length-4,length);
				String name = fname.charAt(0) + "" + mname.charAt(0) + "" + lname.charAt(0);
				List<ClientModel> list = clientModelRepository.getTotalTrips(clientDetailBean.getDriver());
				int count = list.size() + 1;
				List<ClientModel> list1 = clientModelRepository.getwholeTrips(clientDetailBean.getDriver());
				int total = list1.size() + 1;
				String pattern = "ddMM";
				Date d1 = new Date();
				SimpleDateFormat format = new SimpleDateFormat(pattern);
				String todayDate = format.format(d1);
				sortName = name.toUpperCase() ;
				sortName=sortName+licenceNo+ (count < 10 ? "0" : "");
				sortName = sortName + count + (total < 10 ? "00" : total <= 99 ? "0" : "");
				sortName = sortName + total;
				String date = todayDate.substring(0, 2);
				String month = todayDate.substring(2, 4);
				CharSequence css = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				if (Integer.parseInt(date) == 27) {
					date = "AA";
				} else if (Integer.parseInt(date) == 28) {
					date = "BB";
				} else if (Integer.parseInt(date) == 29) {
					date = "CC";
				} else if (Integer.parseInt(date) == 30) {
					date = "DD";
				} else if (Integer.parseInt(date) == 31) {
					date = "EE";
				} else {
					date = "" + css.charAt(Integer.parseInt(date) - 1);
				}
				sortName = sortName + date + month;
			}
			return sortName;
		}

	@Override
	public void updateClientDetsils(ClientModel clientModel, String pickUpDateTime, String dropDate) {
		try {
			clientModel.setPickUpDateTime(CommonUtil.convertStringToTimestamp(pickUpDateTime));
			clientModel.setDropDateTime(CommonUtil.convertStringToTimestamp(dropDate));
			ClientModel client = clientModelRepository.findOne(clientModel.getId());
			if (client != null) {
				String pickup = clientModel.getPickUpLocation();
				String pickupEncode = Base64.getEncoder().encodeToString(pickup.getBytes("utf-8"));
				clientModel.setPickUpLocation(pickupEncode);
				client.setPickUpLocation(pickupEncode);
				String drop = clientModel.getDrop_location();
				String dropEncode = Base64.getEncoder().encodeToString(drop.getBytes("utf-8"));
				client.setDrop_location(dropEncode);
                client.setPaymentMode(clientModel.getPaymentMode());


                Guest guestDtls=guestRepository.getGuestDetailByContactNo(clientModel.getGuest().getContactNo());
                if(guestDtls == null) {
                    Long guestId = guestService.saveGuest(clientModel.getGuest());
                    client.setGuest(guestRepository.findOne(guestId));
                }else {
                    client.setGuest(guestRepository.findOne(guestDtls.getId()));
                }
				super.update(client);
			}
		} catch (Exception e) {
			e.getCause();
		}
	}

	@Override
	public void setSatusCancel(String tripId) {
		try {
			if (tripId != null && !("").equals(tripId)) {
				ClientModel client = clientModelRepository.getTripByTripId(tripId);
				if (client != null) {
					client.setStatusID(CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_CANCEL));
					client.setCanceledId("CAL" + tripId);
						super.update(client);
				}
			}
		} catch (Exception e) {
			e.getCause();
		}
	}
		

}