package com.emxcel.dms.portal.restcontroller;

import com.emxcel.dms.core.business.constants.Constants;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.car.CarService;
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.client.TripDeatilsService;
import com.emxcel.dms.core.business.services.driver.DriverService;
import com.emxcel.dms.core.business.services.notification.NotificationService;
import com.emxcel.dms.core.business.services.restservice.geo.GeoDataService;
import com.emxcel.dms.core.business.services.tax.TaxSlabService;
import com.emxcel.dms.core.business.services.trip.ExpenseService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.business.utils.OTPData;
import com.emxcel.dms.core.business.utils.SMSSend;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.client.TripDetails;
import com.emxcel.dms.core.model.common.Notification;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.geo.GeoData;
import com.emxcel.dms.core.model.tax.TaxSlab;
import com.emxcel.dms.core.model.trip.Expense;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.core.beans.StatusModel;
import com.emxcel.dms.portal.constants.UrlConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.ws.rs.QueryParam;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.*;

@RestController
public class LoginRestController {
	@Inject
	private CarService carService;

	@Inject
	private DriverService driverService;

	@Inject
	private ClientModelService clientModelService;

	@Inject
	private GeoDataService geoDataService;

	@Inject
	private TripDeatilsService tripDetailsService;

	@Inject
	private ExpenseService expenseService;

	@Inject
	private TaxSlabService taxSlabService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	/**
	 * NotificationService.
	 */
	@Inject
	private NotificationService notificationService;
	
	/**
	 * UserService
	 */
	@Inject
	private UserService userService;

	/**
	 * Crated By : Johnson Chunara. Used For : Check Car No At Login Time
	 *
	 * @return ResponseEntity<StatusModel>
	 */
	@RequestMapping(value = UrlConstants.CHECK_CAR_NO, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>>checkCarNo(@QueryParam("userToken") String userToken,@QueryParam("resend") String resend) {
		Map<String,Object> map = new HashMap<String,Object>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String carNo = "";
		String pass = "";
		String imeino = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			carNo = newToken[0];
			pass = newToken[1];
			imeino = newToken[2];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (carNo != null && pass != null && imeino != null) {
			Car car = carService.checkCarByCarNo(carNo);
			if (car == null) {
				map.put("status",false);
				map.put(Constants.MESSAGE,"Car No is Not Available");
			} else {
				Driver driver = driverService.getDriverByImeinoTanent(car.getTanentID(), Long.valueOf(imeino));
				if (driver != null) {
					if (pass.equalsIgnoreCase(driver.getPassword()) && imeino.equals(String.valueOf(driver.getImeino()))) {
						if ((driver.getOtp() == null) || (resend!=null)) {
							int otptemp = OTPData.generateOtp();
							map.put("OTP-" ,otptemp);
							try {
								SMSSend.sendOtp(driver, otptemp);
							} catch (Exception e) {
								e.printStackTrace();
							}
							String otpst = String.valueOf(otptemp);
							driverService.setDriverOtp(otpst, driver.getId());
							map.put(Constants.MESSAGE,"OTP is Send Success fully");

						}
						map.put("driverDetails",driver);
						map.put("status",true);
						map.put(Constants.MESSAGE,"Credantial Match");
					} else {

						map.put("status",false);
						map.put(Constants.MESSAGE,"Credantial Not Match");

					}
				} else {
					map.put("status",false);
					map.put(Constants.MESSAGE,"Car And Driver Not Assigned");
				}
			}
		}
		return new ResponseEntity<>(map,HttpStatus.OK);
	}

	/**
	 * Crated By : Johnson Chunara. Used For : Check Car No At Login Time with
	 * OTP
	 *
	 * @param carno
	 *            **carno**
	 * @param otp
	 *            **otp**
	 * @param tokenID
	 *            **tokenId**
	 * @return ResponseEntity<StatusModel>
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = UrlConstants.CHECK_CAR_NO_OTP, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>>  getCarNoByOtp(@QueryParam("otp") final String otp,
			@QueryParam("tokenID") final String tokenID, @QueryParam("userToken") String userToken) {
		Map<String,Object> map =new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String carNo = "";
		String pass = "";
		String imeino = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			carNo = newToken[0];
			pass = newToken[1];
			imeino = newToken[2];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (carNo != null && otp != null && tokenID != null) {
			Car car = carService.checkCarByCarNo(carNo);
			if (car == null) {
				map.put("status",false);
				map.put(Constants.MESSAGE,"Car No is Not Available");
			} else {
				Driver driver = driverService.getDriverByImeinoTanent(car.getTanentID(), Long.valueOf(imeino));
				if (driver != null) {
					if (otp.equals(driver.getOtp())) {
						if (driver.getTokenID() == null || !tokenID.equalsIgnoreCase(driver.getTokenID())) {
							driverService.updateDriverDetailBytokenID(tokenID, otp);
						}
						map.put("status",true);
						map.put(Constants.MESSAGE,"Sucesss fully Login");
						map.put("driverDetails",driver);
					} else {
						map.put("status",false);
						map.put(Constants.MESSAGE,"InValid Login");
					}
				} else {
					map.put("status",false);
					map.put(Constants.MESSAGE,"Car And Driver Not Assigned");
				}
			}
		}
		return new ResponseEntity<>(map,HttpStatus.OK);
	}

	/**
	 * Crated By : Johnson Chunara. Used For : Get Trips For Car No
	 *
	 * @param carno
	 *            **carno**
	 * @return ResponseEntity<StatusModel>
	 */

	@RequestMapping(value = UrlConstants.GET_TRIPS_CAR_NO, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getTripsByCarNo(@QueryParam("userToken") String userToken) {
		Map<String, Object> map = new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String carNo = "";
		String pass = "";
		String imeino = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			carNo = newToken[0];
			pass = newToken[1];
			imeino = newToken[2];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Car car = carService.checkCarByCarNo(carNo);
		if (car!= null) {
			Driver driver = driverService.getDriverByImeinoTanent(car.getTanentID(), Long.valueOf(imeino));
			if (driver != null) {
				if (pass.equalsIgnoreCase(driver.getPassword())) {
					List<ClientModel> tripListByDriver = clientModelService.getByTripStatusByDriver(driver);
					if(!tripListByDriver.isEmpty()){
						map.put("List Of Trips",tripListByDriver);
						map.put("status",true);
						map.put(Constants.MESSAGE,"Data Available For This TripID");
					} else {
						map.put("status",false);
						map.put(Constants.MESSAGE,"No Trips Available For This Car");
					}
				} else {
					map.put("status",false);
					map.put(Constants.MESSAGE,"Car No And Password doesn't match");
				}
			} else {
				map.put("status",false);
				map.put(Constants.MESSAGE,"Driver Not Assigned for this car");
			}
		} else {
			map.put("status",false);
			map.put(Constants.MESSAGE,"Car And Driver Not Assigned");
		}
		return new ResponseEntity<>(map,HttpStatus.OK);
	}

	/**
	 * Crated By : Johnson Chunara. Used For : Set TripStatus live for TripID
	 *
	 * @param tripid
	 *            **tripid**
	 * @param status
	 *            **status**
	 * @param startKm
	 *            **startKm**
	 * @return ResponseEntity<StatusModel>
	 */

	@RequestMapping(value = UrlConstants.SET_START_TRIP_STATUS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> setStartTripStatus(@QueryParam("tripID") final String tripID,
			@QueryParam("status") final String status, @QueryParam("startKm") final String startKm,
			@QueryParam("startDate") final String startDate, @QueryParam("userToken") String userToken) {
		Map<String,Object> map=new HashMap<>();
		if (startDate != null) {
			final byte[] decoded = Base64.getDecoder().decode(userToken);
			String carNo = "";
			String pass = "";
			String imeino = "";
			try {
				String decodeToken = new String(decoded, "UTF-8");
				String[] newToken = decodeToken.split("-");
				carNo = newToken[0];
				pass = newToken[1];
				imeino = newToken[2];
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Car car = carService.checkCarByCarNo(carNo);
			if (car!= null) {
				Driver driver = driverService.getDriverByImeinoTanent(car.getTanentID(), Long.valueOf(imeino));
				if (driver != null) {
					if (pass.equalsIgnoreCase(driver.getPassword())) {
						if (tripID != null && status != null && startKm != null && startDate != null) {
							ClientModel client = clientModelService.getTripByTripId(tripID);
							if (client == null) {
								map.put("status",false);
								map.put(Constants.MESSAGE,"No Trips Available");
							} else {
								client.setStatusID(CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_LIVE));
								TripDetails tripDetail = new TripDetails();
								tripDetail.setTripId(tripID);
								tripDetail.setStartKm(Double.parseDouble(startKm));
								tripDetail.setStartDate(convertStringToTimestamp(startDate));
								
								if (client.getGuest() != null && client.getGuest().getTokenID() != null) {
									String message = "Your Trip is Start !! ./n";
									CommonUtil.getTokenByContactNo(client.getGuest().getTokenID(), message, "feedApp");
								}
								try {
									tripDetailsService.save(tripDetail);
									clientModelService.update(client);
								} catch (ServiceException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								map.put("status",true);
								map.put(Constants.MESSAGE,"Trips Update SucessFully");
							}
						} else {
							map.put("status",false);
							map.put(Constants.MESSAGE,"In valid Parameters");
						}
					} else {
						map.put("status",false);
						map.put(Constants.MESSAGE,"Car No And Password doesn't match");
					}
				} else {

					map.put("status",false);
					map.put(Constants.MESSAGE,"Car And Driver Not Assigned");
				}
			} else {
				map.put("status",false);
				map.put(Constants.MESSAGE,"CarNo Doesn't Exist");
			}
		} else {
			map.put("status",false);
			map.put(Constants.MESSAGE,"Start Date Doesn't Exist");
		}
		return new ResponseEntity<>(map,HttpStatus.OK);
	}

	/**
	 * Crated By : Johnson Chunara. Used For : Set TripStatus end for TripID
	 *
	 * @param tripid
	 *            **tripid**
	 * @param status
	 *            **status**
	 * @param endKm
	 *            **endKm**
	 * @param tollTax
	 *            **tollTax**
	 * @param parkingCharges
	 *            **parkingCharges**
	 * @return ResponseEntity<StatusModel>
	 */

	@RequestMapping(value = UrlConstants.SET_END_TRIP_STATUS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> setEndTripStatus(@QueryParam("tripID") final String tripID,
			@QueryParam("status") final String status, @QueryParam("endKm") String endKm,
			@QueryParam("tollTax") String tollTax, @QueryParam("parkingCharges") String parkingCharges,
			@QueryParam("endDate") String endDate,@QueryParam("mapImage") byte[] mapImage ,@QueryParam("userToken") String userToken) {
		Map<String,Object> map=new HashMap<>();
		if (endDate != null) {
			final byte[] decoded = Base64.getDecoder().decode(userToken);
			String carNo = "";
			String pass = "";
			String imeino = "";
			try {
				String decodeToken = new String(decoded, "UTF-8");
				String[] newToken = decodeToken.split("-");
				carNo = newToken[0];
				pass = newToken[1];
				imeino = newToken[2];
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Car car = carService.checkCarByCarNo(carNo);
			if (car!= null) {
				Driver driver = driverService.getDriverByImeinoTanent(car.getTanentID(), Long.valueOf(imeino));
				if (driver != null) {
					if (pass.equalsIgnoreCase(driver.getPassword())) {
						if (tripID != null && status != null && endKm != null && tollTax != null
								&& parkingCharges != null && endDate != null) {
							ClientModel client = clientModelService.getTripByTripId(tripID);
							if (client == null) {
								map.put("status",false);
								map.put(Constants.MESSAGE,"No Trips Available");
							} else {
								if (tripDetailsService.getTripDetailsByTripId(tripID) != null) {
									client.setStatusID(CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_END));
									client.setMapImageUrl(mapImage);
									TripDetails tripDetails = tripDetailsService.getTripDetailsByTripId(tripID);
									tripDetails.setEndKm((Double.parseDouble(endKm)));
									tripDetails.setTolltax(Long.valueOf(tollTax));
									tripDetails.setPrkingCharges(Long.valueOf(parkingCharges));
									tripDetails.setEndDate(convertStringToTimestamp(endDate));
									Double amount = 0.0;
									try {
										tripDetailsService.update(tripDetails);
										clientModelService.update(client);
										if (client.getInvoiceMode().equalsIgnoreCase("Auto")) {
											if (client.getRateOfContract().getRateOfContract()
													.equalsIgnoreCase("Minimum Km")) {
												amount = clientModelService.countMinimumKm(client, tripDetails);
											} else if (client.getRateOfContract().getRateOfContract()
													.equalsIgnoreCase("Hours And Km")) {
												amount = clientModelService.countHoursandKm(client, tripDetails);
											} else if (client.getRateOfContract().getRateOfContract()
													.equalsIgnoreCase("Source And Destination")) {
												amount = (double) client.getSndPrice();
												Long categoryId = client.getInvoicePackage().getId();
												List<TaxSlab> listOfTaxSlab = taxSlabService.getTaxSlabById(categoryId);
												Double taxRate = 0.0;
												for (TaxSlab taxSlab : listOfTaxSlab) {
													taxRate += taxSlab.getRate();
												}
												Double totaltax = (amount * taxRate) / 100;
												amount += totaltax;
											} else {
												amount = 0.0;
											}
										}
										if (client.getGuest() != null && client.getGuest().getTokenID() != null) {
											String message = "Your Trip started on "+tripDetails.getStartDate()+" and end on "+tripDetails.getEndDate()+" !! ./n";
											CommonUtil.getTokenByContactNo(client.getGuest().getTokenID(), message, "feedApp");
										}
										ClientModel clientModel = clientModelService.getTripByTripId(tripID);
										clientModel.setBillableAmount(amount);
										clientModelService.update(clientModel);
									} catch (ServiceException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									map.put("status",true);
									map.put(Constants.MESSAGE,"Trip Update Sucessfully");
									map.put("amount",amount);
								} else {
									map.put("status",false);
									map.put(Constants.MESSAGE,"Trip Yet Not Started");
								}
							}
						}
					} else {
						map.put("status",false);
						map.put(Constants.MESSAGE,"Car No And Password doesn't match");
					}

				} else {
					map.put("status",false);
					map.put(Constants.MESSAGE,"Car And Driver Not Assigned");
				}
			} else {
				map.put("status",false);
				map.put(Constants.MESSAGE,"Car No Doesn't Exsits");
			}
		} else {
			map.put("status",false);
			map.put(Constants.MESSAGE,"End Date Doesn't Exsits");
		}
		return new ResponseEntity<>(map,HttpStatus.OK);
	}

	/**
	 * Crated By : Johnson Chunara. Used For : Send Geographic data for trip id
	 * to database
	 *
	 * @param tripid
	 *            **tripid**
	 * @param longitude
	 *            **longitude**
	 * @param latitude
	 *            **latitude**
	 * @return ResponseEntity<StatusModel>
	 */
	@RequestMapping(value = UrlConstants.SAVE_GEO_DATA, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> saveGeoData(@QueryParam("latitude") final String latitude,
			@QueryParam("longitude") final String longitude, @QueryParam("tripID") final String tripID,
			@QueryParam("userToken") String userToken) {
		Map<String,Object> map=new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String carNo = "";
		String pass = "";
		String imeino = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			carNo = newToken[0];
			pass = newToken[1];
			imeino = newToken[2];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Car car = carService.checkCarByCarNo(carNo);
		if (car!= null) {
			Driver driver = driverService.getDriverByImeinoTanent(car.getTanentID(), Long.valueOf(imeino));
			if (driver != null) {
				if (pass.equalsIgnoreCase(driver.getPassword())) {
					if (latitude != null && longitude != null && tripID != null) {
						GeoData geoData = new GeoData();
						geoData.setTripID(tripID);
						geoData.setLongitude(longitude);
						geoData.setLatitude(latitude);
						try {
							geoDataService.save(geoData);
						} catch (ServiceException e) {
							e.printStackTrace();
						}
						map.put("status",true);
						map.put(Constants.MESSAGE,"Saved SuccessFully");
					} else {
						map.put("status",false);
						map.put(Constants.MESSAGE,"Saved UnSuccessFully");
					}
				} else {
					map.put("status",false);
					map.put(Constants.MESSAGE,"Car No And Password doesn't match");
				}
			} else {
				map.put("status",false);
				map.put(Constants.MESSAGE,"Car And Driver Not Assigned");
			}
		} else {
			map.put("status",false);
			map.put(Constants.MESSAGE,"Car No Doesn't Exists");
		}
		return new ResponseEntity<>(map,HttpStatus.OK);
	}

	/**
	 * Crated By : Johnson Chunara. Used For : Get Geographic data for trip id
	 * to database
	 *
	 * @param tripid
	 *            **tripid**
	 * @return ResponseEntity<StatusModel>
	 */

	@RequestMapping(value = UrlConstants.GET_GEO_DATA, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> grtGeoData(@QueryParam("tripID") final String tripID,
			@QueryParam("userToken") String userToken) {
		Map<String,Object> map=new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String carNo = "";
		String pass = "";
		String imeino = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			carNo = newToken[0];
			pass = newToken[1];
			imeino = newToken[2];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Car car = carService.checkCarByCarNo(carNo);
		if (car!= null) {
			Driver driver = driverService.getDriverByImeinoTanent(car.getTanentID(), Long.valueOf(imeino));
			if (driver != null) {
				if (pass.equalsIgnoreCase(driver.getPassword())) {
					if (tripID != null) {
						GeoData geoData = geoDataService.getgeoData(tripID);
						map.put("geoData",geoData);
						map.put("status",true);
						map.put(Constants.MESSAGE,"Fetch SuccessFully");
					} else {
						map.put("status",false);
						map.put(Constants.MESSAGE,"Fetch UnSuccessFully");
					}
				} else {
					map.put("status",false);
					map.put(Constants.MESSAGE,"Car No And Password doesn't match");
				}

			} else {
				map.put("status",false);
				map.put(Constants.MESSAGE,"Car No And Password doesn't match");
			}

		} else {
			map.put("status",false);
			map.put(Constants.MESSAGE,"Car Doesn't Exists");
		}
		return new ResponseEntity<>(map,HttpStatus.OK);
	}

	/**
	 * Crated By : Nittin Patel Date : 31-01-2017. Used For : save fuel &
	 * vehicle expences in database and send back to total expenses.
	 *
	 * @param fuelCharge
	 *            **fuelCharge**.
	 * @param vehicleCharge
	 *            **vehicleCharge**.
	 * @return ResponseEntity<StatusModel>
	 */
	@RequestMapping(value = UrlConstants.SAVE_EXPENSE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> saveExpense(@QueryParam("fuelCharge") final Double fuelCharge,@QueryParam("date") final String date,
			@QueryParam("vehicleCharge") final Double vehicleCharge, @QueryParam("userToken") String userToken) {
		float totalFuel = 0;
		float totalVehicle = 0;
		float total = 0;
		Double fuel = 0.0;
		Double vehicle = 0.0;
		String lastInsertDate = "";
		Map<String, Object> map = new HashMap<String, Object>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String carNo = "";
		String pass = "";
		String imeino = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			carNo = newToken[0];
			pass = newToken[1];
			imeino = newToken[2];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Car car = carService.checkCarByCarNo(carNo);
		if (car!= null) {

			Driver driver = driverService.getDriverByImeinoTanent(car.getTanentID(), Long.valueOf(imeino));
			if (driver != null) {
				if (pass.equalsIgnoreCase(driver.getPassword())) {
					if (fuelCharge != 0.0 && vehicleCharge != 0.0) {
						Expense expense = new Expense();
						Timestamp timestamp = new Timestamp(System.currentTimeMillis());
						expense.setCarno(car.getCarNo());
						expense.setCreatedDate(timestamp);
						expense.setFuelCharge(fuelCharge);
						expense.setVehicleCharge(vehicleCharge);
						try {
							expenseService.save(expense);
						} catch (ServiceException e) {
							e.printStackTrace();
						}
						List<Expense> expenseList = expenseService.getTotalCharges(CommonUtil.convertStringToTimestamp(date));
						if (expenseList.size() > 0) {
							for (Expense expense2 : expenseList) {
								 fuel = expense2.getFuelCharge();
								 vehicle = expense2.getVehicleCharge();
								
								totalFuel += expense2.getFuelCharge();
								totalVehicle += expense2.getVehicleCharge();
								total = totalFuel + totalVehicle;
								lastInsertDate = "" + expense2.getCreatedDate();
							}
						}
						map.put("TotalFuel", totalFuel);
						map.put("TotalVehicle", totalVehicle);
						//map.put("data", expenseList);
						map.put("status", true);
						map.put("message", "Saved SuccessFully");
						//map.put("date", lastInsertDate);
					} else {
						List<Expense> expenseList = expenseService.getTotalCharges(CommonUtil.convertStringToTimestamp(date));
						if (expenseList.size() == 0) {
							totalFuel = 0;
							totalVehicle = 0;
							total = 0;
							lastInsertDate = "" + new Timestamp(System.currentTimeMillis());
						} else {
							for (Expense expense2 : expenseList) {
								fuel = expense2.getFuelCharge();
								 vehicle = expense2.getVehicleCharge();
							}
						}
						map.put("TotalFuel", totalFuel);
						map.put("TotalVehicle", totalVehicle);
						//map.put("data", expenseList);
						map.put("status", false);
						map.put("message", "Saved UnsuccessFully");
					}
				} else {
					map.put("status", false);
					map.put("message", "Car Number And Password doesn't match");
				}
			} else {
				map.put("status", false);
				map.put("message", "Car And Driver Not Assigned");
			}

		} else {
			map.put("status", false);
			map.put("message", "Car Number Doesn't Exist");
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	@RequestMapping(value = UrlConstants.CANCEL_REQUEST_DE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<StatusModel> cancelRequest(
			@QueryParam("tripID") final String tripID,
			@QueryParam("tenantID") final String tenantID, 
			@QueryParam("status") final String status,
			@QueryParam("userToken") String userToken) {
		StatusModel addStatus = new StatusModel();
		List<String> messages = new ArrayList<String>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String carNo = "";
		String pass = "";
		String imeino = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			carNo = newToken[0];
			pass = newToken[1];
			imeino = newToken[2];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Car car = carService.checkCarByCarNo(carNo);
		if (car!= null) {
			Driver driver = driverService.getDriverByImeinoTanent(car.getTanentID(), Long.valueOf(imeino));
			if (driver != null) {
				if (pass.equalsIgnoreCase(driver.getPassword())) {
					if (tripID != null && tenantID != null && status != null) {
						if (clientModelService.getTripByTripIdTenant(tripID, Long.valueOf(tenantID)) != null) {
							ClientModel clientmodel = clientModelService.getTripByTripIdTenant(tripID,
									Long.valueOf(tenantID));
							clientmodel.setStatusID(CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_DR_CANCEL));
							try {
								clientModelService.update(clientmodel);
								String msg = "Customer Request for Cancel Trip. Trip : "+clientmodel.getTripId();
								Notification notification = notificationService.saveAlertSchedulerNotification(msg , clientmodel.getTanentID());
								List<User> userList = userService.listOfUserByIdAndTanentID(true, clientmodel.getTanentID());
								if (userList.size() > 0) {
									simpMessagingTemplate.convertAndSendToUser(userList.get(0).getUsername(), "/queue/notify", notification);
								}
								simpMessagingTemplate.convertAndSendToUser(clientmodel.getUpdatedBy(), "/queue/notify", notification);
							} catch (Exception e) {
								e.printStackTrace();
							}
							addStatus.setStatus("0");
							messages.add("Trip Update SuccessFully");
							addStatus.setMessage(messages);
						} else {
							addStatus.setStatus("1");
							messages.add("No Trip Available");
							addStatus.setMessage(messages);

						}

					} else {
						addStatus.setStatus("1");
						messages.add("Trip Update UnsuccessFully");
						addStatus.setMessage(messages);
					}
				} else {
					addStatus.setStatus("1");
					messages.add("Car No And Password doesn't match");
					addStatus.setMessage(messages);
				}
			} else {
				addStatus.setStatus("1");
				messages.add("Car And Driver Not Assigned");
				addStatus.setMessage(messages);
			}
		} else {
			addStatus.setStatus("1");
			messages.add("Car No Doesn't Exists");
			addStatus.setMessage(messages);
		}
		return new ResponseEntity<StatusModel>(addStatus, HttpStatus.OK);
	}

	@RequestMapping(value = UrlConstants.SAVE_DISTANCE_TRIP, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<StatusModel> cancelRequest(@QueryParam("tripID") final String tripID,
			@QueryParam("distance") final String distance) {
		StatusModel addStatus = new StatusModel();
		List<String> messages = new ArrayList<>();
		ClientModel clientModel = clientModelService.getTripByTripId(tripID);
		if (clientModel != null) {
			clientModel.setDriverDistanceMin(Integer.parseInt(distance));
			try {
				clientModelService.save(clientModel);
				addStatus.setStatus("0");
				messages.add("Trips Available");
				addStatus.setMessage(messages);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		} else {
			addStatus.setStatus("1");
			messages.add("Trips Not Available");
			addStatus.setMessage(messages);
		}
		return new ResponseEntity<>(addStatus, HttpStatus.OK);
	}

	public static Timestamp convertStringToTimestamp(final String strDate) {
		try {
			java.sql.Timestamp timeStampDate = java.sql.Timestamp.valueOf(strDate);
			return timeStampDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}