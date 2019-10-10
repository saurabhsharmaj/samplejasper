package com.stackextend.generatepdfdocument.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import com.islanderp.domain.catalog.dto.ProductDto;
import com.islanderp.domain.le.dto.CustomerContactDto;
import com.islanderp.domain.le.dto.CustomerDto;
import com.islanderp.domain.om.dto.OrderDto;
import com.islanderp.domain.om.dto.OrderItemDto;
import com.islanderp.domain.om.dto.OrderItemTypeEnum;
import com.islanderp.ds.InvalidInputException;
import com.islanderp.report.util.ReportUtil;
import com.stackextend.generatepdfdocument.model.OrderModel;

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
    private final String invoice_template = "/jasper/invoice_template.jrxml";

    public String generateInvoiceFor(OrderModel order1, Locale locale) throws Exception {
    	OrderDto order = getData();
    	System.out.println(ReportUtil.getOrderInvoiceId(order));
    	File dir = new File("D:\\upwork\\island\\code\\POC-Code\\jasperreports-pdf-generation-example\\my-invoice");
    	dir.mkdirs();
        File pdfFile = new File("D:\\upwork\\island\\code\\POC-Code\\jasperreports-pdf-generation-example\\my-invoice\\my-invoice.pdf");

        log.info(String.format("Invoice pdf path : %s", pdfFile.getAbsolutePath()));

        try(FileOutputStream pos = new FileOutputStream(pdfFile))
        {
            // Load invoice jrxml template.
            final JasperReport report = loadTemplate();

            // Create parameters map.
            final Map<String, Object> parameters = parameters(order, locale);

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

    private OrderDto getData() throws InvalidInputException {
		OrderDto order = new OrderDto();
		order.setId(123456l);
		order.setVersion(2l);
		CustomerDto customer = new CustomerDto();
		customer.setFirstName("Saurabh");
		customer.setLastName("sharma");
		
		CustomerContactDto customerContact = new CustomerContactDto();
		customerContact.setFirstName("Manu");
		customerContact.setLastName("Madrigal");
		customerContact.setAddress1("1030 Bush St.");
		customerContact.setCity("SF");
		customerContact.setState("CA");
		customerContact.setCountry("US");
		customerContact.setZipCode("302013");
		customerContact.setDefaultContact(true);
		customerContact.setContactType("HOME");
		customer.addContact(customerContact);
		order.setCustomer(customer );
		
		
		OrderItemDto orderItem = new OrderItemDto();
		ProductDto product = new ProductDto();
		product.setName("XX");		
		product.setId(2238l);
		orderItem.setProduct(product);
		
		orderItem.setId(7219l);
		orderItem.setOrderItemType( OrderItemTypeEnum.PRODUCT);
		orderItem.setUnitType("Single Unit");
		orderItem.setQuantity(2);
		//orderItem.setPrices(priceWithoutTax, priceWithTax, taxAmount, taxPercent, taxIncluded, preTaxDiscount, postTaxDiscount);
		orderItem.setPrices(12.00,12.99,0.0,0.0,true,0.0,0.0);
		orderItem.setCurrency("USD");
		order.addOrderItem(orderItem );
		
		
		/*
		 [{"id":7219,"version":0,
		 "createdOn":"2019-10-09T12:42:52.977Z","updatedOn":"2019-10-09T12:42:52.977Z","updatedByUser":"AbstractDtoMapper!!","locked":false,
		 "orderItemType":"PRODUCT","status":"NEW","inventoryTransfer":null,
		 
		 "product":{"id":2238,"version":2,"createdOn":"2019-10-04T09:52:07.843Z",
		 "updatedOn":"2019-10-04T09:52:08.067Z","updatedByUser":"AbstractDtoMapper!!","locked":false,"name":"XX111570182727688","isEnabled":true,
		 "externalIdSet":{UUID=878c4722-82cf-4b59-a724-822f72b9ba8c},"extendedAttributes":{_____DUMMY=___DUMMY, MetrcProductName=Metrc Product For testing},
		 "workflowStatus":"Approved","workflowSubtype":"Edibles","possibleWorkflowActions":[Cancel, Publish, Un-Publish, Update],
		 
		 "sourceSystemInfo":{"id":null,"version":1,"createdOn":null,"updatedOn":null,"updatedByUser":null,"locked":false,"sourceSystemType":"Island ERP",
		 "sourceSystemId":"2256","sourceStatus":null,"sourceNotes":null,"sourceCreatedOn":"2019-10-04T09:52:08.059Z","sourceUpdatedOn":"2019-10-04T09:52:08.106Z"},
		 
		 "detailId":2255,"detailVersion":1,"detailCreatedOn":"2019-10-04T09:52:08.059Z","detailUpdatedOn":"2019-10-04T09:52:08.106Z","detailCreatedInstant":null,
		 "detailUpdatedInstant":null,"detailLocked":false,"sku":"XX111570182727688","shortName":"X","productType":"Edibles",
		 "productStrain":{"id":499,"version":0,
		 "createdOn":"2019-10-04T09:51:09.831Z","updatedOn":"2019-10-04T09:51:09.831Z","updatedByUser":"AbstractDtoMapper!!","locked":false,"name":"Sour Diesel",
		 "spectrumType":"Sativa","comments":""},
		 "categories":[[Spectrum/Sativa: (ProductCategoryDto, 1007, V0, Sativa)], [Metrc_Category_Name/Edible (weight - each):
		  (ProductCategoryDto, 1061, V0, Edible (weight - each))]],
		  "compounds":[{"id":2257,"version":0,"createdOn":"2019-10-04T09:52:08.060Z",
		  "updatedOn":"2019-10-04T09:52:08.060Z","updatedByUser":"AbstractDtoMapper!!","locked":false,"productCompoundType":"THC (%)","compoundValue":2.0,
		  "comments":"percent"}, {"id":2258,"version":0,"createdOn":"2019-10-04T09:52:08.060Z","updatedOn":"2019-10-04T09:52:08.060Z",
		  "updatedByUser":"AbstractDtoMapper!!","locked":false,"productCompoundType":"CBD (%)","compoundValue":9.0,"comments":"percent"}],
		  
		  "unitsOfMeasure":[{"id":2259,"version":0,"createdOn":"2019-10-04T09:52:08.061Z","updatedOn":"2019-10-04T09:52:08.061Z","updatedByUser":
		  "AbstractDtoMapper!!","locked":false,"unitType":"Single Unit","defaultPriceWithoutTax":12.99,"defaultPriceWithTax":12.99,
		  "startingPriceWithoutTax":0.0,"startingPriceWithTax":0.0,"comments":null}],
		  "inventoryUnitOfMeasureType":"Single Unit",
		  "inventoryLiquidationMethod":"FIFO","description":"Testing product","descriptionHTML":null,"suppliers":[Apex Distribution, Apex Solutions Corp],
		  "brand":"Absolute Xtracts","vendor":"Apex Extracts","comments":"Test Product #1"},
		  
		  
		  "unitType":"Single Unit","quantity":2,"priceWithTax":12.99,
		  "priceWithoutTax":12.99,"taxAmount":0.0,"taxPercent":0.0,"totalWithoutTax":25.98,"totalWithTax":25.98,"taxIncluded":true,"preTaxDiscount":0.0,
		  "postTaxDiscount":0.0,"currency":"USD","comments":null}]
		  
		 */
		orderItem = new OrderItemDto();
		product = new ProductDto();
		product.setName("YY");		
		product.setId(2238l);
		orderItem.setProduct(product);
		
		orderItem.setId(7219l);
		orderItem.setOrderItemType( OrderItemTypeEnum.PRODUCT);
		orderItem.setUnitType("Single Unit");
		orderItem.setQuantity(3);
		//orderItem.setPrices(priceWithoutTax, priceWithTax, taxAmount, taxPercent, taxIncluded, preTaxDiscount, postTaxDiscount);
		orderItem.setPrices(10.00,15.00,0.0,0.0,true,0.0,0.0);
		orderItem.setCurrency("USD");
		order.addOrderItem(orderItem );
		
		
		orderItem = new OrderItemDto();
		product = new ProductDto();
		product.setName("ZZ");		
		product.setId(2238l);
		orderItem.setProduct(product);
		
		orderItem.setId(7219l);
		orderItem.setOrderItemType( OrderItemTypeEnum.PRODUCT);
		orderItem.setUnitType("Single Unit");
		orderItem.setQuantity(3);
		//orderItem.setPrices(priceWithoutTax, priceWithTax, taxAmount, taxPercent, taxIncluded, preTaxDiscount, postTaxDiscount);
		orderItem.setPrices(10.00,15.00,0.0,0.0,true,0.0,0.0);
		orderItem.setCurrency("USD");
		order.addOrderItem(orderItem );
		
		
		orderItem = new OrderItemDto();
		product = new ProductDto();
		product.setName("AA");		
		product.setId(2238l);
		orderItem.setProduct(product);
		
		orderItem.setId(7219l);
		orderItem.setOrderItemType( OrderItemTypeEnum.PRODUCT);
		orderItem.setUnitType("Single Unit");
		orderItem.setQuantity(3);
		//orderItem.setPrices(priceWithoutTax, priceWithTax, taxAmount, taxPercent, taxIncluded, preTaxDiscount, postTaxDiscount);
		orderItem.setPrices(10.00,15.00,0.0,0.0,true,0.0,0.0);
		orderItem.setCurrency("USD");
		order.addOrderItem(orderItem );
		
		
		
		orderItem = new OrderItemDto();
		product = new ProductDto();
		product.setName("QQ");		
		product.setId(2238l);
		orderItem.setProduct(product);
		
		orderItem.setId(7219l);
		orderItem.setOrderItemType( OrderItemTypeEnum.PRODUCT);
		orderItem.setUnitType("Single Unit");
		orderItem.setQuantity(3);
		//orderItem.setPrices(priceWithoutTax, priceWithTax, taxAmount, taxPercent, taxIncluded, preTaxDiscount, postTaxDiscount);
		orderItem.setPrices(10.00,15.00,0.0,0.0,true,0.0,0.0);
		orderItem.setCurrency("USD");
		order.addOrderItem(orderItem );
		
		
		return order;
	}

	// Fill template order parametres
    private Map<String, Object> parameters(OrderDto order, Locale locale) {
        final Map<String, Object> parameters = new HashMap<>();

        parameters.put("logo", getClass().getResourceAsStream(logo_path));
        parameters.put("order",  order);
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

}
