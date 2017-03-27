package com.emxcel.dms.portal.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;

import org.junit.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.management.openmbean.InvalidKeyException;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.portal.Application;
import com.emxcel.dms.portal.model.ResponseBean;
import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.payment.BillPaymentModelService;
import com.emxcel.dms.core.model.payment.BillPaymentModel;

/**
 * Created by emxcel on 4/1/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class Testing {

    @Inject
    private BillPaymentModelService billPaymentModelService;

    /*@Test
    public void testGetCarType() throws Exception {
        String tanentId = "javadev5@emxcelsolutions.com 1";
        String base64encodedString = Base64.getEncoder().encodeToString(tanentId.getBytes("utf-8"));
        System.err.println("base64encodedString:::::::::::::" + base64encodedString);
        final byte[] decoded = Base64.getDecoder().decode(base64encodedString);
        if (decoded != null) {
            String decodeToken;
            decodeToken = new String(decoded, "UTF-8");
            if (decodeToken != null) {
                String[] newToken = decodeToken.split(" ");
                if (newToken.length > 2) {
                    String companyName = newToken[0] != null && !newToken[0].equals("") ? newToken[0] : "";
                    String tenantID = newToken[1] != null && !newToken[1].equals("") ? newToken[1] : "";
                    System.err.println("companyName:::::::::" + companyName);
                    System.err.println("tenantID:::::::::" + tenantID);
                }
            }
<<<<<<< Updated upstream
        }

        // Testing Token Id
        CommonUtil.getTokenByContactNo("fP0BKH8nGjY:APA91bHoOnAF0IWqkAmy24eBG2jBFEEPEP2rVwCKjZzFmDyfwMTmWHepczt_lOYr6QxMVVjOZ2R4T2Q80lJdzMWp2FGMljteCCX--ksDoMN5X4_LMegWPPp0VWYx8JtZDIuldvWiFcuv", "hello");
    }

        }
    }*/
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        /*String value= "GJ 01 MH 4578-ramesh123-355259075247091";
		//Encode

		String encoded =Base64.getEncoder().encodeToString(value.getBytes("utf-8"));
		System.out.println("encoded : "+encoded);

		//Decode
		byte[] decoded =Base64.getDecoder().decode(encoded);
		System.out.println("Decode :" +new String(decoded));*/
Testing test=new Testing();
        test.getClasses("com.emxcel.dms.core.model");


    }


/*	public void saveBillPaymentModePayUMoney() throws java.security.InvalidKeyException, ServiceException{

		 String amount = "200";
		 String accessKey = "HSHOC3Q8U6RGJG5WX7QG";
	     String secretKey = "742d354a30ad2aacc21440f507b35f42feccd9bb";
	     String returnUrl = "35.154.98.128:8080/PaymentIntegration/return_emxcel.jsp";
	     String txnID = String.valueOf(System.currentTimeMillis());
	     String dataString = "merchantAccessKey=" + accessKey + "&transactionId=" + txnID + "&amount=" + amount;
	     String tripId = "FR45444";
	 	SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
	 	try {
	 		Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(secretKeySpec);
			byte []hmacArr = mac.doFinal(dataString.getBytes());
			StringBuilder build = new StringBuilder();
		 	for (byte b : hmacArr) {
		 	    build.append(String.format("%02x", b));
		 	}
		 	String hmac = build.toString();
		 	BillPaymentModel billPaymentModel= billPaymentModelService.saveBillPaymentMode(amount,accessKey,returnUrl,txnID,hmac,tripId);

		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
		}
	}*/

    public void getClasses(String packageName)
            throws ClassNotFoundException, IOException {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration resources = classLoader.getResources(path);
        List dirs = new ArrayList();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            System.out.println("File :  : : " + resource.getFile());
            dirs.add(new File(resource.getFile()));
        }

        for (Object directory : dirs) {
            System.out.println(directory.toString());
            List<ResponseBean> listofResponseBean = findClasses(new File(directory.toString()), packageName);
            System.out.println(listofResponseBean.size());
        }


            

    }


    public  List<ResponseBean> findClasses(File directory, String packageName) throws ClassNotFoundException {

        List<ResponseBean> listofbean = new ArrayList<>();
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                ResponseBean bean = new ResponseBean();
                System.out.println("Directory name ::  " + file.getName());
                    bean.setPackageName(file.getName());
                    File[] listofSubfiles = file.listFiles();
                    List<String> list = new ArrayList<String>();
                    for (File subfile : listofSubfiles) {
                        System.out.println("file name :::  " + subfile.getName().split(".class")[0]);
                        list.add(subfile.getName().split(".class")[0]);
                        bean.setClassName(list);
                    }
                    listofbean.add(bean);
            }

        }
        return listofbean;
    }
}