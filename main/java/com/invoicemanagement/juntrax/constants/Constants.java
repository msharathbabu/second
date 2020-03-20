package com.invoicemanagement.juntrax.constants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.google.common.io.Files;
import com.invoicemanagement.juntrax.main.Main;
import com.invoicemanagement.juntrax.utils.WebUtil;

public class Constants {
	static Main runner = new Main();
	public static Constants cs = new Constants();
	JavascriptExecutor js;
	Select select;
	WebUtil wb = new WebUtil();
	public boolean addInvoiceWithoutDetails(){
		 boolean status ;
		try{
			js = (JavascriptExecutor)runner.driver;
			Actions actions = new Actions(runner.driver);
			runner.driver.findElement(By.xpath("//div[@id='tab1']/p/button[@data-target='#addInvoiceModal']")).click();
			wb.waitForAnElement("//button[@id='submit-add-inv-btn']");
			WebElement addbtn = runner.driver.findElement(By.xpath("//button[@id='submit-add-inv-btn']"));
			try{
                        Thread.sleep(7000);
                        }
                        catch(Exception e){}
			js.executeScript("arguments[0].scrollIntoView(true);", addbtn);
			addbtn.click();
			WebElement toastmsg = runner.driver.findElement(By.xpath("//div[@id='toast-container']"));
			actions.moveToElement(toastmsg).perform();
			status = true;
		}catch(Exception e){
			e.printStackTrace();
			status = false;
		}return status;
	}
	public boolean addInvoiceWithAllMandatoryDetails(String paymentstatus){
		boolean status;
		try{
			runner.driver.findElement(By.xpath("//div[@id='tab1']/p/button[@data-target='#addInvoiceModal']")).click();
			wb.waitForAnElement("//select[@name='clientId']");
			WebElement clientid = runner.driver.findElement(By.xpath("//select[@name='clientId']"));
			select =  new Select(clientid);
			select.selectByVisibleText("Matrix Corp");
			runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='invoiceDate']")).click();
			runner.driver.findElement(By.xpath("//div[@class='datepicker-days']/table/tbody/tr[4]/td[4]")).click();
			runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='dueDate']")).click();
			runner.driver.findElement(By.xpath("//div[@class='datepicker-days']/table/tbody/tr[4]/td[5]")).click();
			WebElement pon = runner.driver.findElement(By.xpath("//select[@name='poNumber']"));
			select =  new Select(pon);
			select.selectByVisibleText("101");
			WebElement statuslist = runner.driver.findElement(By.xpath("//select[@name='status']"));
			select =  new Select(statuslist);
			select.selectByVisibleText(paymentstatus);
			wb.waitForAnElement("//input[@name='box-attachment']");
			runner.driver.findElement(By.xpath("//input[@name='box-attachment']")).click();
			try{Thread.sleep(5000);}catch(Exception e){}
			//TestCase 4:
			try {
				Runtime.getRuntime().exec("./AutoITScripts/select.exe");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try{Thread.sleep(5000);}catch(Exception e){}
			WebElement addbtn = runner.driver.findElement(By.xpath("//button[@id='submit-add-inv-btn']"));
			try{Thread.sleep(7000);}catch(Exception e){}
//			js.executeScript("arguments[0].scrollIntoView(true);", addbtn);
//			try{Thread.sleep(10000);}catch(Exception e){}
			status = true;
		}catch(Exception e){
			e.printStackTrace();
			status = false;
		}return status;
	}
	public boolean addInvoiceWithMultipleItemsServices(String paymentstatus){
		boolean status;
		try{
			js =  (JavascriptExecutor)runner.driver;
			cs.addInvoiceWithAllMandatoryDetails(paymentstatus);
			WebElement item = runner.driver.findElement(By.xpath("//select[@name='items.0.id']"));
			js.executeScript("arguments[0].scrollIntoView(true);", item);
			select=new Select(item);
			select.selectByVisibleText("First Product");
			runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='items.0.description']")).clear();
			runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='items.0.description']")).sendKeys("Hello Hello");
			String unitprice1 = runner.driver.findElement(By.xpath("items.0.unitPrice")).getText();
			WebElement totaltax = runner.driver.findElement(By.xpath("//select[@name='items.0.totalTax']"));
			select=new Select(totaltax);
			select.selectByVisibleText("VAT - 5%");
			runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='items.0.discount']")).sendKeys("2");
			String itemdiscountpercentage1 =runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='items.0.discount']")).getText();
			runner.driver.findElement(By.xpath("//button[text()='Add Another']")).click();
			WebElement item1 = runner.driver.findElement(By.xpath("//select[@name='items.1.id']"));
			js.executeScript("arguments[0].scrollIntoView(true);", item1);
			select=new Select(item1);
			select.selectByVisibleText("Second Product");
			runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='items.1.description']")).clear();
			runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='items.1.description']")).sendKeys("Hello Hello");
			String unitprice2 = runner.driver.findElement(By.xpath("items.1.unitPrice")).getText();
			WebElement totaltax1 = runner.driver.findElement(By.xpath("//select[@name='items.1.totalTax']"));
			select=new Select(totaltax1);
			select.selectByVisibleText("VAT - 5%");
			runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='items.1.discount']")).sendKeys("2");
			String itemdiscountpercentage2 = runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='items.1.discount']")).getText();
			WebElement service1 = runner.driver.findElement(By.xpath("//select[@name='services.0.id']"));
			select=new Select(service1);
			select.selectByVisibleText("LS103 Jack Rapper");
			WebElement project1 = runner.driver.findElement(By.xpath("//select[@name='services.0.project']"));
			select=new Select(project1);
			select.selectByVisibleText("Version 1.0");
			runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='services.0.description']")).sendKeys("Hello Hello");
			String servicebillamount1 = runner.driver.findElement(By.xpath("//input[@name='services.0.billRate']")).getText();
			WebElement servicetax1 = runner.driver.findElement(By.xpath("//select[@name='services.0.totalTax']"));
			select=new Select(servicetax1);
			select.selectByVisibleText("VAT - 5%");
			runner.driver.findElement(By.xpath("//form[@id='addInvoiceForm']//div[@id='services-fields']//button[text()='Add Another']")).click();
			WebElement service2 = runner.driver.findElement(By.xpath("//select[@name='services.1.id']"));
			select=new Select(service2);
			select.selectByVisibleText("LS103 Jack Rapper");
			WebElement project2 = runner.driver.findElement(By.xpath("//select[@name='services.1.project']"));
			select=new Select(project2);
			select.selectByVisibleText("Version 1.0");
			runner.driver.findElement(By.xpath("//div[@class='form-group']/input[@name='services.1.description']")).sendKeys("Hello Hello");
			String servicebillamount2 = runner.driver.findElement(By.xpath("//input[@name='services.0.billRate']")).getText();
			WebElement servicetax2 = runner.driver.findElement(By.xpath("//select[@name='services.1.totalTax']"));
			select=new Select(servicetax2);
			select.selectByVisibleText("VAT - 5%");
			WebElement taxgroup = runner.driver.findElement(By.xpath("//select[@class='form-control change-tax-ai']"));
			try{Thread.sleep(5000);}catch(Exception e){}
			js.executeScript("arguments[0].scrollIntoView(true);", taxgroup);
			select=new Select(taxgroup);
			select.selectByVisibleText("VAT-5%");
//			runner.driver.findElement(By.xpath("//button[@id='submit-add-inv-btn']")).click();
			status = true;
		}catch(Exception e){
			e.printStackTrace();
			status = false;
		}return status;
	}
	
	public  void takeScreenshot(boolean status,String operation){
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyyHHmmss");
		Date date = new Date();
		System.out.println(formatter.format(date));
		if(status == true){
		TakesScreenshot ts = (TakesScreenshot)runner.driver; 
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		File dstfile = new File("./Screenshots/"+formatter.format(date)+operation+".png");
		try {
			Files.copy(srcFile,dstfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}	
	}
	
	public int calculateTotalAmount(String value,String discount){
		int unitprice = Integer.parseInt(value);
		int itemdiscount = Integer.parseInt(discount);
		int totalitemamount= unitprice/100*itemdiscount;
		return totalitemamount;
	}
	
}
