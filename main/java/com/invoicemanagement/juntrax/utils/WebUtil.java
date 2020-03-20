package com.invoicemanagement.juntrax.utils;

import org.openqa.selenium.By;
import com.invoicemanagement.juntrax.main.Main;

public class WebUtil
{	
	static boolean status = false;
	Main runner = new Main();
	public void waitForAnElement(String xpath){
		while(status == false){
			try{
			runner.driver.findElement(By.xpath(xpath));
			status = true;
			break;
			}catch(Exception e){
				status = false;
			
				if(e.getMessage().contains("org.openqa.selenium.StaleElementReferenceException")){
					status = true;
				}
			}
			
			}
		status = false;
		}
}

