package com.emxcel.dms.portal.controller;
import com.emxcel.dms.core.business.repositories.driver.DriverRepository;
import com.emxcel.dms.core.business.repositories.mapping.CarDriverMappingRepository;
import com.emxcel.dms.core.business.services.mapping.CarDriverMappingService;
import com.emxcel.dms.core.model.car.CarDriverMapping;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.portal.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by emxcelsolution on 1/6/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CarDriverMappingControllerTest {

    /**
     * carDriverMappingRepository
     */
    @Autowired
    private CarDriverMappingRepository carDriverMappingRepository;

    /**
     * carDriverMappingService
     */
    @Autowired
    private CarDriverMappingService carDriverMappingService;
    
    /**
     * driverRepository
     */
    @Autowired
    private DriverRepository driverRepository;

    @Test
    public void updateRemoveCarDriver() {
        CarDriverMapping carDriver = carDriverMappingRepository.findOne(1L);
        if (carDriver != null) {
            carDriver.setDriId(null);
            try {
                carDriverMappingRepository.save(carDriver);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void listCarDriverMap() throws Exception {
        List<CarDriverMapping> carList = carDriverMappingRepository.findAllCarDriver(1L);
        List<Driver> driverList = driverRepository.listOfDriver(1L);
        
        	System.out.println("ListOfCars :"+carList.size());
        	System.out.println("ListOfDrivers :"+driverList.size());
    }

    @Test
    public void updatecarDriver() throws Exception {
        CarDriverMapping carDriver = new CarDriverMapping();
        carDriver.setTanentID(1L);
        carDriver.setCarId(1L);
        carDriver.setDriId(1L);
        Long mapId = 1L;
        if (mapId != null) {
            carDriver.setDriId(mapId);
        }
        try {
        	carDriverMappingService.update(carDriver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}