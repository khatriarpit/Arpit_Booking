package com.emxcel.dms.portal.controller;

import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.List;

import com.emxcel.dms.core.business.utils.OTPData;
import com.emxcel.dms.core.business.utils.SMSSend;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emxcel.dms.core.business.repositories.car.CarRepository;
import com.emxcel.dms.core.business.repositories.client.ClientModelRepository;
import com.emxcel.dms.core.business.repositories.companymaster.CompanyMasterRepository;
import com.emxcel.dms.core.business.repositories.driver.DriverRepository;
import com.emxcel.dms.core.business.repositories.guest.GuestRepository;
import com.emxcel.dms.core.business.repositories.superadmin.InvoicePackageRepository;
import com.emxcel.dms.core.business.repositories.superadmin.RateofContractRepository;
import com.emxcel.dms.core.business.repositories.tax.TaxSlabRepository;
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.client.impl.ClientModelServiceImpl;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.guest.Guest;
import com.emxcel.dms.core.model.tax.TaxSlab;
import com.emxcel.dms.portal.Application;

/*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration

public class ClientControllerTest {
	
	@InjectMocks
	private ClientModelServiceImpl clientModelServiceImpl;

	@Mock
	private ClientModelRepository carTypeRepository;
	/**
	 * **We autowired it to use services of DriverService **.
	 */
	@Autowired
	private  DriverRepository driverRepository;
	/**
	 * **We autowired it to use services of ClientModelService **.
	 */
	@Autowired
	private ClientModelRepository clientModelRepository;

	@Autowired
	private ClientModelService clientModelService;
	/**
	 * **We autowired it to use services of GuestService **.
	 */
	@Autowired
	private GuestRepository guestRepository;
	/**
	 * **We autowired it to use services of CarService **.
	 */
	@Autowired
	private CarRepository carRepository;

	/**
	 * **We autowired it to use services of RateOfContractService **.
	 */
	@Autowired
	private RateofContractRepository rateofContractRepository;
	/**
	 * **We autowired it to use services of InvoicePackageService **.
	 */
	@Autowired
	private InvoicePackageRepository invoicePackageRepository;

	@Autowired
	private CompanyMasterRepository companyMasterRepository;

	@Autowired
	private TaxSlabRepository taxSlabRepository;

	@Before
	public void prepare() {
		MockitoAnnotations.initMocks(this);
	}

	/*@Test
	public void getCompanyList(){
		List<CompanyMaster> comapnyList = companyMasterRepository
				.getCompanyNameList();
	}*/
	@Test
	public void listBookedCars(){
		List<ClientModel> client = clientModelRepository
				.getClientModelListByTenantIDAndCreated(100L);
		System.out.print(client.size());
	}

	@Test
	public void invoiceTexList(){
		List<TaxSlab> listoftax = taxSlabRepository
				.getTaxSlabById(Long.valueOf(3L));
		System.out.println(listoftax.size());
	}
	@Test
	public void updateClient() throws Exception {
		ClientModel clientModel = new ClientModel();
		clientModel.setId(850L);
		//ClientModel client = clientModelService.getById(clientModel.getId());
		ClientModel client = clientModelRepository.findOne(clientModel.getId());
		System.out.print(client.toString());
		if (client != null) {
			/*String pickup = clientModel.getPickUpLocation();
			String pickupEncode = Base64.getEncoder()
					.encodeToString(pickup.getBytes("utf-8"));
			clientModel.setPickUpLocation(pickupEncode);
			client.setPickUpLocation(pickupEncode);
			String drop = clientModel.getDrop_location();
			String dropEncode = Base64.getEncoder()
					.encodeToString(drop.getBytes("utf-8"));
			client.setDrop_location(dropEncode);*/

			Guest guest = client.getGuest();
			guest.setPersonName("mathyue");
			//guest.setPersonName(guest.getPersonName());
			guest.setContactNo(guest.getContactNo());
			guest.setEmailId(guest.getEmailId());
			//guestService.update(guest);
			guestRepository.save(guest);
			clientModelRepository.save(client);
		}

		System.err.println("Hello");
	}

	@Test
	public void saveClient() throws Exception{
		ClientModel clientModel = new  ClientModel();
		clientModelService.save(clientModel);

	}

	@Test
	public void OTp() throws Exception{
		ClientModel clientModel = new  ClientModel();
		clientModelService.save(clientModel);
		int otptemp = OTPData.generateOtp();
		String contact = "7878901010";
		clientModel.getGuest().setContactNo(contact);
		Guest guest =  clientModel.getGuest();
		SMSSend.sendSms(guest, otptemp);
	}

	@Test
	public void customer_Cancel_Approve(){
		ClientModel clientModel = new  ClientModel();
		int otptemp = OTPData.generateOtp();
		String contact = "7878901010";
		clientModel.getGuest().setContactNo(contact);
		Guest guest =  clientModel.getGuest();
		try {
			SMSSend.sendSms(guest, otptemp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*@Test
	public void testCountMinimumKm(ClientModel client) {
		Double totalDaysAmount = 0.0;
		int day = 0;
		client.setTripId("pIJ0if2X");
		ClientModel clientModel = clientModelServiceImpl.getTripByTripId(client.getTripId());
		Timestamp pickupDate = clientModel.getPickUpDateTime();
		Timestamp dropDate = clientModel.getDropDateTime();
	    day = dropDate.getDate() - pickupDate.getDate();
		System.out.println(day);
		long milliseconds = dropDate.getTime() - pickupDate.getTime();
		int seconds = (int) milliseconds / 1000;
		int hours = seconds / 3600;
		int days = hours/ 24;
		int hour = hours%days;
		Double minimumKm = clientModel.getMinkms();
		Integer minimumRate  = clientModel.getMinrate();
		Integer graceHours = clientModel.getGraceHours();
		Double perDayAmount = (minimumKm * minimumRate);
		if(hour>graceHours){
			 totalDaysAmount = ((days+1) * perDayAmount);
		}
		else{
			 totalDaysAmount = (days * perDayAmount);
		}
		System.out.println(totalDaysAmount);
	}

	@Test
	public void testCountHoursandKm() {
		fail("Not yet implemented");
	}
*/
}
