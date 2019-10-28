package com.stackextend.generatepdfdocument;

import java.util.Locale;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.stackextend.generatepdfdocument.model.BasicHealth;
import com.stackextend.generatepdfdocument.model.OrderModel;
import com.stackextend.generatepdfdocument.model.Patient;
import com.stackextend.generatepdfdocument.service.InvoiceService;
import com.stackextend.generatepdfdocument.service.OrderService;


@SpringBootApplication
public class GeneratePdfDocumentApplication implements CommandLineRunner {

    Logger log = LogManager.getLogger(GeneratePdfDocumentApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GeneratePdfDocumentApplication.class, args);
	}

	@Resource
	private OrderService orderService;
	@Resource
    private InvoiceService invoiceService;

	@Override
	public void run(String... args) throws Exception {
        log.info("Start invoice generation...");

        Patient patient = new Patient();
        
        patient.setName("Saurabh");
        patient.setAge(34);
        patient.setGender("Male");
        BasicHealth basicHealth = new BasicHealth();
		patient.setBasicHealth(basicHealth);
        invoiceService.generate(patient, Locale.ENGLISH);

        log.info("Invoice generated successfully...");
	}
}
