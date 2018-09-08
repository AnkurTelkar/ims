package com.ims.reports.viewResolver;

import java.util.Locale;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.ims.reports.views.GeneratePdf;

public class PdfViewResolver implements ViewResolver {

	@Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        GeneratePdf generatePdf = new GeneratePdf();
        return generatePdf;
    }
}
