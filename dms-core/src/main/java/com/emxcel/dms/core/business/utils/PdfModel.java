package com.emxcel.dms.core.business.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.invoice.Invoice;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfModel {
	private static Font TIME_ROMAN = new Font(Font.FontFamily.UNDEFINED, 18, Font.BOLDITALIC, BaseColor.RED);
	private static Font TIME_ROMAN2 = new Font(Font.FontFamily.UNDEFINED, 10, Font.BOLDITALIC);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.ITALIC);
	private static Font TIME_ROMAN_SMALL2 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.ITALIC);
	private static Font TIME_ROMAN1 = new Font(Font.FontFamily.UNDEFINED, 9, Font.NORMAL);
	private static Font TIMES_ROMAN1 = new Font(Font.FontFamily.UNDEFINED, 10, Font.BOLD);

	/**
	 * @param args
	 */
	public static Document pdfmodel(String file, Invoice invoice, ClientModel client) throws IOException {
		Document document = null;
		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			addMetaData(document);
			addTitlePage(document);
			createTable(document, invoice,client);
			addSubject(document);
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

	private static void addMetaData(Document document) {
		document.addTitle("D.K.TRAVELS AND TOURS ");
		document.addSubject("D.K.TRAVELS AND TOURS ");
		document.addAuthor("D.K.TRAVELS AND TOURS ");
		document.addCreator("Invoice");
	}

	private static void addTitlePage(Document document) throws DocumentException {
		Paragraph preface = new Paragraph();
		creteEmptyLine(preface, 1);
		preface.add(new Paragraph("D.K.TRAVELS AND TOURS", TIME_ROMAN));
		creteEmptyLine(preface, 1);
		preface.add(new Paragraph("807-STAR PLAZA, OPP.RAVI HOTEL, NR.CIRCUIT HOUSE,", TIME_ROMAN_SMALL));
		preface.add(new Paragraph("PHULCHHAB CHOWK, RAJKOT ", TIME_ROMAN_SMALL));
		preface.add(new Paragraph("MO.   9510063345 ", TIME_ROMAN_SMALL));
		preface.add(new Paragraph("PH. 0281- 2471764 / 2471875 / 2431701  ", TIME_ROMAN_SMALL));
		preface.add(new Paragraph("Email:  info@dktravels.net  ", TIME_ROMAN_SMALL));
		preface.add(new Paragraph("___________________________________________________________________________"));
		creteEmptyLine(preface, 1);
		preface.add(new Paragraph("S.TAX:- "));
		preface.add(new Paragraph("INVOICE NO. "));
		preface.add(new Paragraph("TO, "));
		//preface.add(new Paragraph("INVOICE NO: " + simpleDateFormat.format(new Date()), TIME_ROMAN_SMALL));
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		preface.add(new Paragraph("Date: " + simpleDateFormat.format(new Date()), TIMES_ROMAN1));
		document.add(preface);
	}

	// get cell and set font, size to that column
	public static PdfPCell getNormalCell(String string, Font font) throws DocumentException, IOException {
		if (string != null && "".equals(string)) {
			return new PdfPCell();
		}
		Font f = font;
		PdfPCell cell = new PdfPCell(new Phrase(string, f));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		return cell;
	}

	private static void creteEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private static void createTable(Document document, Invoice invoice, ClientModel client) throws DocumentException {
		Paragraph paragraph = new Paragraph();
		creteEmptyLine(paragraph, 2);
		document.add(paragraph);
		PdfPTable table = new PdfPTable(10);
		PdfPCell cell = new PdfPCell(new Phrase("     "));
		try {
			table.addCell(getNormalCell("PASSENGER'S NAME", TIMES_ROMAN1));
			table.addCell(getNormalCell("DATE OF TRAVEL", TIMES_ROMAN1));
			table.addCell(getNormalCell("PLACE OF TOUR", TIMES_ROMAN1));
			table.addCell(getNormalCell("K.M./DAY", TIMES_ROMAN1));
			table.addCell(getNormalCell("RATE PER K.M", TIMES_ROMAN1));
			table.addCell(getNormalCell("AMOUNT", TIMES_ROMAN1));

			table.setHeaderRows(1);
			table.setWidthPercentage(100);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.getDefaultCell();
			table.getDefaultCell();
			table.getDefaultCell();
			table.getDefaultCell();
			table.getDefaultCell();
			//table.addCell(getNormalCell("" + invoice.getTripId(), TIME_ROMAN1));
			/*table.addCell(getNormalCell("" + client.getGuest().getcName(), TIME_ROMAN1));
			table.addCell(getNormalCell("" + client.getPickUpDateTime(), TIME_ROMAN1));
			table.addCell(getNormalCell("" + client.getDestinationPlace(), TIME_ROMAN1));
			table.addCell(getNormalCell("" + invoice.getKm(), TIME_ROMAN1));
			table.addCell(getNormalCell("" + client.getRate(), TIME_ROMAN1));
			table.addCell(getNormalCell("" + Double.toString(invoice.getAmount()), TIME_ROMAN1));*/
			
			
			table.setHeaderRows(5);
		} catch (IOException e) {
			e.printStackTrace();
		}

		cell.setRowspan(2);
		cell.setColspan(1);

		for (int i = 0; i < 4; i++) {
			table.addCell(cell);
			table.addCell(cell);
			table.addCell(cell);
			table.addCell(cell);
			table.addCell(cell);
		}
		document.add(table);
	}

	public static String dateConvert(java.sql.Date dateFormat) {
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String date = df.format(dateFormat);
		return date;
	}

	private static void addSubject(Document document) throws DocumentException {
		Paragraph preface1 = new Paragraph();
		creteEmptyLine(preface1, 1);
		preface1.add(new Paragraph("TERMS & CONDITIONS:", TIME_ROMAN_SMALL2));
		creteEmptyLine(preface1, 1);
		preface1.add(new Paragraph("- Tour Cancellation Charges will be 50% on total tour cost.,", TIME_ROMAN_SMALL));
		preface1.add(new Paragraph("- Per day minimum 300 K.M will be charged. ", TIME_ROMAN_SMALL));
		preface1.add(new Paragraph("- Driver allowance and Toll tax extra.  ", TIME_ROMAN_SMALL));
		preface1.add(new Paragraph(
				"- Cheque should be in favor of �D K TRAVELS AND TOURS.�                                                                                     Authorized Signatory.  ",
				TIME_ROMAN_SMALL));
		preface1.add(new Paragraph("- Subject to Rajkot Jurisdiction only.  ", TIME_ROMAN_SMALL));
		preface1.add(new Paragraph(
				"- If you do the payment through credit card then 6.00% + 2.5% tax would be charge on it. ",
				TIME_ROMAN_SMALL));
		preface1.add(new Paragraph("E. & O.E ", TIME_ROMAN2));
		preface1.add(new Paragraph("___________________________________________________________________________"));
		creteEmptyLine(preface1, 1);
		document.add(preface1);
	}
}