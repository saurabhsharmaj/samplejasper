package com.stackextend.generatepdfdocument.controller;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stackextend.generatepdfdocument.model.Patient;
import com.stackextend.generatepdfdocument.service.InvoiceService;
import com.stackextend.generatepdfdocument.service.OrderService;
import com.stackextend.generatepdfdocument.utils.PrintUtils;

@RestController
public class HomeController {

	@Resource
	private OrderService orderService;
	@Resource
	private InvoiceService invoiceService;

	@PostMapping
	public void generate(@RequestBody Patient patient) throws Exception {
		
		String prescription = invoiceService.generate(patient, Locale.ENGLISH);
	   PrintUtils.print(prescription);
	}
}
