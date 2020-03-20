package com.invoicemanagement.juntrax.mailgenerator;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.http.ParseException;
import java.io.FileNotFoundException;
import java.util.Properties;

import javax.mail.BodyPart;

import javax.mail.Message;

import javax.mail.MessagingException;

import javax.mail.Multipart;

import javax.mail.PasswordAuthentication;

import javax.mail.Session;

import javax.mail.Transport;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;

import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMultipart;


public class MailGenerator {

	public static void sendReport(String content,String mailinglist) {
	
	        String to =  mailinglist;
	
	        final String username = "naveenkumar.upputuri@gmail.com";
	

	        final String password = "emp5@juntrax.com";
	
	        // String host = "localhost";
	
	        Properties props = new Properties();

	        props.put("mail.smtp.host", "smtp.gmail.com");
	        
	        props.put("mail.smtp.port", "465");
	        props.put("mail.smtp.starttls.enable","true");
	        props.put("mail.smtp.debug", "true");
	        props.put("mail.smtp.auth", "true");
	        
	        props.put("mail.smtp.socketFactory.port", "465");
	        
	        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.put("mail.smtp.socketFactory.fallback", "false");

	        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

	            @Override

	            protected PasswordAuthentication getPasswordAuthentication() {

	                return new PasswordAuthentication(username, password);

	            }

	        });

	        try {

	            Message message = new MimeMessage(session);

	            message.setFrom(new InternetAddress(username));
	            String[] strArr = to.split(";");

	            InternetAddress[] address = new InternetAddress[strArr.length];

	            for (int i = 0; i < strArr.length; i++) {

	                address[i] = new InternetAddress(strArr[i]);

	            }

	            message.setRecipients(Message.RecipientType.TO, address);

	            message.setSubject("");

	            // message.setSubject("KpiValidation "+" Cluster Report");

	            Multipart mp = new MimeMultipart();

	            BodyPart messageBodyPart = new MimeBodyPart();
	
	            System.out.println(content.toString());

	            messageBodyPart.setContent(content.toString(), "text/html");
	
	            messageBodyPart.setDisposition(MimeBodyPart.INLINE);
	
	            Multipart multipart = new MimeMultipart();
	
	            multipart.addBodyPart(messageBodyPart);
	
	            // Send the complete message parts

	            message.setContent(multipart);
	
	            Transport.send(message);
	
	            System.out.println("Mail Sent.");
	
	        } catch (MessagingException e) {
	
	            throw new RuntimeException(e);

	        }

	    }
	
	 public static String  readEmailReportFile()
	 {
		
		 
		 String content = "";
		    try {
		        BufferedReader in = new BufferedReader(new FileReader(""));
		        String str;
		        while ((str = in.readLine()) != null) {
		            content +=str;
		        }
		        in.close();
		    } catch (IOException e) {
		    }
		   
		    return content;
	 }
	
}

