package com.emxcel.dms.portal.constants;

public class UrlConstants {
	// PERMITIONS 
	public static final String PERMISSION_LIST = "/permission-list";
	public static final String PERMISSION = "/permission";
	public static final String DELETE_USER_PERMISSION = "/delete-permission";
	public static final String SAVE_PERMISSION = "/save_permission";
	// Error
	public static final String ERROR = "/error";

	// Image Servlet
	public static final String FETCH_IMAGE = "/fetch-image";

	// Master Links
	public static final String GET_CITY = "/getCity";
	public static final String GET_STATE = "/getState";

	// Term And Conditions
	public static final String TERM_CONDITIONS = "/terms-and-conditions";
	
	//
	public static final String USER_ROLE = "/userrole";
	public static final String SAVE_USER_ROLE = "/saveuserrole";

	// Root Pages
	public static final String ROOT = "/";
	public static final String LOGIN = "/login";
	public static final String LOGOUT = "/logout";
	public static final String ADMIN_LOGIN = "/admin";
	public static final String INDEX = "/index";
	public static final String USER_VERIFICATION = "/user-verification";
	public static final String PASSWORD_VERIFICATION = "/password-verification";
	public static final String DASHBOARD = "/dashboard";

	// Super Admin
	public static final String TENANT_LIST = "/tenant-list";
	public static final String TENANT = "/tenant";
	public static final String SAVE_TENANT = "/save-tenant";
	public static final String DELETE_TENANT = "/delete-tenant";
	public static final String RESEND_EMAIL = "/resend-email";
	public static final String SELL_ALL_SELLER = "/sell-all-seller";
	public static final String SELL_ALL_SELLER_LIST = "/sell-all-seller-list";

	public static final String SAVE_SELLER = "/saveseller";
	public static final String EDIT_SELLER = "/editseller";	

// Destination Master
	public static final String DESTINATION_MASTER = "/destination-master";
	public static final String SAVE_DESTINATION = "/save-destination";
	public static final String DELETE_DESTINATION = "/delete-destination";
	public static final String LIST_IINVOICE_PACKAGE = "/list-invoice-package";
	public static final String SAVE_INVOICE_PACKAGE = "/save-invoice-package";
	public static final String INVOICE_PACKAGE = "/invoice-package";
	public static final String DELETE_INVOICE_PACKAGE = "/delet-invoice-package";

	// Check Colour
	public static final String CHANGE_COLOR = "/change-color";

	// USER LOGS
	public static final String USER_LOGS_ADMIN = "/user-logs-admin";
	public static final String USER_LOGS = "/user-logs";
	public static final String USER_LOGS_SUPERADMIN = "/user-logs-superadmin";

	// superadmin
	public static final String PACKAGE_LIST = "/package-list";
	public static final String PACKAGE = "/package";
	public static final String DELETE_PACKAGE = "/delete-package";
	public static final String SAVE_PACKAGE = "/save-package";
	public static final String EDIT_PACKAGE = "/edit-package";
	public static final String RATE_OF_CONTRACT = "/rate-contract";
	public static final String LIST_RATE_OF_CONTRACT = "/rate-contract-list";
	public static final String SAVE_RATE_OF_CONTRACT = "/rate-contract-save";
	public static final String DELETE_RATE_OF_CONTRACT = "/rate-contract-delete";
	public static final String GET_PACKAGE = "/get-package";

	public static final String PACKAGE_CREATE = "/create-package";
	public static final String SAVE_CREATE_PACKAGE = "/save-created-package";
	public static final String CREATE_PACKAGE_LIST = "/create-package-list";
	public static final String GET_CREATE_PACKAGE = "/get-create-package";

	// Renew Package
	public static final String RENEW_PACKAGE = "/renew-package";
	public static final String SAVE_RENEW_PACKAGE = "/save-renew-package";

	public static final String DELETE_PACKAGE_CREATE = "/delete-package-create";
	public static final String CITY_CODE = "/getcitycode";

	// Pending Verification
	public static final String PENDING_VERIFICATION_LIST = "/pending-verification-list";
	public static final String PENDING_VERIFICATION_APPROVED = "/pending-verification-approve";
	public static final String PENDING_VERIFICATION_PAGE = "/pending-verification";
	public static final String PENDING_VERIFICATION_DELETE = "/pending-verification-delete";

	// User
	public static final String FORGOTTEN_PASSWORD_PAGE = "/forgot-password";
	public static final String FORGOTTEN_PASSWORD = "/frgt-password";
	public static final String UPDATE_PASSWORD = "/update-password";
	public static final String CHANGE_PASSWORD = "/change-password";
	public static final String UPDATE_PASSWORD_FOR_DE = "/changepassword-forde";
	public static final String USER = "/user";
	public static final String SAVE_USER = "/save-user";
	public static final String DELETE_USER = "/delete-user";
	public static final String USER_LIST = "/user-list";
	public static final String RESENDPASSWORD = "/resend-password";
	public static final String CHANGEPASSWORDFORDE_PAGE = "/change-password-forde";
	public static final String CHANGEPASSWORDFORDE_ACCEPTED = "/change-password-accepted";
	public static final String USER_GROUP="/user-group";
	public static final String SAVE_USER_GROUP="save-user-group";

	// Car Type
	public static final String ADD_CARTYPE = "/cartype";
	public static final String CARTYPE_LIST = "/cartype-list";
	public static final String SAVE_CARTYPE = "/save-cartype";
	public static final String DELETE_CARTYPE = "/delete-cartype";
	public static final String EDIT_CARTYPE = "/edit-cartype";

	// Driver(Jimit)
	public static final String DRIVER_LIST = "/driver-list";
	public static final String DRIVER = "/driver";
	public static final String DELETE_DRIVER = "/delete-driver";
	public static final String SAVE_DRIVER = "/save-driver";
	public static final String REMOVE_CAR_DRIVER_MAPPING = "/remove-car-driver-mapping";
	public static final String LIST_DRIVER_LIST_AJAX = "/get-driver-detail-list";

	// Change Driver(Jimit)
	public static final String DRIVER_CHANGE = "/driver-change";
	public static final String CHANGE_DRIVER = "/change-driver";
	public static final String SEARCH_BY_TRIP = "/search-trip";
	public static final String GET_DRIVER_BY_TRIP = "/get-driver-by-trip";

	// Car Name
	public static final String ADD_CARNAME = "/carname";
	public static final String GET_CARNAME = "/get-carname";
	public static final String SAVE_CARNAME = "/save-carname";
	public static final String DELETE_CARNAME = "/delete-carname";
	public static final String CARNAME_LIST = "/carname-list";

	// Car
	public static final String CAR = "/car";
	public static final String SAVE_CAR = "/save-car";
	public static final String DELETE_CAR = "/delete-car";
	public static final String LIST_CAR = "/car-list";
	public static final String LIST_CAR_LIST_AJAX = "/get-car-detail-list";
    public static final String LIST_TENANT_PACKAGE_CAR_OR_DRIVER_LIST_AJAX = "/get-tenant-package-car-detail-list";


	// Car Driver
	public static final String CAR_DRIVER = "/car-driver";
	public static final String UPDATE_CAR_DRIVER_MAPPING = "/update-driver";
	public static final String GET_CARNO = "/get-car-no";
	public static final String CAR_CHECK_COLOR = "/check-car-color";
	public static final String CAR_DRIVER_MAPPING = "/car-driver-mapping";
	public static final String CHECK_CAR_DRIVER_MAPPING = "/check-car-driver-mapping";

	// Book Car
	public static final String SEARCH_CAR = "/search-car";
	public static final String SEARCH_CARS_BOOKED_AVAILABLE = "/search-booked-available-cars";
	public static final String MANUAL_INVOICE = "/manual-invoice";
	public static final String SAVE_MANUAL_INVOICE ="save-manual-invoice";
	public static final String MANUAL_TAX = "/manual-tax-amount";
	public static final String GENERATE_INVOICE ="/downloadPDF";
	public static final String SHOW_MANUAL_INVOICE_PDF = "/show-pdf";
	public static final String SEND_MANUAL_INVOICE_PDF = "/send-pdf";
	public static final String CUSTOMER_CANCEL_REQUEST = "/customercancelrequest";
	public static final String CUSTOMER_CANCEL_APPROVE = "/customercancelapprove";
	public static final String CUSTOMER_CANCEL_REJECT = "/customercancelreject";


	// BOOKED CAR STATUS
	public static final String BOOKED_STATUS = "/booked-car-status";
	public static final String SAVE_BOOKEDCARSTATUS = "/save-booked-car-status";
	public static final String DELETE_BOOKEDCARSTATUS = "/delete-booked-car-status";
	public static final String LIST_BOOKEDCARS = "/booked-car-list";

	// Client Book Car
	public static final String BOOK_CAR_LIST = "/available-car";
	public static final String CLIENT_PAGE = "/client";
	public static final String SAVE_CLIENT = "/save-client";
	public static final String UPDATE_CLIENT = "/update-client";
	public static final String DOWNLOAD_PDF = "/download-pdf";
	public static final String GETCOMPANY_LIST = "/get_Company_list";
	public static final String SOURCEPLACE_LIST = "/get_SourcePlace_list";
	public static final String DESTINATIONPLACE_LIST = "/get_DestinationPlace_list";
	public static final String INVOICE_TAX_LIST = "/gettaxList";
	public static final String CANCEL_REQUEST_LIST = "/cancelList";
	public static final String APPROVE_DRIVER_REQUEST = "/approveDriverRequest";
	public static final String REJECT_DRIVER_REQUEST = "/rejectDriverRequest";
	public static final String DRIVER_REQUEST_OPERATION = "/driverrequestoperation";
	public static final String GET_GUEST_DETAIL = "/getguestDetail";
	public static final String GET_CONTACT_LIST = "/getcontactlist";

	// feedback
	public static final String FEEDBACK = "/feedback";
	public static final String SAVE_FEEDBACK = "/save-feedback";
	public static final String ENTER_FEEDBACK = "/enter-feedback";
	public static final String CHECK_FEEDBACK_BY_TRIP_ID = "/check-feedback-by-trip-id";
	public static final String FEEDBACK_REPORT = "/feedback-report";
	public static final String FEEDBACK_LIST = "/feedback-list";
	public static final String AJAX_FEEDBACK_LIST = "/feedbacklist";


	// COMPANY MASTER
	public static final String ADD_COMPANY_MASTER = "/company-master";
	public static final String SAVE_COMAPANY_MASTER = "/save_company-master";
	public static final String DELETE_COMAPANY_MASTER = "/delete_company-master";
	public static final String LIST_COMAPANY_MASTER = "/list_company-master";
	public static final String COMAPANY_TYPE = "/company-type";
	public static final String SAVE_COMPANY_TYPE = "/save-company-type";
	public static final String UPDATE_COMPANY_TYPE = "/update-company-type";
	public static final String COMPANY_TYPE_LIST = "/company-type-list";

	// TAX SLAB
	public static final String TAX_SLAB = "/tax-slab";
	public static final String SAVE_TAXSLAB = "/save-tax-slab";
	public static final String DELETE_TAXSLAB = "/delete-tax-slab";

	// TAX CATEGORY
	public static final String TAX_CATEGORY = "/tax-category";
	public static final String SAVE_TAXCATEGORY = "/save-tax-category";
	public static final String DELETE_TAXCATEGORY = "/delete-tax-category";

	// Canceled Trip
	public static final String CANCELED_TRIP = "/canceled-trip";
	public static final String INACTIVE_PACKAGE_MASTER = "/inactive-package-master";

	// WebService for LoginRestController
	public static final String CHECK_CAR_NO = "/login/app/check-car-no";
	public static final String CHECK_CAR_NO_OTP = "/login/app/check-car-no-otp";
	public static final String GET_TRIPS_CAR_NO = "/login/app/get-trips-car-no";
	public static final String SET_START_TRIP_STATUS = "/login/app/set-start-trip-status";
	public static final String SET_END_TRIP_STATUS = "/login/app/set-end-trip-status";
	public static final String SAVE_GEO_DATA = "/login/app/save-geo-data";
	public static final String GET_GEO_DATA = "/login/app/get-geo-data";
	public static final String SAVE_EXPENSE = "/login/app/save-expense";
	public static final String CANCEL_REQUEST_DE = "/login/app/cancel-request-de";
	public static final String SAVE_USER_PROFILE = "/login/app/saveuserprofile";
	public static final String RATE_USER_TRIPS = "/login/app/rateusertrips";
	public static final String SAVE_CUSTOMER_FEEDBACK ="/login/app/savefeedback";

	// WebService for FeedAppRestController

	public static final String FEED_USER_REGISTER = "/login/app/register";
	public static final String FEED_USER_LOGIN_CHECK = "/login/app/logincheck";
	public static final String FEED_USER_FORGET_PASSWORD = "/login/app/forgetpassword";
	public static final String FEED_USER_CHANGE_PASSWORD = "/login/app/changepassword";
	public static final String FEED_USER_SEARCH_CAR = "/login/app/searchcar";
	public static final String FEED_USER_SEARCH_DRIVER = "/login/app/searchdriver";
	public static final String FEED_USER_PAST_TRIPS = "/login/app/pasttrips";
	public static final String FEED_USER_DRIVER_BY_TANENT = "/login/app/driver_ByTanentID";
	public static final String FEED_USER_CAR_BY_TANENT = "/login/app/car_ByTanentID";
	public static final String FEED_USER_PENDING_TRIP = "/login/app/pendingtrip";
	public static final String FEED_USER_PENDING_CANCELED_MYTRIPS_OTP = "/login/app/pending-cancel-mytrips-otp";
	public static final String FEDD_USER_PENDING_CANCELED_MYTRIPS ="/login/app/pending-cancel-mytrips";
	public static final String FEED_USER_GET_CITY_NAME ="/login/app/get-city-name";
	public static final String FEED_USER_GET_TENANT_FROM_CITY = "/login/app/get-tenant-from-city";
	public static final String FEED_USER_BOOK_CAR_TENANT= "/login/app/book-car-tenant";
	public static final String PAST_RIDE ="/login/app/past-ride";
	public static final String UPCOMING_RIDES ="/login/app/upcoming-ride";
	public static final String CANCLE_REQUEST ="/login/app/cancle-request";
	public static final String FEED_USER_BOOK_CAR_TENANT_CAR_TYPE="/login/app/book-car-tenant-cartype";
	public static final String FEED_USER_BOOK_CAR_CITY_CAR_TYPE="/login/app/book-car-city-car-type";
	public static final String FEED_USER_CAR_CANCEL_OTP="/login/app/car-cancel-otp";
	public static final String FEED_USER_GET_GEO_DATA_FEED="/login/app/get-geo-data-feed";
	public static final String FEED_CUSTOMER_CANCEL_REQUEST = "/login/app/feedcustomercancelrequest";
	public static final String FEED_OTP_CUSTOMER_CANCEL_VERIFICATION ="/login/app/customercancelotpverification";
	public static final String FEED_DRIVER_TRAKING="/login/app/feed-driver-tracking";
	public static final String FEED_REMARK_LIST="/login/app/feed-remark-list";
	public static final String SAVE_BILLPAYMENT_PAYUMONEY="/login/app/save-billpayment-payumoney";
	public static final String FEED_SAVE_BILL_PAYMENT_MODE="/login/app/feed-save-bill-payment-mode";
	public static final String FEED_SAVE_PAYMENT_MODE ="/login/app/feed-save-payment-mode";
	public static final String EMERGENCY_CONTACT = "/login/app/emergencycontact";
	public static final String FEED_SAVE_BILL_PAYMENT_MODE_PAYUMONEY="/login/app/feed-save-bill-payment-mode-payumoney";
	public static final String FEED_SAVE_PAYMENT_MODE_PAYUMONEY="/login/app/feed-save-payment-mode-payumoney";
	public static final String FEED_SPLIT_MONEY_PAYUMONEY="/login/app/feed-split-money";
	public static final String FEED_USER_GET_TENANT_BY_REVIEW = "/login/app/get-tenant-by-review";
	public static final String FEED_USER_GET_CAR_FOR_REVIEW = "/login/app/get-car-For-review";

	// Car Location
	public static final String FETCH_CAR_LOCATION = "/get-location";
	public static final String CAR_LOCATION = "/get-car-location";
	// Change Start End Trip
	public static final String GET_TRIP_DETAIL = "/enter_trip";
	public static final String GET_TRIP_BY_ID_IN_DETAIL = "/end_trip";
	public static final String GET_TRIP_IN_DETAIL = "/endtrip";
	public static final String GET_TRIPS_DETAIL_CLIENTLIST = "/client-trip";
	public static final String CHANGE_REQUEST = "/change-request";
	public static final String CHANGE_REQUEST_ACCEPT = "/change-request-accepted";
	public static final String CHANGE_REQUEST_REJECT = "/change-request-rejected";
	public static final String SAVE_DISTANCE_TRIP = "/login/app/save-distance-trip";



	//Pre Booking of Car
	public static final String PRE_BOOKING = "/prebooking-car";
	public static final String SAVE_PRE_BOOKING = "/save-prebooking";
	public static final String LIST_PRE_BOOKING = "/list-prebooking";
	public static final String DELETE_PRE_BOOKING = "/delete-prebooking";
	public static final String CONFIRM_BOOKING = "/confirm-prebooking";

	public static final String FEED_USER_GET_DRIVER_FOR_REVIEW = "/login/app/get-driver-For-review";
	public static final String FEED_USER_SAVE_CLIENT = "/login/app/save-client";

	public static final String REJECT_DRIVER_CANCEL_REQUEST = "/reject-cancelrequest";
	public static final String APPROVE_CHANGE_LATER = "/approve-changelater";

	//Support Ticket
	public static final String GENERATE_SUPPORT_TIKCKET = "/submitTicket";
	public static final String SUPPORT_TIKCKET = "/ticket";
	public static final String VIEW_SUPPORT_TICKET = "/viewTickets";
	public static final String TICKET_DETAILS = "/openTicket";
	public static final String VIEW_TICKET_TICKET_HANDLER = "/viewTicketsTicketHandler";
	public static final String TICKET_COMMENT_SUPPORTER = "/addComment";

	public static final String GET_DRIVER_BY_CAR = "/getDriverByCar";
	//static invoice
	public static final String INVOICE_STATIC = "/invoice-static";
	public static final String SAVE_INVOICE_STATIC ="/save-ivoice-static";
	public static final String LIST_INVOICE_STATIC = "/invoice-static-list";
	public static final String PREVIEW ="/previewInvoice";

	public static final String VIEW_TICKET_DEPARTMENT_HANDLER = "/viewTicketsSpecificDepartment";
	public static final String ASSIGN_TICKET = "/assignTicket";
	public static final String VIEW_TICKET_ASSIGNMENT_HISTORY = "/viewAssignmentHistory";
	public static final String UPDATE_STATUS = "/updateStatus";
	public static final String ADD_DEPARTMENT_TYPE = "/addDepartmentType";
	public static final String SAVE_DEPARTMENT_TYPE = "/saveDepartmentType";
	public static final String VIEW_DEPARTMENT_TYPE = "/viewDepartmentType";
	public static final String DELETE_DEPARTMENT_TYPE = "/deleteDepartmentType";
	public static final String OPEN_CLOSED_TICKET = "/openClosedTicket";
	public static final String UPDATE_TICKET_PRIORITY = "/updateTicketPriority";
	public static final String USER_TICKET_STATUS = "/userTicketStatus";
	public static final String TICKET_TIMELINE = "/ticketTimeline";


	//Web Socket Notification Url
	public static final String GET_NOTIFICATIONS_BY_USER = "/get-notification-by-user";
	public static final String UPDATE_NOTIFICATION_BY_ID = "/update-notification-by-id";

	public static final String IMAGE = "/login/app/image-byte";
	public static final String LOAD_CASH = "/login/app/loadcash";

	public static final String GET_PERMISSION = "/getPermission";
	
	//Email template
	public static final String EMAIL_TEMPLATE="/email-template";
	public static final String SAVE_EMAIL_TEMPLATE="/save-email-template";
	public static final String EMAIL_TEMPLATE_LIST="/email-template-list";
	
	//SMS template
	public static final String SMS_TEMPLATE="/sms-template";
	public static final String SAVE_SMS_TEMPLATE="/save-sma-template";
	public static final String SMS_TEMPLATE_LIST="/sms-template-list";
}