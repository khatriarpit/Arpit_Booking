
package com.emxcel.dms.core.business.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.modules.email.Email;
import com.emxcel.dms.core.model.common.EmailLog;
import com.emxcel.dms.core.model.superadmin.TenantPackage;
import com.emxcel.dms.core.model.user.User;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * @author emxcelsolution
 *
 */
public class CommonUtil {

	public static final boolean REMOTE = false;

	public static final String LOCATION = ((REMOTE) ? "C:/deployment/images/"
			: "/home/amd-08/emxcelsolutions/");

	public static final String PROJECT_LOCATION = ((REMOTE) ? "C:/deployment/images/"
			: "/home/emxcelsolution/IdeaProjects/emxcel-dms/dms/dms-portal/src/main/webapp/WEB-INF/tiles/view/");
	
	public static final String HTML = CommonUtil.PROJECT_LOCATION + "pdf-html/invoice-pdf.html";

    public static final String DEST = CommonUtil.LOCATION + "pdfHtml/";
	
	/**
	 * Created By : Jimit Patel. Use: To check the user login
	 * 
	 * @return boolean.
	 */
	public static boolean checkUserLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			return true;
		} else {
			return false;
		}
	}

	public static String getUsernameByAuthentication() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			return auth.getName();
		} else {
			return null;
		}
	}

	/**
	 * Created By: Naresh Banda. Date: 30-09-2016 Use : get Date By SqlDate
	 * 
	 * @param sqlDate.
	 * @return String
	 */
	public static String getDateBySqlDate(final java.sql.Date sqlDate) {
		SimpleDateFormat viewFormat = new SimpleDateFormat(Constants.PORTAL_DATE_FORMAT);
		String sqlDateFormat = viewFormat.format(sqlDate);
		return sqlDateFormat;
	}

	/**
	 * @param name.
	 * @return String
	 */
	public static final String getCancelId(final String name) {
		String sortName = "";
		// int total = list1.size() + 1;
		Date d1 = new Date();
		DateFormat format = new SimpleDateFormat(Constants.PORTAL_DATE_TIME_FORMAT);
		String todayDate = format.format(d1);
		sortName = name.toUpperCase();
		String date = todayDate.substring(0, 2);
		String month = todayDate.substring(2, 4);
		String time = todayDate.substring(4, 8);
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
		sortName = sortName + date + month + time;
		return sortName;
	}

	public static String getFileName(MultipartFile imgFile, String path) {
		String fileName = "";
		if (imgFile.getOriginalFilename() != null && !imgFile.getOriginalFilename().equals("")) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			MultipartFile multipartFile = imgFile;
			try {
				FileCopyUtils.copy(imgFile.getBytes(), new File(path + File.separator + imgFile.getOriginalFilename()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileName = multipartFile.getOriginalFilename();
		}
		return fileName;
	}

	/**
	 * @param tokenID.
	 * @param content.
	 */
	public static void getTokenByContactNo(final String tokenID, final String content, String type) {
		final String serverKey = type.equals("feedApp") ? "AAAA5wHmVIw:APA91bFTlKlVbKlgd-VAZ4EcjCmbJsAxJCuuAi9S4eXZ7LjClg3v1WuL0DKeyByCvVSaB9hMs_iiqNLT3oY79PajLCLDZ0tPUgYwbmLW1Kvk2JeiPPCUEPedFA6csQyBE6tRM8o5bXHA" : "AAAAjdm5U7g:APA91bHIPL6pBbMrdLJAE1eL9opL4ZLJXdAK9CSYqkfiLtxk1R3bzO_uC721lSVGLPDhtnJHgXjzrqros7-7s6bLEabNKm_x7ynoSqtF3fqj925CzzJiwjHX0b1K5o7pdFEyZ2Ko1tFG";
		Thread t = new Thread() {
			public void run() {
				try {
					Sender sender = new FCMSender(serverKey);
					Message message = new Message.Builder().collapseKey("message").timeToLive(3).delayWhileIdle(true)
							.addData("message", content).build();
					sender.send(message, tokenID, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		try {
			t.join();
		} catch (InterruptedException iex) {
			iex.printStackTrace();
		}
	}

	/**
	 * Created By : Jimit Patel. Use: To check the user login
	 * 
	 * @return boolean.
	 */
	public static User getUser(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			return user;
		} else {
			return null;
		}
	}

	/**
	 * @return
	 */
	public static java.sql.Date getSysdate() {
		Calendar currenttime = Calendar.getInstance();
		java.sql.Date today = new java.sql.Date((currenttime.getTime()).getTime());
		return today;
	}

	/**
	 * @param utilDate
	 * @return java.sql.Date
	 */
	public static java.sql.Date getSqlDate(java.util.Date utilDate) {
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		return sqlDate;
	}

	/**
	 * @return
	 */
	public static String getPasswordFormat() {
		StringBuilder sb = new StringBuilder();
		int n = 8; // how many characters in password
		String set = "aujkdnxmlksyksggdtrrqqqooqopbbxhxhbnc"; // characters to
																// choose from
		for (int i = 0; i < n; i++) {
			int k = i;
			k = k + i + 5;
			// random number between 0 and set.length()-1 inklusive
			sb.append(set.charAt(k));
		}
		String result = sb.toString();
		return result;
	}

	public static Map<String, Object> getEmail(String subject, String from, String fromAddress, String toAddress,
			String template, Map<String, String> map, Long userID) {
		Map<String, Object> mapEmail = new HashMap<String, Object>();
		Email email = new Email();
		email.setSubject(subject);
		email.setFrom(from);
		email.setFromEmail(fromAddress);
		email.setTo(toAddress);
		email.setTemplateName(template);
		email.setTemplateTokens(map);
		email.setUserID(userID);
		mapEmail.put("email", email);

		EmailLog emailLog = new EmailLog();
		emailLog.setUserID(userID);
		emailLog.setSentTo(toAddress);
		emailLog.setType(subject);
		Timestamp datetime = new Timestamp(System.currentTimeMillis());
		emailLog.setDatetime(datetime);
		emailLog.setSent(false);
		mapEmail.put("emailLog", emailLog);
		return mapEmail;
	}

	public static Map<String, Object> checkExistingCarAndDriver(List<TenantPackage> tenantPackageList,
			String[] carLength, String[] driverLength, String carCommaIndex, String driverCommaIndex) {
		// entire tenant list existing car and driver list
		String[] carErrorIndexCount = carCommaIndex.split(",");
		String[] driverErrorIndexCount = driverCommaIndex.split(",");
		Map<String, Object> model = new HashMap<String, Object>();
		if (tenantPackageList != null && tenantPackageList.size() > 0) {
			for (TenantPackage tenantPackage : tenantPackageList) {
				if (tenantPackage.getCarList() != null && !tenantPackage.getCarList().equals("")) {
					String[] carNoLength = tenantPackage.getCarList().split(",");
					String carnoString = "";
					for (String carNo1 : carNoLength) {
						int carIndex = 0;
						for (String carno2 : carLength) {
							if (carNo1.equals(carno2)) {
								if (!carnoString.equals("")) {
									carnoString += ",";
								}
								carnoString += carErrorIndexCount[carIndex];
							}
							carIndex++;
						}
					}
					if (!carnoString.equals("")) {
						model.put("error", "true");
						model.put("type", "car");
						model.put("car-error", carnoString);
					}
				}
				if (tenantPackage.getDriverList() != null && !tenantPackage.getDriverList().equals("")) {
					String[] driverLicLength = tenantPackage.getDriverList().split(",");
					String driverString = "";
					for (String driverLicNo : driverLicLength) {
						int driverIndex = 0;
						for (String driverLic : driverLength) {
							if (driverLicNo.equals(driverLic)) {
								if (!driverString.equals("")) {
									driverString += ",";
								}
								driverString += driverErrorIndexCount[driverIndex];
							}
							driverIndex++;
						}
					}
					if (!driverString.equals("")) {
						model.put("error", "true");
						model.put("type", "driver");
						model.put("driver-error", driverString);
					}
				}
			}
		}
		String error = (String) model.get("error");
		if (error == null) {
			model.put("error", "false");
		}
		return model;
	}

	public static String convertTimestampToDate(Timestamp timestamp) {
		java.util.Date date = new java.util.Date(timestamp.getTime());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(simpleDateFormat.format(date));
		return simpleDateFormat.format(date);
	}

	public static String convertTimestampToTimeOnly(Timestamp timestamp) {
		java.util.Date date = new java.util.Date(timestamp.getTime());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		System.out.println(simpleDateFormat.format(date));
		return simpleDateFormat.format(date);
	}

	public static Timestamp convertStringToTimestamp(String str_date) {
		try {
			SimpleDateFormat e = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			java.util.Date date = e.parse(str_date);
			Timestamp timeStampDate = new Timestamp(date.getTime());
			return timeStampDate;
		} catch (ParseException var4) {
			System.out.println("Exception :" + var4);
			return null;
		}
	}

	public static String convertTimestampToDateInString(Timestamp timestamp) {
		java.util.Date date = new java.util.Date(timestamp.getTime());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		System.out.println(simpleDateFormat.format(date));
		return simpleDateFormat.format(date);
	}

	/**
	 * Created By: Naresh Banda Date : 31-01-2016 Use: get status by statusID
	 * 
	 * @param statusID
	 * @return String
	 */
	public static String getStatusByStatusID(int statusID) {
		String status = "";
		if (statusID == 1) {
			status = Constants.TRIP_STATUS_PENDING;
		} else if (statusID == 2) {
			status = Constants.TRIP_STATUS_LIVE;
		} else if (statusID == 3) {
			status = Constants.TRIP_STATUS_END;
		} else if (statusID == 4) {
			status = Constants.TRIP_STATUS_CANCEL;
		} else if (statusID == 5) {
			status = Constants.TRIP_STATUS_DR_CANCEL;
		} else if (statusID == 6) {
			status = Constants.TRIP_STATUS_CE_CANCEL;
		}else if (statusID == 7) {
			status = Constants.TRIP_UNASSIGN;
		}
		return status;
	}

	/**
	 * Created By: Naresh Banda Date : 31-01-2016 Use: get status id by status
	 * 
	 * @param status
	 * @return int
	 */
	public static int getStatusIDByStatus(String status) {
		int statusID = 0;
		if (status.equals(Constants.TRIP_STATUS_PENDING)) {
			statusID = 1;
		} else if (status.equals(Constants.TRIP_STATUS_LIVE)) {
			statusID = 2;
		} else if (status.equals(Constants.TRIP_STATUS_END)) {
			statusID = 3;
		} else if (status.equals(Constants.TRIP_STATUS_CANCEL)) {
			statusID = 4;
		} else if (status.equals(Constants.TRIP_STATUS_DR_CANCEL)) {
			statusID = 5;
		} else if (status.equals(Constants.TRIP_STATUS_CE_CANCEL)) {
			statusID = 6;
		} else if (status.equals(Constants.TRIP_UNASSIGN)) {
			statusID = 7;
		}
		return statusID;
	}

	public static Timestamp convertToTimestampDBformate(String str_date) {
		try {
			SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date = e.parse(str_date);
			Timestamp timeStampDate = new Timestamp(date.getTime());
			return timeStampDate;
		} catch (ParseException var4) {
			System.out.println("Exception :" + var4);
			return null;
		}
	}

    public static void createPdf(String file, Map<String,Object> model, HttpServletResponse response) throws IOException, DocumentException {
        File fileName = new File(HTML);
        FileInputStream fis = null;
        String text = "";
        File image;
        try {
            fis = new FileInputStream(fileName);
            int content;
            while ((content = fis.read()) != -1) {
                text += (char) content;
            }
            text = text.replace("${imgFile}", (String)model.get("imgFile"));
            text = text.replace("${companyName}", (String)model.get("companyName"));
			text = text.replace("${address1}", (String)model.get("address1"));
			text = text.replace("${address2}", (String)model.get("address2"));
			text = text.replace("${address3}", (String)model.get("address3"));
			text = text.replace("${emailid}", (String)model.get("emailid"));
			text = text.replace("${phoneNo}", ""+(Long)model.get("phoneNo"));
			text = text.replace("${landlineNo}", ""+(Long)model.get("landlineNo"));
			text = text.replace("${note}", (String)model.get("Note"));
			text = text.replace("${termsConditions}", (String)model.get("TermsConditions"));
			text = text.replace("${ServiceTax}",""+(Double)model.get("ServiceTax"));
            text = text.replace("${InvoiceNumber}",(String)model.get("InvoiceNumber"));
            text = text.replace("${PassengerName}",(String)model.get("PassengerName"));
            text = text.replace("${DateOfTravel}",""+(Date)model.get("DateOfTravel"));
            text = text.replace("${PlaceOfTour}",(String)model.get("PlaceOfTour"));
            text = text.replace("${KM}",""+(Float)model.get("KM"));
            text = text.replace("${RatePerKM}",""+(Integer)model.get("RatePerKM"));
            text = text.replace("${Amount}",""+(Double)model.get("Amount"));
            text = text.replace("${TollTax}","");
            text = text.replace("${DriverAllowance}",""+(Integer)model.get("DriverAllowance"));
            text = text.replace("${finalTotalInWord}",""+(String)model.get("finalTotalInWord"));
			text = text.replace("${finalTotal}", ""+(Double)model.get("finalTotal"));
			text = text.replace("${totalAmountDue}", ""+(Integer)model.get("totalAmountDue"));
			text = text.replace("${dateOfInvoice}", ""+(Date)model.get("dateOfInvoice"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(DEST));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8)));
        // step 5
        document.close();
    }

	
	/*public final String getVoucherNo(final ClientModel clientModel,
									 java.sql.Date sqlStartDate) {
		String sortName = "";
		if (clientModel.getDriver() != null) {
			String fname = clientModel.getDriver().getFirstName();
			String mname = clientModel.getDriver().getMiddleName();
			String lname = clientModel.getDriver()`.getLastName();
			String name = fname.charAt(0) + "" + mname.charAt(0) + "" + lname.charAt(0);
			List<ClientDetail> list = clientDetailService.getTotalTrips(clientDetailBean.getDirId(),
					sqlStartDate);
			int count = list.size() + 1;
			List<ClientDetail> list1 = clientDetailService.getwholeTrips(clientDetailBean.getDirId());
			int total = list1.size() + 1;
			String pattern = "ddMM";
			Date d1 = new Date();
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			String todayDate = format.format(d1);
			sortName = name.toUpperCase() + (count < 10 ? "0" : "");
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
	}*/

	private static ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {
		InputStream inputStream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			inputStream = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			//logger.error(Constants.EXCEPTION_THROW, e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					//logger.error(Constants.EXCEPTION_THROW, e);
				}
			}
		}
		return baos;
	}

	public static final String[] units = {
            "", "one", "two", "three", "four", "five", "six", "seven",
            "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen",
            "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    public static final String[] tens = {
            "","","twenty","thirty","forty","fifty","sixty","seventy","eighty","ninety"
    };

    public static String convert(final int n) {
        if (n < 0) {
            return "minus " + convert(-n);
        }
        if (n < 20) {
            return units[n];
        }
        if (n < 100) {
            return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
        }
        if (n < 1000) {
            return units[n / 100] + " hundred" + ((n % 100 != 0) ? " " : "") + convert(n % 100);
        }
        if (n < 1000000) {
            return convert(n / 1000) + " thousand" + ((n % 1000 != 0) ? " " : "") + convert(n % 1000);
        }
        if (n < 1000000000) {
            return convert(n / 1000000) + " million" + ((n % 1000000 != 0) ? " " : "") + convert(n % 1000000);
        }
        return convert(n / 1000000000) + " billion"  + ((n % 1000000000 != 0) ? " " : "") + convert(n % 1000000000);
    }

	/**
	 * Created By: Pratik Soni
	 * Used For: Creating Ticketid
	 * @param tenantId - *Tenant id*
	 * @param initials - *Department initials*
	 * @param id *Ticket Sequence number for that tenant*
	 * @return *Ticket ID*
	 */
	public static String generateTicketId(String tenantId, String initials,int id) {

		String ticketId = tenantId+"_"+initials+"_"+id;
		return ticketId;
	}



	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	public static List findClasses(File directory, String packageName) throws ClassNotFoundException {
		List classes = new ArrayList();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

}