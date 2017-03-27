package com.emxcel.dms.core.business.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.guest.Guest;
import com.emxcel.dms.core.model.user.User;

public class SMSSend {

	/**
	 * @param c.
	 * @throws Exception.
	 */
	/*
	 * public static void sendOtp(Client c) throws Exception { String message =
	 * "DKTT .. Your trip is confirmed from " + c.getPickUpPlace() + " to " +
	 * c.getPlaceName() + ".";
	 * sendOtpMSG(Long.valueOf(c.getGuest().getContactNo()), message); }
	 */

	/**
	 * @param d.
	 * @param c.
	 * @throws Exception.
	 */
	/*
	 * public static void sendOtp(Driver d, Client c) throws Exception { String
	 * message =
	 * "DKTT .. New trip has been generated. Details as follows: Pick Up Place: "
	 * + c.getPickUpPlace() + " , Pick Up Time: " + c.getPickUpTime() +
	 * ", Destination: " + c.getPlaceName() + ".";
	 * sendOtpMSG(Long.valueOf(c.getGuest().getContactNo()), message); }
	 */

	/**
	 * @param driverDetail.
	 * @param otp.
	 * @throws Exception.
	 */
	public static void sendOtp(Driver driverDetail, int otp) throws Exception {
		String message = "DKTT .. OTP is " + otp;
		sendSms(Long.valueOf(driverDetail.getContactNo()), message);
	}

	/**
	 * @param userDetail.
	 * @param otp.
	 * @throws Exception.
	 */
	public static void sendOtp(User userDetail, int otp) throws Exception {
		String message = "DKTT .. OTP is " + otp;
		sendSms(Long.valueOf(userDetail.getContactNo().toString()), message);
	}

	/**
	 * @param phn1.
	 * @param tripid.
	 * @param temp.
	 * @throws Exception.
	 */
	public static void sendOtp(Long phn1, String tripid, String temp) throws Exception {
		String message = "test";
		if (temp.equalsIgnoreCase("old")) {
			message = "DKTT .. Your trip with ID " + tripid + " has been cancelled.";
		}

		if (temp.equalsIgnoreCase("new")) {
			message = "DKTT .. A new trip has been generated. Trip ID: " + tripid;
		}
		sendOtpMSG(phn1, message);
	}

	/**
	 * @param contact.
	 * @param message.
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	public static void sendSms(Long contact, String message) {
		sendOtpMSG(contact, message);
	}

	/**
	 * @param guestDetail.
	 * @throws Exception.
	 */
	public static void sendSms(Guest guestDetail, int otp) throws Exception {
		if (guestDetail != null) {
			String message = "DKTT .. OTP is " + otp;
			sendOtpMSG(Long.valueOf(guestDetail.getContactNo()), message);
		}
	}

	/**
	 * @param mobile.
	 * @param message.
	 */
	private static void sendOtpMSG(final Long mobile, final String message) {
		String apikey = "Aa33875b7dbbe84c4c14877361bdc1610";
		String senderid = "EMXCEL";
		URLConnection myURLConnection = null;
		URL myURL = null;
		BufferedReader reader = null;
		String encoded_message = URLEncoder.encode(message);
		String mainUrl = "http://sms.alphacomputers.biz/api/v3/?method=sms&";
		StringBuilder sbPostData = new StringBuilder(mainUrl);
		sbPostData.append("api_key=" + apikey);
		sbPostData.append("&to=" + mobile);
		sbPostData.append("&sender=" + senderid);
		sbPostData.append("&message=" + encoded_message);
		mainUrl = sbPostData.toString();
		String response = "";
		try {
			myURL = new URL(mainUrl);
			myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
			while ((response = reader.readLine()) != null) {
				response = reader.readLine();
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void emergencyMSG(String driverContact, String driverName, String carNo, String startendUrl,
			String contacts) {
		String message = "Driver Name :" + driverName + ", Driver Contact :" + driverContact + ", Car Number :" + carNo
				+ ", Start-End Url :" + startendUrl;
		sendSms(Long.parseLong(contacts), message);
	}
}