package com.sushil.expressway.services;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import java.awt.Color;

import com.sushil.expressway.entitys.Client;
import com.sushil.expressway.entitys.Consignment;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {

    private ConsignmentService consignmentService;
    private ClientService clientService;
    private final TemplateEngine templateEngine;

    // public byte[] generateInvoicePdf(Long clientId, LocalDate fromDate, LocalDate toDate) throws Exception {
    //     Client client = clientService.getClientById(clientId);
    //     List<Consignment> consignments = consignmentService.getConsignmentsByClientAndDateRange(clientId,
    //             fromDate, toDate);

    //     if (consignments.isEmpty()) {
    //         throw new RuntimeException("No consignments found for the specified date range");
    //     }

    //     Document document = new Document(PageSize.A4, 36, 36, 36, 36); // margins
    //     ByteArrayOutputStream out = new ByteArrayOutputStream();
    //     PdfWriter.getInstance(document, out);

    //     document.open();

    //     // Fonts
    //     Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, Color.BLACK);
    //     Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.BLACK);
    //     Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.BLACK);
    //     Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
    //     Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.DARK_GRAY);

    //     // ================= HEADER =================
    //     PdfPTable headerTable = new PdfPTable(2);
    //     headerTable.setWidthPercentage(100);
    //     headerTable.setWidths(new float[] { 60, 40 });

    //     // Left: Logo + Company Info
    //     PdfPCell leftCell = new PdfPCell();
    //     leftCell.setBorder(Rectangle.BOX);

    //     try {
    //         Image logo = Image.getInstance(
    //                 "S:\\Sushil\\BITS PROJECT\\Expressway-couriers\\expressway-backend\\src\\main\\resources\\static\\expresswayCourier.png"); // adjust
    //                                                                                                                                            // path
    //         logo.scaleToFit(190, 100);
    //         logo.setAlignment(Image.ALIGN_LEFT);
    //         leftCell.addElement(logo);
    //     } catch (Exception e) {
    //         leftCell.addElement(new Paragraph("<< COMPANY LOGO >>", boldFont));
    //     }

    //     leftCell.addElement(new Paragraph("EXPRESSWAY COURIER SERVICES", titleFont));
    //     leftCell.addElement(new Paragraph("Kahdge House Sion - 400001", normalFont));
    //     leftCell.addElement(new Paragraph("Phone: +91-9876543210 | Email: info@expressway.com", normalFont));
    //     headerTable.addCell(leftCell);

    //     // Right: Invoice details
    //     PdfPCell rightCell = new PdfPCell();
    //     rightCell.setBorder(Rectangle.NO_BORDER);
    //     rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    //     rightCell.setPaddingTop(10f);
    //     rightCell.addElement(new Paragraph("INVOICE", titleFont));
    //     rightCell.addElement(new Paragraph("BILL PERIOD: " +
    //             fromDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " TO " +
    //             toDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), normalFont));
    //     rightCell.addElement(new Paragraph("BILL NO: " + "A" + System.currentTimeMillis() % 10000, normalFont));
    //     rightCell.addElement(new Paragraph("INVOICE DATE: " +
    //             LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont));
    //     headerTable.addCell(rightCell);

    //     document.add(headerTable);
    //     document.add(Chunk.NEWLINE);

    //     // ================= CLIENT INFO =================
    //     Paragraph clientInfo = new Paragraph("BILL TO:", boldFont);
    //     clientInfo.add(new Paragraph(client.getName(), normalFont));
    //     clientInfo.add(new Paragraph(client.getAddress(), normalFont));
    //     clientInfo.add(new Paragraph("GST NO: " + client.getContactPerson(), normalFont));
    //     clientInfo.setSpacingAfter(10f);
    //     document.add(clientInfo);

    //     // ================= CONSIGNMENT TABLE =================
    //     PdfPTable table = new PdfPTable(8);
    //     table.setWidthPercentage(100);
    //     table.setWidths(new float[] { 6, 12, 20, 20, 10, 10, 12, 10 });
    //     table.setSpacingBefore(10f);

    //     String[] headers = { "SRNO", "DATE", "C.NOTE", "DESTINATION", "MODE", "D/S", "WEIGHT", "AMOUNT" };
    //     for (String h : headers) {
    //         PdfPCell headerCell = new PdfPCell(new Phrase(h, headerFont));
    //         headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    //         headerCell.setBackgroundColor(new Color(220, 220, 220));
    //         headerCell.setPadding(6f);
    //         table.addCell(headerCell);
    //     }

    //     int srNo = 1;
    //     long billAmount = 0;
    //     boolean shade = false;
    //     for (Consignment consignment : consignments) {
    //         Color rowColor = shade ? new Color(245, 245, 245) : Color.WHITE;

    //         table.addCell(makeCell(String.valueOf(srNo++), normalFont, Element.ALIGN_CENTER, rowColor));
    //         table.addCell(makeCell(consignment.getBookingDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
    //                 normalFont, Element.ALIGN_CENTER, rowColor));
    //         table.addCell(makeCell(consignment.getTrackingNumber(), normalFont, Element.ALIGN_CENTER, rowColor));
    //         table.addCell(makeCell(consignment.getReceiverAddress(), normalFont, Element.ALIGN_LEFT, rowColor));
    //         table.addCell(makeCell(consignment.getServiceType(), normalFont, Element.ALIGN_CENTER, rowColor));
    //         table.addCell(makeCell(consignment.getServiceType(), normalFont, Element.ALIGN_CENTER, rowColor));
    //         table.addCell(makeCell(consignment.getWeight() + "kg", normalFont, Element.ALIGN_CENTER, rowColor));
    //         table.addCell(makeCell(String.valueOf(consignment.getTotalAmount()), normalFont,
    //                 Element.ALIGN_RIGHT, rowColor));

    //         billAmount += consignment.getTotalAmount();
    //         shade = !shade; // alternate rows
    //     }
    //     document.add(table);

    //     // ================= SUMMARY BLOCK =================
    //     document.add(Chunk.NEWLINE);
    //     PdfPTable summaryTable = new PdfPTable(2);
    //     summaryTable.setWidthPercentage(40);
    //     summaryTable.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);

    //     double fuelSurcharge = 0;
    //     double otherCharges = 0;
    //     double subTotal = billAmount + fuelSurcharge + otherCharges;
    //     double igst = subTotal * 0.18;
    //     double grandTotal = subTotal + igst;

    //     addSummaryRow(summaryTable, "Bill Amount", billAmount, normalFont, normalFont);
    //     addSummaryRow(summaryTable, "Fuel Surcharge 40%", fuelSurcharge, normalFont, normalFont);
    //     addSummaryRow(summaryTable, "Other Charges", otherCharges, normalFont, normalFont);
    //     addSummaryRow(summaryTable, "Sub Total", subTotal, boldFont, normalFont);
    //     addSummaryRow(summaryTable, "IGST @ 18%", igst, boldFont, normalFont);
    //     addSummaryRow(summaryTable, "GRAND TOTAL", grandTotal, headerFont, headerFont);

    //     document.add(summaryTable);

    //     // ================= AMOUNT IN WORDS =================
    //     document.add(Chunk.NEWLINE);
    //     document.add(new Paragraph("Amount in Words: " +  + " ONLY", boldFont));

    //     // ================= NOTES =================
    //     document.add(Chunk.NEWLINE);
    //     document.add(new Paragraph("Terms & Conditions:", boldFont));
    //     document.add(new Paragraph("1. Payment by A/C Payee Cheques only favouring << EXPRESSWAY COURIER SERVICES >>",
    //             smallFont));
    //     document.add(new Paragraph("2. Kindly indicate the Bill No. & Date on the reverse of the Cheque.", smallFont));
    //     document.add(new Paragraph("3. In case of cash payment, demand receipt within 2 days from our representative.",
    //             smallFont));
    //     document.add(new Paragraph(
    //             "4. If payment is not made on or before due date, interest @ 24% P.A. will be charged.", smallFont));
    //     document.add(new Paragraph(
    //             "5. Any discrepancy regarding this bill must be notified within a week from receipt.", smallFont));

    //     // ================= FOOTER =================
    //     document.add(Chunk.NEWLINE);
    //     document.add(new Paragraph("GST NO: 27ATOPM3117D1ZS | PAN NO: ATOPM3117D", smallFont));
    //     document.add(Chunk.NEWLINE);

    //     Paragraph sign = new Paragraph("For EXPRESSWAY COURIER SERVICES", boldFont);
    //     sign.setAlignment(Element.ALIGN_RIGHT);
    //     document.add(sign);

    //     document.add(Chunk.NEWLINE);
    //     Paragraph thanks = new Paragraph("THANK YOU FOR YOUR BUSINESS!", headerFont);
    //     thanks.setAlignment(Element.ALIGN_CENTER);
    //     document.add(thanks);

    //     document.close();
    //     return out.toByteArray();
    // }

    public byte[] generateInvoicePdf(Long clientId, LocalDate fromDate, LocalDate toDate) throws Exception {
        Client client = clientService.getClientById(clientId);
        List<Consignment> consignments = consignmentService.getConsignmentsByClientAndDateRange(clientId,
                fromDate, toDate);

        if (consignments.isEmpty()) {
            throw new RuntimeException("No consignments found for the specified date range");
        }
         double billAmount = consignments.stream()
                .mapToDouble(Consignment::getTotalAmount)
                .sum();

        double fuelSurcharge = billAmount * 0.4;  // 40% of bill amount
        double sgst = billAmount * 0.09; // 9% tax
        double cgst = billAmount * 0.09; // 9% tax
        double igst = billAmount * 0.18; // 18% tax
        double otherCharges = 0;
        double subTotal = billAmount + fuelSurcharge + sgst + cgst + igst + otherCharges;
        double grandTotal = subTotal + igst;
        String InWords = UtilService.convert(grandTotal);

        // ========= PREPARE CONTEXT FOR TEMPLATE =========
        Context context = new Context();
        context.setVariable("client", client);
        context.setVariable("logo", "S:\\Sushil\\BITS PROJECT\\Expressway-couriers\\expressway-backend\\src\\main\\resources\\static\\expresswayCourier.png");
        context.setVariable("consignments", consignments);
        context.setVariable("fromDate", fromDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        context.setVariable("toDate", toDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        context.setVariable("billNo", "A" + System.currentTimeMillis() % 10000);
        context.setVariable("invoiceDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        context.setVariable("billAmount", String.format("%.2f", billAmount));
        context.setVariable("fuelSurcharge", String.format("%.2f", fuelSurcharge));
        context.setVariable("otherCharges", String.format("%.2f", otherCharges));
        context.setVariable("subTotal", String.format("%.2f", subTotal));
        context.setVariable("sgst", String.format("%.2f", sgst));
        context.setVariable("cgst", String.format("%.2f", cgst));
        context.setVariable("igst", String.format("%.2f", igst));
        context.setVariable("grandTotal", String.format("%.2f", grandTotal));
        context.setVariable("grandTotalInWords",InWords);
        
        // 1. Load HTML template with Thymeleaf/Freemarker
        String html = templateEngine.process("invoice", context); // Thymeleaf
        String baseUri = getClass().getResource("/static/").toExternalForm();
        // 2. Render HTML to PDF
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.withHtmlContent(html, baseUri);
        builder.toStream(out);
        builder.run();

        return out.toByteArray();
    }

    // private void addSummaryRow(PdfPTable table, String label, double value, Font labelFont, Font valueFont) {
    //     PdfPCell labelCell = new PdfPCell(new Phrase(label, labelFont));
    //     labelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
    //     labelCell.setPadding(5f);
    //     labelCell.setBorder(Rectangle.NO_BORDER);
    //     table.addCell(labelCell);

    //     PdfPCell valueCell = new PdfPCell(new Phrase(String.format("%.2f", value), valueFont));
    //     valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    //     valueCell.setPadding(5f);
    //     valueCell.setBorder(Rectangle.NO_BORDER);
    //     table.addCell(valueCell);
    // }
}
