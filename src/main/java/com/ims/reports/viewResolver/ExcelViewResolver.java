package com.ims.reports.viewResolver;

import java.util.Locale;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.ims.reports.views.GenerateExcel;

public class ExcelViewResolver implements ViewResolver{

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		GenerateExcel generateExcel = new GenerateExcel();
		return generateExcel;
	}

}