package com.invoicemanagement.juntrax.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.invoicemanagement.juntrax.dataproviders.DataProvider;
import com.invoicemanagement.juntrax.utils.SeleniumUtility;

public class Helper {
	private static final Logger log = LoggerFactory.getLogger(Helper.class);
	Main runner = new Main();
	DataProvider dp = new DataProvider();
	SeleniumUtility su = new SeleniumUtility();
	public void stopselenium() {
		try {
			log.info("Discard Selenium Webriver:");
				Thread.sleep(9000);
				runner.driver.close();
			 

		} catch (Exception ex) {
			log.info("Exception");
		}
	}
	
	public void performTestOperation(String operation){
		log.info("Performing the Operation {" + operation + "}");
		Boolean status = null;
		switch(operation.trim().toLowerCase()){
		case "launch" : 
			su.setWebDriver(dp.getUrl());
			break;
		case "login":
			 status = su.goToLagunaSystemsUsa(dp.getEmail(),dp.getPassword());
			 break;
		case "createinvoicefordue":
			status = su.createinvoiceForDue();
			break;
		case "createinvoiceforpaid":
			status =su.createinvoiceForpaid();
			break;
		case "searchinvoice":
			status =su.searchInvoice();
			break;
		case "sendreminder":
			status =su.sendReminder();
			break;
		case "viewinvoice":
			status =su.viewinvoice();
			break;
		case "printinvoice":
			status =su.printInvoice();
			break;
		case "recordpayment":
			su.recordPayment();
			break;
		case "getallinvoices":
			status =su.getAllInvoices();
			break;
		case "cancelinvoice":
			status =su.cancelInvoice();
			break;
		case "editinvoice":
			status = su.editInvoice();
			break;
		case "sendinvoice" : 
			status =su.sendInvoice();
			break;
			
		}
	}
	
	public void testCaseStatus(String operation ){
		
	}
	
	
	
}
