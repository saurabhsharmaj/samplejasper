package com.stackextend.generatepdfdocument.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import com.stackextend.generatepdfdocument.model.Patient;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class InvoiceService {

    Logger log = LogManager.getLogger(InvoiceService.class);

    private static final String logo_path = "/jasper/images/logo-herblife.png";
    private final String invoice_template = "/jasper/rx.jrxml";

	// Fill template order parametres
    private Map<String, Object> parameters(Patient patient, Locale locale) {
        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("logo", getClass().getResourceAsStream(logo_path));
        parameters.put("patient",  patient);
        parameters.put("REPORT_LOCALE", locale);

        return parameters;
    }

    // Load invoice jrxml template
    private JasperReport loadTemplate() throws JRException {

        log.info(String.format("Invoice template path : %s", invoice_template));

        final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

        return JasperCompileManager.compileReport(jasperDesign);
    }

	public String generate(Patient patient, Locale locale) {
		File dir = new File("D:\\upwork\\island\\code\\POC-Code\\jasperreports-pdf-generation-example\\my-invoice");
    	dir.mkdirs();
        File pdfFile = new File("D:\\upwork\\island\\code\\POC-Code\\jasperreports-pdf-generation-example\\my-invoice\\my-invoice.pdf");

        log.info(String.format("Invoice pdf path : %s", pdfFile.getAbsolutePath()));

        try(FileOutputStream pos = new FileOutputStream(pdfFile))
        {
            // Load invoice jrxml template.
            final JasperReport report = loadTemplate();

            // Create parameters map.
            final Map<String, Object> parameters = parameters(patient, locale);

            // Create an empty datasource.
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Invoice"));

            // Render as PDF.
            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);

        }
        catch (final Exception e)
        {
            log.error(String.format("An error occured during PDF creation: %s", e));
        }
        
        return pdfFile.getAbsolutePath();
	}

}
