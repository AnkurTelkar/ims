package com.ims.reports.views;

import java.util.Map;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.web.servlet.view.document.AbstractXlsxView;
 
public class GenerateExcel extends AbstractXlsxView {
 
    

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if( model == null || !model.containsKey("filename") ) {
			throw new Exception( "Invalid filename" );
			
		}
		
		response.setHeader("Content-Disposition", "attachment; filename=\"" + model.get( "filename" ) + "\"");

		 Sheet sheet = workbook.createSheet("sheet 1");
	        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
	        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
	        style.setFillPattern( FillPatternType.SOLID_FOREGROUND );
	        style.setAlignment(HorizontalAlignment.CENTER);
	        
	        Row row = null;
	        Cell cell = null;
	        int rowCount = 0;
	        int colCount = 0;
	 
	        // Create header cells
	        row = sheet.createRow(rowCount++);
	 
	        cell = row.createCell(colCount++);
	        cell.setCellStyle(style);
	        cell.setCellValue("Name");
	 
	        cell = row.createCell(colCount++);
	        cell.setCellStyle(style);
	        cell.setCellValue("Flavor");
	 
	        cell = row.createCell(colCount++);
	        cell.setCellStyle(style);
	        cell.setCellValue("Toppings");
	 
	        // Create data cells
	        row = sheet.createRow(rowCount++);
	        colCount = 0;
	        row.createCell(colCount++).setCellValue("Euto Pizza");
	        row.createCell(colCount++).setCellValue("Chieickn");
	 
	        StringBuffer toppings = new StringBuffer("");
	        toppings.append( "Extra Cheese " ).append( "Extra Mionise " );
	 
	        row.createCell(colCount++).setCellValue(toppings.toString());
	        
	        for (int i = 0; i < 3; i++)
	            sheet.autoSizeColumn(i, true);
		
	}
 
}