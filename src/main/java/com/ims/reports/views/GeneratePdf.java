package com.ims.reports.views;

import java.awt.Color;
import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.web.servlet.view.document.AbstractPdfView;
 
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class GeneratePdf extends AbstractPdfView{
 
    @Override
    protected void buildPdfDocument(Map<String, Object> model,
            Document document, PdfWriter writer, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
 
        PdfPTable table = new PdfPTable(3);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setBackgroundColor(Color.lightGray);
 
        table.addCell("Name");
        table.addCell("Flavor");
        table.addCell("Toppings");
 
        table.addCell("Euro");
        table.addCell("Chicken");
 
        StringBuffer toppings = new StringBuffer("");
        toppings.append( "Extra Cheese " ).append( "Extra Mionise " );
        table.addCell(toppings.toString());
        document.add(table);
 
    }
}