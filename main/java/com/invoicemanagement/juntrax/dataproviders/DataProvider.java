package com.invoicemanagement.juntrax.dataproviders;

import java.io.File;
import java.io.FileInputStream;


import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class DataProvider {
	private static String url;
	private static String email;
	private static String password;
	private static String paymentstatus;
	
	
	public void setUrl(String url){
		this.url=url;
	}
	public String getUrl(){
		return url;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return email;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return password;
	}
	public void setPaymentStatus(String paymentstatus){
		this.paymentstatus = paymentstatus;
	}
	public String getPaymentStatus(){
		return paymentstatus;
	}
	
	public void getData(){
		DataProvider dp = new DataProvider();
		try {
			File file = new File("./TestCases/invoice.xlsx");
			FileInputStream fs = new FileInputStream(file);
			Workbook wb = WorkbookFactory.create(fs);
			int activerowcount = wb.getSheet("Sheet1").getLastRowNum();
			for(int i=1;i<=activerowcount;i++){
				if(wb.getSheet("Sheet1").getRow(i).getCell(2).toString().equals("launch")){
					dp.setUrl(wb.getSheet("Sheet1").getRow(i).getCell(3).toString());
					}
				else if(wb.getSheet("Sheet1").getRow(i).getCell(2).toString().equals("login")){
					dp.setEmail(wb.getSheet("Sheet1").getRow(i).getCell(3).toString());
					dp.setPassword(wb.getSheet("Sheet1").getRow(i).getCell(4).toString());
				}
				else if(wb.getSheet("Sheet1").getRow(i).getCell(2).toString().equals("createinvoicefordue")){
					dp.setPaymentStatus(wb.getSheet("Sheet1").getRow(i).getCell(3).toString());
				}
				else if(wb.getSheet("Sheet1").getRow(i).getCell(2).toString().equals("createinvoiceforpaid")){
					dp.setPaymentStatus(wb.getSheet("Sheet1").getRow(i).getCell(3).toString());
				}
				}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	

}
