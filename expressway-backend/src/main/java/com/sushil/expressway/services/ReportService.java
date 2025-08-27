package com.sushil.expressway.services;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import com.sushil.expressway.entitys.Client;
import com.sushil.expressway.entitys.Consignment;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {

    private ConsignmentService consignmentService;
    private ClientService clientService;

    public byte[] generateInvoicePdf(Long clientId, LocalDate fromDate, LocalDate toDate) throws Exception {
        // Fetch data
        Client client = clientService.getClientById(clientId);
        List<Consignment> consignments = consignmentService.getConsignmentsByClientAndDateRange(clientId, fromDate, toDate);
        
        if (consignments.isEmpty()) {
            throw new RuntimeException("No consignments found for the specified date range");
        }

        // Create PDF document
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        // Open document
        document.open();

        // Add header
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph header = new Paragraph("Invoice", headerFont);
        header.setAlignment(Element.ALIGN_CENTER);
        header.setSpacingAfter(20);
        document.add(header);

        // Add client details
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

        document.add(new Paragraph("Bill To:", boldFont));
        document.add(new Paragraph(client.getName(), normalFont));
        document.add(new Paragraph(client.getAddress(), normalFont));
        document.add(new Paragraph("Email: " + client.getEmail(), normalFont));
        document.add(new Paragraph("Contact: " + client.getContactNumber(), normalFont));
        document.add(new Paragraph(" ")); // Spacing

        // Add invoice details
        document.add(new Paragraph("Invoice Period: " + 
            fromDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + 
            toDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), boldFont));
        document.add(new Paragraph(" ")); // Spacing

        // Create consignments table
        PdfPTable table = new PdfPTable(5); // 5 columns
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Add table headers
        String[] headers = {"Date", "Tracking No", "Description", "Weight (kg)", "Amount"};
        for (String headerText : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(headerText, boldFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(5);
            table.addCell(cell);
        }

        // Add consignment data
        double totalAmount = 0;
        for (Consignment consignment : consignments) {
            table.addCell(new Phrase(consignment.getBookingDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont));
            table.addCell(new Phrase(consignment.getTrackingNumber(), normalFont));
            table.addCell(new Phrase(consignment.getServiceType(), normalFont));
            table.addCell(new Phrase(String.valueOf(consignment.getWeight()), normalFont));
            table.addCell(new Phrase(String.format("₹%d", consignment.getTotalAmount()), normalFont));
            totalAmount += consignment.getTotalAmount();
        }

        // Add total amount row
        PdfPCell totalCell = new PdfPCell(new Phrase("Total Amount:", boldFont));
        totalCell.setColspan(4);
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        totalCell.setPadding(5);
        table.addCell(totalCell);
        
        PdfPCell amountCell = new PdfPCell(new Phrase(String.format("₹%.2f", totalAmount), boldFont));
        amountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        amountCell.setPadding(5);
        table.addCell(amountCell);

        document.add(table);

        // Add footer
        Paragraph footer = new Paragraph("Thank you for your business!", normalFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(20);
        document.add(footer);

        // Close document
        document.close();

        return out.toByteArray();
    }
}


