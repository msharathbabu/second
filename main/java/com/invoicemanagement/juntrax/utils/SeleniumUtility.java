package com.invoicemanagement.juntrax.utils;



import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.invoicemanagement.juntrax.constants.Constants;
import com.invoicemanagement.juntrax.dataproviders.DataProvider;
import com.invoicemanagement.juntrax.main.Main;

public class SeleniumUtility {
	Main runner = new Main();
	Constants cns = new Constants();
	DataProvider dp = new DataProvider();
	Select select;
	Actions action;
	WebUtil wb = new WebUtil();
	public static JavascriptExecutor js;
	public static SeleniumUtility su = new SeleniumUtility();
	private static final Logger log = LoggerFactory.getLogger(SeleniumUtility.class);
	public void setWebDriver(String url){
		runner.driver = new ChromeDriver();
		runner.driver.get(url);
		runner.driver.manage().window().maximize();
		cns.takeScreenshot(true, "launch");
	}
	
	public boolean goToLagunaSystemsUsa(String email,String pwd){
		boolean status;
		try{
			runner.driver.findElement(By.xpath("//div/input[@type='email']")).sendKeys(email);
			runner.driver.findElement(By.xpath("//div/input[@type='password']")).sendKeys(pwd);
			runner.driver.findElement(By.xpath("//button[@type='submit']")).click();
			wb.waitForAnElement("//div[@class='slider']");
			runner.driver.findElement(By.xpath("//div[@class='slider']")).click();
			runner.driver.findElement(By.xpath("//ul[@id='side-menu']/li[@name='Offices']")).click();
			wb.waitForAnElement("//div[@name='office0']//a[@name='Sales'][contains(text(),'Sales')]");
			runner.driver.findElement(By.xpath("//div[@name='office0']//a[@name='Sales'][contains(text(),'Sales')]")).click();
			wb.waitForAnElement("//ul[@class='nav nav-pills']/li/a[@name='invoicesDetails']");
			runner.driver.findElement(By.xpath("//ul[@class='nav nav-pills']/li/a[@name='invoicesDetails']")).click();
			cns.takeScreenshot(true, "login");
			Thread.sleep(7000);
			status = true;
		}catch(Exception e){
			e.printStackTrace();
			status=false;
		}return status;
	}
	public boolean createinvoiceForDue(){
		boolean status;
		try{
			//Test case 1 : 
			cns.addInvoiceWithAllMandatoryDetails(dp.getPaymentStatus());
			//Test case 2 :
			cns.addInvoiceWithoutDetails();
			//Test Case 3 ://Test Case 6 ://Test Case 7 ://Test Case 8 :
			cns.addInvoiceWithMultipleItemsServices(dp.getPaymentStatus());
			cns.takeScreenshot(true, "createinvoiceFordue");
			try{Thread.sleep(10000);}catch(Exception e){}
			status = true;
		}catch(Exception e){
			e.printStackTrace();
			status = false;
		}return status;
	}
	public boolean createinvoiceForpaid(){
		boolean status;
		try{
			//Test Case 5 :
			cns.addInvoiceWithMultipleItemsServices(dp.getPaymentStatus());
			cns.takeScreenshot(true, "createinvoiceForpaid");
			status = true;
		}catch(Exception e){
			e.printStackTrace();
			status = false;
		}return status;
	}
	public boolean searchInvoice(){
		//Test Case 9 :
		boolean status;
		try{
			WebElement dblength = runner.driver.findElement(By.xpath("//select[@name='datatable_length']"));
			select = new Select(dblength);
			select.selectByVisibleText("100");
			runner.driver.findElement(By.xpath("//input[@id='validSearch']")).sendKeys("INV34");
			cns.takeScreenshot(true, "searchinvoice");
			try{Thread.sleep(5000);}catch(Exception e){}
			status = true;
		}catch(Exception e){
			e.printStackTrace();
			status = false;
		}return status;
	}
	public boolean sendReminder(){
		//Test case 16 :	
		boolean status;
		try{
			su.searchInvoice();
			//Test case 19:
			runner.driver.findElement(By.xpath("//td/div[contains(text(),'Paid')]"));
			boolean version = runner.driver.findElement(By.xpath("//i[@data-target='#sendReminderModal']")).isEnabled();
			if(version == true){
				
				log.info("Sending reminder for already paid invoice should not be enabled");
			}else{
			runner.driver.findElement(By.xpath("//i[@data-target='#sendReminderModal']")).click();
			wb.waitForAnElement("//button[@class='btn btn-success']");
			runner.driver.findElement(By.xpath("//button[@class='btn btn-success']")).click();
			wb.waitForAnElement("//div[@id='toast-container']");
			WebElement toastmsg = runner.driver.findElement(By.xpath("//div[@id='toast-container']"));
			action = new Actions(runner.driver);
			action.moveToElement(toastmsg).perform();
			cns.takeScreenshot(true, "sendreminder");
			}
			status = true;
		}catch(Exception e){
			e.printStackTrace();
			status = false;
		}return status;
	}
	public boolean viewinvoice(){
		//Test case 11:
		boolean status;
		try{
			su.searchInvoice();
			runner.driver.findElement(By.xpath("//i[@data-target='#editInvoiceModal']")).click();
			WebElement view = runner.driver.findElement(By.xpath("//div[@id='view-inv-html-code']//div//div//div//div//strong[contains(text(),'From :')]"));
			if(view != null){
				log.info("Successfully opened the view of Invoice");
			cns.takeScreenshot(true, "viewinvoice");
			}
			else
				log.info("Did not opened the view of Invoice");
				status = true;
			}catch(Exception e){
				e.printStackTrace();
				status = false;
		}return status;
	}
	public boolean printInvoice(){
		//Test case 12 : 
		boolean status;
		try{
			su.searchInvoice();
			wb.waitForAnElement("//i[@class='#printInvoiceModal']");
			runner.driver.findElement(By.xpath("//i[@class='#printInvoiceModal']")).click();
			try {
				Robot r = new Robot();
				r.keyPress(KeyEvent.VK_CONTROL);
				r.keyPress(KeyEvent.VK_P);
				r.keyRelease(KeyEvent.VK_CONTROL);
				r.keyRelease(KeyEvent.VK_P);
				r.keyPress(KeyEvent.VK_ENTER);
				r.keyRelease(KeyEvent.VK_ENTER);
			} catch (AWTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			status = true;
		}catch(Exception e ){
			e.printStackTrace();
			status = false;
		}return status;
	}
	public void recordPayment(){
		//Test case 14:  //Test case 22: //Test Case 23 :
		su.searchInvoice();
		String amount = runner.driver.findElement(By.xpath("(//tbody/tr[@role='row']/td/strong[contains(text(),'USD')])[1]")).getText();
		String[] iamount = amount.split(" ");
		int invoiceamount = Integer.parseInt(iamount[1]);
		String amount1 = runner.driver.findElement(By.xpath("(//tbody/tr[@role='row']/td/strong[contains(text(),'USD')])[2]")).getText();
		String[] ramount = amount1.split(" ");
		int recievedamount = Integer.parseInt(ramount[1]);
		if(invoiceamount != recievedamount ){
			runner.driver.findElement(By.xpath("//td/div[contains(text(),'Due')]"));
			log.info("The Amount status is correct");
			}
		runner.driver.findElement(By.xpath("//div[@id='tab1']/p/button[@data-target='#addInvoiceModal']"));
		wb.waitForAnElement("//input[@name='paymentDate']");
		runner.driver.findElement(By.xpath("//input[@name='paymentDate']")).click();
		wb.waitForAnElement("//div[@class='datepicker-days']/table/tbody/tr[4]/td[2]");
		runner.driver.findElement(By.xpath("//div[@class='datepicker-days']/table/tbody/tr[4]/td[2]")).click();
		WebElement pon = runner.driver.findElement(By.xpath("//select[@name='paymentMode']"));
		select =  new Select(pon);
		select.selectByVisibleText("Bank Transfer");
		wb.waitForAnElement("//div[@class='input-group']/input[@name='amount']");
		runner.driver.findElement(By.xpath("//div[@class='input-group']/input[@name='amount']")).sendKeys("1");
		runner.driver.findElement(By.xpath("//button[@type='submit']")).click();
		recievedamount = recievedamount+1;
		if(invoiceamount != recievedamount ){
			runner.driver.findElement(By.xpath("//td/div[contains(text(),'Due')]"));
			log.info("The Amount status is correct");
		}
		//Test Case 18:
		else if(invoiceamount == recievedamount){
			
			runner.driver.findElement(By.xpath("//td/div[contains(text(),'Paid')]"));
			runner.driver.findElement(By.xpath("//div[@id='tab1']/p/button[@data-target='#paymentModal']")).isEnabled();
			log.info("Record icon should not be enabled");
			
		}
		
		
	}
	public boolean getAllInvoices(){
		//Test Case 10 :
		boolean status;
		try{
			su.searchInvoice();
			status = true;
		}
		catch(Exception e ){
			e.printStackTrace();
			status = false;
		}return status;
		
	}
	public boolean cancelInvoice(){
		//test case 15:
		boolean status;
		try{
			su.searchInvoice();
			//Test case 20 :
			runner.driver.findElement(By.xpath("//td/div[contains(text(),'Paid')]"));
			boolean version = runner.driver.findElement(By.xpath("//i[@class='fa fa-trash fa-lg cancelInvoice-btn']")).isEnabled();
			if(version == true){
				
				log.info("Cancelling icon for already paid invoice should not be enabled");
			}
			runner.driver.findElement(By.xpath("//i[@class='fa fa-trash fa-lg cancelInvoice-btn']")).click();
			wb.waitForAnElement("//button[@class='btn btn-success']");
			runner.driver.findElement(By.xpath("//button[@class='btn btn-success']")).click();
			try{Thread.sleep(4000);}catch(Exception e){}
			WebElement toastmsg = runner.driver.findElement(By.xpath("//div[@id='toast-container']"));
			action = new Actions(runner.driver);
			action.moveToElement(toastmsg).perform();
			cns.takeScreenshot(true, "cancelInvoice");
			status = true;
		}catch(Exception e){
			e.printStackTrace();
			status=false;
		}return status;
	}
	public boolean editInvoice(){
		//Test case 21: //Test case 17 ;
		boolean status;
		try{
			su.viewinvoice();
			js = (JavascriptExecutor)runner.driver;
			runner.driver.findElement(By.xpath("//div[@class='modal-header']/h4/button[@id='editInvOiceButton']")).click();
			runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='dueDate']")).click();
			runner.driver.findElement(By.xpath("//div[@class='datepicker-days']/table/tbody/tr[4]/td[6]")).click();
			String amount = runner.driver.findElement(By.xpath("(//tbody/tr[@role='row']/td/strong[contains(text(),'USD')])[1]")).getText();
			String[] iamount = amount.split(" ");
			int invoiceamount = Integer.parseInt(iamount[1]);
			String amount1 = runner.driver.findElement(By.xpath("(//tbody/tr[@role='row']/td/strong[contains(text(),'USD')])[2]")).getText();
			String[] ramount = amount1.split(" ");
			int recievedamount = Integer.parseInt(ramount[1]);
			if(invoiceamount != recievedamount ){
				runner.driver.findElement(By.xpath("//td/div[contains(text(),'Due')]"));
				log.info("The Amount status is correct");
			}
			wb.waitForAnElement("//button[text()='Update']");
			WebElement updatebtn = runner.driver.findElement(By.xpath("//button[text()='Update']"));
			try{Thread.sleep(3000);}catch(Exception e){}
			js.executeScript("arguments[0].scrollIntoView(true);", updatebtn);
			updatebtn.click();
			wb.waitForAnElement("//div[@id='toast-container']");
			WebElement toastmsg = runner.driver.findElement(By.xpath("//div[@id='toast-container']"));
			action = new Actions(runner.driver);
			action.moveToElement(toastmsg).perform();
			cns.takeScreenshot(true, "editInvoice");
			status =true;
		}catch(Exception e ){
			e.printStackTrace();
			status = false;
		}return status;
		
	}
	public boolean sendInvoice(){
		//Test Case 13:
		boolean status;
		try{
			js = (JavascriptExecutor)runner.driver;
			su.searchInvoice();
			runner.driver.findElement(By.xpath("//i[@class='fa fa-paper-plane fa-lg sendInvoice']")).click();
			runner.driver.findElement(By.xpath("//input[@name='subject']")).sendKeys("Re : Invoice");
			runner.driver.findElement(By.xpath("//textarea[@placeholder='Content']")).sendKeys("Re : Invoice");
			wb.waitForAnElement("//button[contains(text(),'Send')]");
			WebElement sendbtn = runner.driver.findElement(By.xpath("//button[contains(text(),'Send')]"));
			try{Thread.sleep(3000);}catch(Exception e){}
			js.executeScript("arguments[0].scrollIntoView(true);", sendbtn);
			sendbtn.click();
			wb.waitForAnElement("//i[@class='glyphicon glyphicon-ok-circle fa-lg green']");
			WebElement senticon  = runner.driver.findElement(By.xpath("//i[@class='glyphicon glyphicon-ok-circle fa-lg green']"));
			if(senticon != null){
				log.info("Successfully sent the invoice to the client");
			}
			else
				log.info("Didn't sent the invoice to the client");
			status= true;
		}catch(Exception e){
			e.printStackTrace();
			status =false;
		}return status;
	}
	
}
