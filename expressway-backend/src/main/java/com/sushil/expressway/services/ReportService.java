package com.sushil.expressway.services;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;

import com.sushil.expressway.entitys.Client;
import com.sushil.expressway.entitys.Consignment;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {

    private ConsignmentService consignmentService;
    private ClientService clientService;

    public byte[] generateInvoicePdf(Long clientId, LocalDate fromDate, LocalDate toDate) throws Exception {
        Client client = clientService.getClientById(clientId);
        List<Consignment> consignments = consignmentService.getConsignmentsByClientAndDateRange(clientId, fromDate,
                toDate);

        if (consignments.isEmpty()) {
            throw new RuntimeException("No consignments found for the specified date range");
        }

        Document document = new Document(PageSize.A4, 20, 20, 20, 20);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);

        // ================= HEADER BLOCK =================
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[] { 70, 30 });

        PdfPCell leftCell = new PdfPCell();
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.addElement(new Paragraph("TO,", boldFont));
        leftCell.addElement(new Paragraph(client.getName(), normalFont));
        leftCell.addElement(new Paragraph(client.getAddress(), normalFont));
        leftCell.addElement(new Paragraph("G.S.T NO: " + client.getContactPerson(), normalFont));
        headerTable.addCell(leftCell);

        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.addElement(new Paragraph("BILL PERIOD: " +
                fromDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " TO " +
                toDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), normalFont));
        rightCell.addElement(new Paragraph("BILL NO: " + "A" + System.currentTimeMillis() % 10000, normalFont));
        rightCell.addElement(new Paragraph("INVOICE DATE: " +
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont));
        headerTable.addCell(rightCell);

        document.add(headerTable);
        document.add(Chunk.NEWLINE);

        // ================= CONSIGNMENT TABLE =================
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setWidths(new float[] { 6, 12, 20, 20, 10, 10, 12, 10 });

        String[] headers = { "SRNO", "DATE", "C.NOTE", "DESTINATION", "MODE", "D/S", "WEIGHT", "AMOUNT" };
        for (String h : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(h, boldFont));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(Color.LIGHT_GRAY);
            table.addCell(headerCell);
        }

        int srNo = 1;
        long billAmount = 0;
        for (Consignment consignment : consignments) {
            table.addCell(new Phrase(String.valueOf(srNo++), normalFont));
            table.addCell(new Phrase(consignment.getBookingDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                    normalFont));
            table.addCell(new Phrase(consignment.getTrackingNumber(), normalFont));
            table.addCell(new Phrase(consignment.getReceiverAddress(), normalFont));
            table.addCell(new Phrase(consignment.getServiceType(), normalFont));
            table.addCell(new Phrase(consignment.getServiceType(), normalFont));
            table.addCell(new Phrase(consignment.getWeight() + "kg", normalFont));
            table.addCell(new Phrase(String.format("%.2f", consignment.getTotalAmount()), normalFont));
            billAmount += consignment.getTotalAmount();
        }
        document.add(table);

        // ================= SUMMARY BLOCK =================
        document.add(Chunk.NEWLINE);
        PdfPTable summaryTable = new PdfPTable(2);
        summaryTable.setWidthPercentage(40);
        summaryTable.setHorizontalAlignment(Element.ALIGN_RIGHT);

        double fuelSurcharge = 0; // if needed, calculate
        double otherCharges = 0;
        double subTotal = billAmount + fuelSurcharge + otherCharges;
        double igst = subTotal * 0.18; // 18% IGST
        double grandTotal = subTotal + igst;

        addSummaryRow(summaryTable, "BILL AMOUNT", billAmount, boldFont, normalFont);
        addSummaryRow(summaryTable, "FUEL SURCHARGES 40%", fuelSurcharge, boldFont, normalFont);
        addSummaryRow(summaryTable, "OTHER CHARGES", otherCharges, boldFont, normalFont);
        addSummaryRow(summaryTable, "SUB TOTAL", subTotal, boldFont, normalFont);
        addSummaryRow(summaryTable, "IGST @ 18%", igst, boldFont, normalFont);
        addSummaryRow(summaryTable, "Grand Total", grandTotal, boldFont, normalFont);

        document.add(summaryTable);

        // ================= AMOUNT IN WORDS =================
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("RUPEES: " + UtilService.convert((int) grandTotal) + " ONLY", boldFont));

        // ================= NOTES =================
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("1. Payment by A/C Payee Cheques only favouring << EXPRESSWAY COURIER SERVICES >>",
                normalFont));
        document.add(new Paragraph("2. Kindly indicate the Bill No. & Date on the reverse of the Cheque.", normalFont));
        document.add(new Paragraph("3. In case of cash payment, demand receipt within 2 days from our representative.",
                normalFont));
        document.add(new Paragraph(
                "4. If payment is not made on or before due date, interest @ 24% P.A. will be charged.", normalFont));
        document.add(new Paragraph(
                "5. Any discrepancy regarding this bill must be notified within a week from receipt.", normalFont));

        // ================= FOOTER =================
        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("GST NO: " + "27ATOPM3117D1ZS", normalFont));
        document.add(new Paragraph("PAN NO: " + "ATOPM3117D", normalFont));
        document.add(Chunk.NEWLINE);
        Paragraph sign = new Paragraph("FOR EXPRESSWAY COURIER SERVICES", boldFont);
        sign.setAlignment(Element.ALIGN_RIGHT);
        document.add(sign);

        document.add(Chunk.NEWLINE);
        Paragraph thanks = new Paragraph("THANK YOU", boldFont);
        thanks.setAlignment(Element.ALIGN_CENTER);
        document.add(thanks);

        document.close();
        return out.toByteArray();
    }

    private void addSummaryRow(PdfPTable table, String label, double value, Font bold, Font normal) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label, bold));
        labelCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(String.format("%.2f", value), normal));
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(valueCell);
    }

}
