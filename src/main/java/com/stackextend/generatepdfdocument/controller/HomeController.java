package com.stackextend.generatepdfdocument.controller;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackextend.generatepdfdocument.model.OrderModel;
import com.stackextend.generatepdfdocument.service.InvoiceService;
import com.stackextend.generatepdfdocument.service.OrderService;

@RestController
public class HomeController {

	@Resource
	private OrderService orderService;
	@Resource
	private InvoiceService invoiceService;

	@GetMapping
	public String generate() throws Exception {
		OrderModel order = orderService.getOrderByCode("XYZ123456789");        
		return invoiceService.generateInvoiceFor(order, Locale.ENGLISH);
	}
}
