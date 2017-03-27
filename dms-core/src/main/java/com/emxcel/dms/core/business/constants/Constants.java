package com.emxcel.dms.core.business.constants;

import java.util.Currency;
import java.util.Locale;

public class Constants {

	public final static String COMPANY_NAME = "Emxcel Corporate Logic & Solutions";
	public final static String COMPANY_EMAIL_ID = "info.emxcelsolutions@gmail.com";
	public final static String TEST_ENVIRONMENT = "TEST";
	public final static String PRODUCTION_ENVIRONMENT = "PROD";

	public static final String ALL_REGIONS = "*";

	public final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public final static String PORTAL_ONLY_DATE_FORMAT = "dd/MM/yyyy";
	public final static String PORTAL_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	public final static String PORTAL_UNDATE_FORMAT = "MM/dd/yyyy";

	public final static String PORTAL_DATE_TIME_FORMAT = "ddMMHHmm";
	public final static String PORTAL_TIME_FORMAT = "HH:mm:ss";
	public final static String DEFAULT_DATE_FORMAT_YEAR = "yyyy";
	public final static String DEFAULT_LANGUAGE = "en";

	public final static String EMAIL_CONFIG = "EMAIL_CONFIG";
	
	public final static String WRONG = " Something Went Wrong !!!";

	public final static String UNDERSCORE = "_";
	public final static String SLASH = "/";
	
	public final static boolean TRUE = true;
	public final static boolean FALSE = false;

	public final static String TRIP_STATUS_LIVE = "Live";
	public final static String TRIP_STATUS_PENDING = "Pending";
	public final static String TRIP_STATUS_END = "End";
	public final static String TRIP_STATUS_CANCEL = "Cancel";
	public final static String TRIP_STATUS_DR_CANCEL ="Dr-cancel";
	public final static String TRIP_STATUS_CE_CANCEL="Ce_cancel";
	public final static String TRIP_UNASSIGN="Unassign";

	public final static String READ = "read";
	public final static String UNREAD = "unread";

	public final static Locale DEFAULT_LOCALE = Locale.US;
	public final static Currency DEFAULT_CURRENCY = Currency.getInstance(Locale.US);

	public final static String NEW_TRIP_MESSAGE = "A new trip has been generated. Trip ID: ";
	public final static String CURRENT_PASSWORD_MESSAGE = "Your Current Password is: ";

	public final static String APIKEY = "Aa33875b7dbbe84c4c14877361bdc1610";

	public static final String STATUSMODEL_OLD = "old";
	public static final String STATUSMODEL_NEW = "new";

	public static final String USER_ROLE_SUPERADMIN = "ROLE_SUPERADMIN";
	public static final String USER_ROLE_ADMIN = "ROLE_ADMIN";
	public static final String USER_ROLE_USER = "ROLE_USER";
	public static final String USER_ROLE_DRIVER = "ROLE_DRIVER";
	public static final String USER_ROLE_ACCOUNT = "ROLE_ACCOUNT";

	//Email Sent
	public static final String SUBJECT_FORGETPASS = "Forget Password";
	public static final String CUSTOM_TEMPLATE = "email_template_custom.ftl";
	public static final String SUBJECT_RENEW_PACKAGE = "Renew Package";
	public static final String SUBJECT_INVOICE_PDF = "Invoice Document";

	//Pre Booking
	public static final Integer REQUEST_PENDING=0;
	public static final Integer REQUEST_CONFIRMED=1;
	public static final Integer REQUEST_CANCELED =2;
	

	public static final Integer ACTIVE=0;
	public static final Integer DEACTIVE=1;
	public static final String MESSAGE = "message";
	public static final String EXCEPTION_THROW = "error";
	public static final String REDIRECT = "redirect:";
	public static final String TRUE_AS_STRING = "true";
	public static final String FALSE_AS_STRING = "false";
	
	public static final String TRIP = "trip";
	public static final String START_TRIP = "start_trip";
	public static final String END_TRIP = "end_trip";
	public static final String AUTO = "Auto";
	public static final String COMPANY = "Company";
	public static final String MINIMUM_KM = "Minimum Km";
	public static final String HOURS_AND_KM = "Hours And Km";
	public static final String SOURCE_AND_DESTINATION = "Source And Destination";
	public static final String USER_LIMIT = "userLimit";
	public static final String CAR_LIMIT = "carLimit";
	public static final String DRIVER_LIMIT = "driverLimit";

	public static final String TEMPLATE_FORGETPASS = "email_template_custom.ftl";

	public static final String TICKET_MANAGER_ROLE = "ROLE_TICKET_HANDLER";
	public static final String TICKET_ACCOUNTANT_ROLE = "ROLE_ACCOUNTANT";
	public static final String TICKET_SALE_ROLE = "ROLE_SALE";

	public static final int OPEN_TICKET = 1;
	public static final int CLOSE_TICKET = 0;
	public static final int RESOLVED_TICKET = 3;
	public static final String TICKET_DEFAULT_PRIORITY = "Medium";
	public static final String SECRETKEY ="742d354a30ad2aacc21440f507b35f42feccd9bb";

	public static final String MODEL_PATH="dms-core-model/target/classes/com/emxcel/dms/core/model";

}