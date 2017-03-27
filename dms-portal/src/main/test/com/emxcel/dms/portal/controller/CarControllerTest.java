package com.emxcel.dms.portal.controller;

import static org.junit.Assert.fail;

import java.util.List;

import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.Application;
import com.emxcel.dms.portal.constants.UrlConstants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.car.CarNameService;
import com.emxcel.dms.core.business.services.car.CarTypeService;
import com.emxcel.dms.core.model.car.CarName;
import com.emxcel.dms.core.model.car.CarType;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CarControllerTest {

	@Autowired
	private CarTypeService carTypeService;
	
	@Autowired
	private CarNameService carNameService;


	
	@Test
	public void testAddcarType() {
		String id = "1";
		if (id != null && !id.equals("")) {
			 CarType carType = carTypeService.getById(Long.valueOf(id));
			 System.out.println("Data : "+carType.getCarType());
		}
	}

	@Test
	public void testCartypelist() {
		List<CarType> carList = carTypeService.list();
		for (CarType carType : carList){
			System.out.println("CarModel :"+carType.getCarType());
			System.out.println("============================");
		}
	}

	@Test
	public void testSaveCarType() {
        CarType carType = new CarType();
        //carType.setId(3l);
        carType.setCarType("SUV");
		List<CarType> list = carTypeService.checkCarType(
				carType.getCarType(), carType.getId());
		if (list.size() > 0) {
            System.out.println("====== already exist:::::"+list.size());
		} else {
			if (carType.getId() != null) {
				System.out.println("====== Car Type Update Successfully ");
			} else {
				System.out.println(
						 "New Car Type Added Successfully !!!"+carType.getCarType());
			}
			try {
				carTypeService.save(carType);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testDeleteCarType() {
		 CarType carType = new CarType();
		    carType.setId(100l);
	        //carType.setCarType("Sedan");
		List<CarName> list = carNameService.getCarNameById(carType.getId());
		if (list.size() != 0) {
			System.out.println("In Car Model with same Car Type is already assigned !!!");
		} else {
			try {
				 carType = carTypeService.getById(carType.getId());
				 System.out.println("============="+carType.getCarType());
				if (carType != null) {
					carTypeService.delete(carType);
				}
			} catch (NumberFormatException | ServiceException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testAddCarName() {
		CarName carName = new CarName();
		//carName.setId(1l);
		List<CarType> carTypes = carTypeService.list();
		System.out.println("CAR TYPES ============="+carTypes.size()
		);
		if (carName.getId() != null) {
			carName = carNameService.getById(Long.valueOf(carName.getId()));
			System.out.println("NAME==============="+carName.getCarName());
			System.out.println("TYPE==============="+carName.getCarTypeId());
		}
	}



	@Test
	public void testCarName() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveCar() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteCar() {
		fail("Not yet implemented");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCarColor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCarname() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCarByID() {
		fail("Not yet implemented");
	}

}
