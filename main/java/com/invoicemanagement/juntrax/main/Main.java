package com.invoicemanagement.juntrax.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;

import com.invoicemanagement.juntrax.dataproviders.DataProvider;



public class Main {
	public static WebDriver driver;
	public static Helper helper = new Helper();
	public static DataProvider dp = new DataProvider();
	static List<String> keywords = new ArrayList<String>();
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	static {
		System.setProperty("webdriver.chrome.driver","./drivers/windows/chromedriver.exe");
	}

	public static void main(String[] args) throws EncryptedDocumentException, IOException{
		try {
			readTestCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		dp.getData();
		for(String key : keywords){
			helper.performTestOperation(key);
		}
	}
	
	public static List<String> readTestCase(){
		
		try {
			File file = new File("./TestCases/invoice.xlsx");
			FileInputStream fs = new FileInputStream(file);
			Workbook wb = WorkbookFactory.create(fs);
			int activerowcount = wb.getSheet("Sheet1").getLastRowNum();
			for(int i=1;i<=activerowcount;i++){
				if(wb.getSheet("Sheet1").getRow(i).getCell(1).toString().equals("run")){
					String k= wb.getSheet("Sheet1").getRow(i).getCell(2).toString();
						keywords.add(k);
						log.info(k);
					}
				else if(wb.getSheet("Sheet1").getRow(i).getCell(1).toString().equals("")){
					log.info(wb.getSheet("Sheet1").getRow(i).getCell(2).toString() +" "+ "Operation is not running");
				}
				else
					log.info("Operation not allowed");
				}
			} 
		catch (Exception e) {
			
		}
		return keywords;
	}
	
	
}
