package com.emxcel.dms.portal.controller;

import com.emxcel.dms.core.business.repositories.client.ClientModelRepository;
import com.emxcel.dms.core.business.repositories.superadmin.PackageRepository;
import com.emxcel.dms.core.business.services.client.impl.ClientModelServiceImpl;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.superadmin.Impl.PackageServiceImpl;
import com.emxcel.dms.core.business.services.superadmin.PackageService;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.superadmin.PackageModel;
import com.emxcel.dms.portal.Application;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

/**
 * Created by emxcel on 5/1/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PackageControllerTest {
	@Autowired
	ClientModelRepository packageRepository;
    @Test
    public void getClientDetailById() {
        try {
       
            ClientModelServiceImpl packageService = new ClientModelServiceImpl(packageRepository);
            ClientModel pckg= packageService.getById(Long.valueOf(1450L));
          System.out.print("Trip ID  :: "+pckg.getTripId());
        }catch(Exception e)
        {
            e.printStackTrace();

        }
    }


}
