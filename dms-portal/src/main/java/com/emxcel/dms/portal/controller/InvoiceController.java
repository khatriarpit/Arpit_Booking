package com.emxcel.dms.portal.controller;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.modules.email.Email;
import com.emxcel.dms.core.business.modules.email.HtmlEmailSender;
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.email.EmailLogService;
import com.emxcel.dms.core.business.services.invoice.InvoiceService;
import com.emxcel.dms.core.business.services.invoice.Invoice_StaticService;
import com.emxcel.dms.core.business.services.invoice.TermsConditionsService;
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.business.services.tax.TaxSlabService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.common.EmailLog;
import com.emxcel.dms.core.model.invoice.Invoice;
import com.emxcel.dms.core.model.invoice.Invoice_Static;
import com.emxcel.dms.core.model.invoice.TermsConditions;
import com.emxcel.dms.core.model.superadmin.Tenant;
import com.emxcel.dms.core.model.tax.TaxSlab;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * Created by emxcelsolution on 2/22/17.
 */
@Controller
public class InvoiceController {

    private static final Logger logger = Logger.getLogger(InvoiceController.class);

    public static final String HTML = CommonUtil.PROJECT_LOCATION + "pdf-html/invoice-pdf.html";

    public static final String DEST = CommonUtil.LOCATION + "pdf-html/";

    /**
     * clientModelService
     */
    @Inject
    private ClientModelService clientModelService;

    /**
     * taxSlabService
     */
    @Inject
    private TaxSlabService taxSlabService;

    /**
     * invoiceService
     */
    @Inject
    private InvoiceService invoiceService;


    /**
     * invoice_staticService
     */
    @Inject
    private Invoice_StaticService invoice_staticService;

    /**
     * termsConditionsService
     */
    @Inject
    private TermsConditionsService termsConditionsService;

    /**
     * **Autowired service of HtmlEmailSender **.
     */
    @Inject
    private HtmlEmailSender htmlEmailSender;

    /**
     * **Autowired service of companyService **.
     */
    @Inject
    private TenantService companyService;

    /**
     * EmailLogService.
     */
    @Inject
    private EmailLogService emailLogService;

    @RequestMapping(value = UrlConstants.MANUAL_INVOICE, method = RequestMethod.GET)
    private ModelAndView addInvoice(@RequestParam(value = "tripId", required = false) final String tripId,
                                    @ModelAttribute("command") final Invoice invoice, final BindingResult result, final RedirectAttributes ra) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (tripId != null && !"".equals(tripId)) {
                ClientModel clientModel1 = clientModelService.getTripByTripId(tripId);
                if (clientModel1 != null) {
                    map.put("clientModel", clientModel1);
                }
                if (invoiceService.getTripByTripId(tripId) != null) {
                    Invoice invoice2 = invoiceService.getTripByTripId(tripId);
                    map.put("InvoiceDetails", invoice2);
                }
            }
        } catch (Exception e) {
            logger.error(Constants.EXCEPTION_THROW, e);
            ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
            return new ModelAndView(Constants.REDIRECT + UrlConstants.MANUAL_INVOICE);
        }
        return new ModelAndView(ViewConstants.MANUAL_INVOICE, map);
    }

    @RequestMapping(value = UrlConstants.SAVE_MANUAL_INVOICE, method = RequestMethod.POST)
    private ModelAndView saveInvoice(@ModelAttribute("command") final Invoice invoice, final HttpSession session,
                                     final BindingResult result, final RedirectAttributes redirectAttrs) {
        User userSession = (User) session.getAttribute("user");
        ClientModel clientModel = clientModelService.getTripByTripId(invoice.getTripId());
        try {
            if (invoice.getId() == null) {
                invoice.setStatus(1);
                clientModel.setManualInvStatus(1);
                invoice.setTanentID(userSession.getTanentID());
                if (invoice.getId() != null) {
                    invoice.setInvoiceNo(generateInvoiceNumber(String.valueOf(invoice.getId())));
                } else {
                    invoice.setInvoiceNo(generateInvoiceNumber());
                }
            }
            invoiceService.save(invoice);
            clientModelService.save(clientModel);
        } catch (Exception e) {
            logger.error(Constants.EXCEPTION_THROW, e);
            redirectAttrs.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
            return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_BOOKEDCARS);
        }

        return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_BOOKEDCARS);
    }

    private String generateInvoiceNumber(String invoiceNo) {
        String str1;
        String invoiceNumber = null;
        if (invoiceNo != null && !"".equals(invoiceNo)) {
            String[] spti1 = invoiceNo.split("/");
            str1 = spti1[0];
            Date d = new Date(0);
            SimpleDateFormat df = new SimpleDateFormat("yy");
            String formate = df.format(d);
            int year1 = 1 + Integer.parseInt(formate);
            String year = "/" + formate + "-" + year1;
            int number = Integer.parseInt(str1) + 1;
            String number1 = String.format("%03d", number);
            invoiceNumber = number1 + year;
        }
        return invoiceNumber;
    }

    private String generateInvoiceNumber() {
        Date d = new Date(0);
        String invoiceNumber;
        String str = "001";
        SimpleDateFormat df = new SimpleDateFormat("yy");
        String formate = df.format(d);
        int year1 = 1 + Integer.parseInt(formate);
        String year = "/" + formate + "-" + year1;
        invoiceNumber = str + year;
        return invoiceNumber;
    }

    @RequestMapping(value = UrlConstants.MANUAL_TAX, method = RequestMethod.POST)
    @ResponseBody
    public Double getTaxAmount(@RequestParam(value = "amount", required = false) final Double amount,
                               @RequestParam(value = "invoicecategory", required = false) final String invoicecategory) {
        Double rate = 0.0;
        Double tax = 0.0;
        try {
            if (invoicecategory != null && !("").equals(invoicecategory)) {
                java.util.List<TaxSlab> listOfTaxSlab = taxSlabService.getTaxSlabById(Long.valueOf(invoicecategory));
                for (TaxSlab taxSlab : listOfTaxSlab) {
                    rate += taxSlab.getRate();
                }
                tax = (amount * rate) / 100;
            }
        } catch (Exception e) {
            logger.error(Constants.EXCEPTION_THROW, e);
        }
        return tax;
    }

    @RequestMapping(value = UrlConstants.SHOW_MANUAL_INVOICE_PDF, method = RequestMethod.GET)
    private ModelAndView showPdf(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(value = "tripId", required = false) final String tripId,
                                 final RedirectAttributes ra) {
        Map<String, Object> model = new HashMap<>();
        User userSession = (User) session.getAttribute("user");
        try {
            if (tripId != null && !"".equals(tripId)) {
                Invoice_Static invoice_staticByTanent = invoice_staticService.getByTanentID(userSession.getTanentID());
                Invoice invoiceByStaticInvoice = invoiceService.getByTanentID(invoice_staticByTanent.getTanentID());
                ClientModel clientByTrip = clientModelService.getByTanentID(invoiceByStaticInvoice.getTanentID(),invoiceByStaticInvoice.getTripId());
                List<TermsConditions> termsConditionsList = termsConditionsService.getListOfTermConditionsByTenantID(invoice_staticByTanent.getId());
                final ServletContext servletContext = request.getSession().getServletContext();
                final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                final String temperotyFilePath = tempDirectory.getAbsolutePath();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Date date = new Date(timestamp.getTime());
                String fileName = "invoice-pdf-" + date.getTime() + ".pdf";
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "attachment; filename=" + fileName);
                try {
                    model.put("companyName", invoice_staticByTanent.getCompanyName());
                    model.put("address1", invoice_staticByTanent.getAddress1());
                    model.put("address2", invoice_staticByTanent.getAddress2());
                    model.put("address3", invoice_staticByTanent.getAddress3());
                    model.put("emailid", invoice_staticByTanent.getEmailid());
                    model.put("landlineNo", invoice_staticByTanent.getLandlineNo());
                    model.put("phoneNo", invoice_staticByTanent.getPhoneNo());
                    model.put("ServiceTax", invoiceByStaticInvoice.getTax());
                    model.put("InvoiceNumber", invoiceByStaticInvoice.getInvoiceNo());
                    model.put("PassengerName", clientByTrip.getGuest().getPersonName());
                    Timestamp pickUpDateTime = clientByTrip.getPickUpDateTime();
                    Date pickUpDate = new Date(pickUpDateTime.getTime());
                    model.put("DateOfTravel", pickUpDate);
                    model.put("PlaceOfTour", clientByTrip.getDestinationPlace());
                    model.put("KM", invoiceByStaticInvoice.getKm());
                    model.put("RatePerKM", clientByTrip.getRate());
                    model.put("Amount", invoiceByStaticInvoice.getTotalAmount());
                    model.put("TollTax", "");
                    model.put("DriverAllowance", clientByTrip.getDriverAllownce());
                    model.put("Note", invoice_staticByTanent.getNote());
                    if (termsConditionsList.size() > 0) {
                        String termConditions = "";
                        for (TermsConditions termsConditions1 : termsConditionsList) {
                            if (termsConditions1.getTermsCondition() != null && !termsConditions1.getTermsCondition().equals("")) {
                                if (!termConditions.equals("")) {
                                    termConditions += "<br></br><br></br>";
                                }
                                termConditions += "->" + termsConditions1.getTermsCondition();
                            }
                        }
                        model.put("TermsConditions", termConditions);
                        model.put("imgFile", CommonUtil.LOCATION + "invoice/invoice_static/1/" + invoice_staticByTanent.getImage());
                        Double amount = invoiceByStaticInvoice.getTotalAmount();
                        Double tolltax = 0.0;
                        Integer driverAllowance = clientByTrip.getDriverAllownce();
                        double finalAmount = amount + tolltax + driverAllowance;
                        model.put("finalTotal", finalAmount);
                        model.put("finalTotalInWord", CommonUtil.convert((int)finalAmount));
                        model.put("totalAmountDue", 0);
                        Timestamp todayDate= new Timestamp(System.currentTimeMillis());
                        Date today = new Date(todayDate.getTime());
                        model.put("dateOfInvoice", today);
                    }
                    CommonUtil.createPdf(DEST + fileName, model, response);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    baos = convertPDFToByteArrayOutputStream(DEST + fileName);
                    OutputStream os = response.getOutputStream();
                    baos.writeTo(os);
                    os.flush();
                } catch (Exception e) {
                    logger.error(Constants.EXCEPTION_THROW, e);
                }
            }
        } catch (Exception e) {
            logger.error(Constants.EXCEPTION_THROW, e);
            ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
            return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_BOOKEDCARS);
        }
        return new ModelAndView(ViewConstants.LIST_BOOKEDCARS, model);
    }


    @RequestMapping(value = UrlConstants.SEND_MANUAL_INVOICE_PDF, method = RequestMethod.GET)
    public ModelAndView sendEmailPDF(final HttpSession session, final HttpServletRequest request,final RedirectAttributes ra)
            throws Exception {
        ModelAndView model = new ModelAndView();
        User userSession = (User) session.getAttribute("user");
        if (userSession != null) {
            String from = "";
            String toAddress = "";
            if (userSession.getTanentID() != null) {
                Tenant company = companyService.getById(userSession.getTanentID());
                if (company != null) {
                    from = company.getCompanyname();
                    toAddress = company.getEmailid();
                }
            } else {
                from = Constants.COMPANY_NAME;
                toAddress = Constants.COMPANY_EMAIL_ID;
            }
            from = Constants.COMPANY_NAME;
            toAddress = "nitin.kotadiya@emxcelsolutions.com";
            String subject = Constants.SUBJECT_INVOICE_PDF;
            String fromAddress = Constants.COMPANY_EMAIL_ID;
            String content = "Nitin Patel";
            String template = Constants.CUSTOM_TEMPLATE;
            Map<String, String> map = new HashMap<String, String>();
            map.put("HEADER", subject);
            map.put("CONTENT", content);
            map.put("FOOTER", "Thanks<br>" + from);
            Map<String, Object> mapEmail = CommonUtil.getEmail(subject, from, fromAddress, toAddress, template, map,
                    userSession.getId());
            Email email = (Email) mapEmail.get("email");
            EmailLog emailLog = (EmailLog) mapEmail.get("emailLog");
            emailLog = emailLogService.saveEmailLog(emailLog);
            email.setFileName("/home/emxcelsolution/Downloads/cmp_full_page_table.pdf");
            htmlEmailSender.send(email, emailLog);
            model.setViewName(ViewConstants.RESEND_PASSWORD_PAGE);
        }
        return new ModelAndView(ViewConstants.LIST_BOOKEDCARS);
    }

    private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {
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
            logger.error(Constants.EXCEPTION_THROW, e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(Constants.EXCEPTION_THROW, e);
                }
            }
        }
        return baos;
    }


    @RequestMapping(value = UrlConstants.LIST_INVOICE_STATIC, method = RequestMethod.GET)
    public final ModelAndView invoiceStaticList(@ModelAttribute("command") final Invoice_Static invoice_Static) {
        Map<String, Object> model = new HashMap<>();
        model.put("invoicelist", invoice_staticService.list());
        return new ModelAndView(ViewConstants.LIST_INVOICE_STATIC, model);
    }

    @RequestMapping(value = UrlConstants.INVOICE_STATIC, method = RequestMethod.GET)
    public final ModelAndView invoiceStatic(@ModelAttribute("command") Invoice_Static invoice_Static,
                                            @RequestParam(value = "id", required = false) final String id, final RedirectAttributes ra) {
        Map<String, Object> model = new HashMap<>();
        try {
            if (id != null && !"".equals(id)) {
                invoice_Static = invoice_staticService.getById(Long.valueOf(id));
                if (invoice_Static != null) {
                    List<TermsConditions> termsConditionsList = termsConditionsService.getListOfTermConditionsByTenantID(invoice_Static.getId());
                    if (termsConditionsList.size() > 0) {
                        List<String> termConditions = new ArrayList<String>();
                        for (TermsConditions termsConditions1 : termsConditionsList) {
                            if (termsConditions1.getTermsCondition() != null && !termsConditions1.getTermsCondition().equals("")) {
                                termConditions.add(termsConditions1.getTermsCondition());
                            }
                        }
                        if (termConditions.size() > 0) {
                            invoice_Static.setTermsConditions(termConditions);
                        }
                    }
                    model.put("Invoice", invoice_Static);
                }
            }
        } catch (Exception e) {
            logger.error(Constants.EXCEPTION_THROW, e);
            ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
            return new ModelAndView(Constants.REDIRECT + UrlConstants.INVOICE_STATIC);
        }
        return new ModelAndView(ViewConstants.INVOICE_STATIC, model);
    }


    @RequestMapping(value = UrlConstants.SAVE_INVOICE_STATIC, method = RequestMethod.POST)
    public final ModelAndView saveInvoiceStatic(@RequestParam(value = "imgFile", required = false) final MultipartFile imgFile, @ModelAttribute("command") final Invoice_Static invoice_Static,
                                                final HttpSession session, final RedirectAttributes ra) {
        User user = (User) session.getAttribute("user");
        invoice_Static.setTanentID(user.getTanentID());
        try {

            String fileName;
            String path = CommonUtil.LOCATION + "invoice/invoice_static/" + invoice_Static.getTanentID();
            fileName = CommonUtil.getFileName(imgFile, path);
            if (!"".equals(fileName)) {
                invoice_Static.setImage(fileName);
            }
            Long invoiceID = null;
            if (invoice_Static.getId() != null) {
                List<TermsConditions> termsConditionsList = termsConditionsService.getListOfTermConditionsByTenantID(invoice_Static.getTanentID());
                if (termsConditionsList.size() > 0) {
                    for (TermsConditions termsConditions1 : termsConditionsList) {
                        termsConditionsService.delete(termsConditions1);
                    }
                }
                invoiceID = invoice_Static.getId();
                invoice_staticService.update(invoice_Static);
            } else {
                invoiceID = invoice_staticService.saveReturnID(invoice_Static);
            }
            if (invoice_Static.getTermsConditions() != null) {
                for (String termsCondition : invoice_Static.getTermsConditions()) {
                    TermsConditions termsConditions = new TermsConditions();
                    termsConditions.setInvoice_static_id(invoiceID);
                    termsConditions.setTermsCondition(termsCondition);
                    termsConditionsService.save(termsConditions);
                }
            }
        } catch (Exception e) {
            logger.error(Constants.EXCEPTION_THROW, e);
            ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
            return new ModelAndView(Constants.REDIRECT + UrlConstants.INVOICE_STATIC);
        }
        return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_INVOICE_STATIC);
    }

    @ResponseBody
    @RequestMapping(value = UrlConstants.PREVIEW, method = RequestMethod.POST)
    private void generateStaticInvoicePDF(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                                          //@RequestParam(value = "imgFile", required = false) final MultipartFile imgFile,
                                          @RequestParam("companyName") String companyName,
                                          @RequestParam("address1") String address1,
                                          @RequestParam("address2") String address2,
                                          @RequestParam("address3") String address3,
                                          @RequestParam("emailid") String emailid,
                                          @RequestParam("phoneNo") String phoneNo,
                                          @RequestParam("landlineNo") String landlineNo,
                                          @RequestParam("note") String note,
                                          @RequestParam("termsConditions") String[] termsConditions,
                                          final RedirectAttributes ra) {

        Map<String, Object> model = new HashMap<>();
        try {
            if (emailid != null && !"".equals(emailid)) {
                final ServletContext servletContext = request.getSession().getServletContext();
                final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
                final String temperotyFilePath = tempDirectory.getAbsolutePath();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Date date = new Date(timestamp.getTime());
                String fileName = "invoice-pdf-" + date.getTime() + ".pdf";
                response.setContentType("application/pdf");
                response.setHeader("Content-disposition", "attachment; filename=" + fileName);
                try {
                    //  model.put("imgFile",imgFile);
                    model.put("companyName", companyName);
                    model.put("address1", address1);
                    model.put("address2", address2);
                    model.put("address3", address3);
                    model.put("emailid", emailid);
                    model.put("landlineNo", Long.valueOf(landlineNo));
                    model.put("phoneNo", Long.valueOf(phoneNo));
                    model.put("note", note);
                    if (termsConditions.length > 0) {
                        for (int i = 1; i <= termsConditions.length; i++) {
                            model.put("termsConditions", termsConditions[i]);
                        }
                    }
                    CommonUtil.createPdf(DEST + fileName, model, response);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    baos = convertPDFToByteArrayOutputStream(DEST + fileName);
                    OutputStream os = response.getOutputStream();
                    baos.writeTo(os);
                    os.flush();
                } catch (Exception e) {
                    logger.error(Constants.EXCEPTION_THROW, e);
                }
            }
        } catch (Exception e) {
            logger.error(Constants.EXCEPTION_THROW, e);
            ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
            // return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_BOOKEDCARS);
        }
        //       return new ModelAndView(ViewConstants.LIST_BOOKEDCARS, model);
    }
}