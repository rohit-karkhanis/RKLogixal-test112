package com.Utilities;

import com.operations.Common.FireClass;
import com.sun.mail.smtp.SMTPTransport;
import com.testcases.driverscripts.Execute_MainScript;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Properties;
import javax.activation.*;
import javax.mail.*;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendStatusReport1 {
	
	public static void main(String[] args) {
	//public void performTask() {
		
		Date Sdate=Execute_MainScript.Startdate;
		Date edate=Execute_MainScript.Enddate;
		int passcount = Execute_MainScript.Passcounter;
		int skipcount = Execute_MainScript.skipcounter;
		int failcount = FireClass.failcounter;
		int TotalTC = Execute_MainScript.TotalTCcounter;

		// Mention the Recipient's email address
		String to = "rohit.karkhanis@logixal.com";
		// Mention the Sender's email address
		String from = "jenkins@logixal.com";
		// Mention the SMTP server address. Below Gmail's SMTP server is being used to send email
		String host = "smtp.office365.com";
		String msg="Test execution status as below :"+"<br />"+"<br />"
				+ "<b>Test execution Start Time : <b>"+Sdate+"<br />"
				+ "<b>Test execution End Time : <b>"+edate+"<br />"+"<br />"+"<br />"
				+ "<table width='60%' border='1' align='Left'>"
				+ "<tr align='center'>"
				+ "<th bgcolor='#D3D3D3'><b>Total number of testcases<b></th>"
				+ "<th bgcolor='#00FF00'><b>Passed Testcases<b></th>"
				+ "<th bgcolor='#FF0000'><b>Failed testcases<b></th>"
				+ "<th bgcolor='#FFA500'><b>Skipped testcases<b></th>"
				+ "</tr>"
				+ "<tr align='center'>"
				+ "<td>"+TotalTC+"</td>"
				+ "<td>"+passcount+"</td>"
				+ "<td>"+failcount+"</td>"
				+ "<td>"+skipcount+"</td>"
				+ "</tr></table>"+"<br />"+"<br />"
				+ "kindly find attached HTML report for more information about execution summary"+"<br />"+"<br />";
		// Get system properties
		Properties properties = System.getProperties();
		

		// Setup mail server
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("jenkins@logixal.com", "Qwert@123");
			}
		});
		// Used to debug SMTP issues
		session.setDebug(true);
		try {
			
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);
			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			// Set Subject: header field
			message.setSubject("This is the Subject Line!");
			// Now set the actual message
			/*message.setText("This is actual message\n "
	            		+  "<table width='100%' border='1' align='center'>"
	                    + "<tr align='center'>"
	                    + "<td><b>Product Name <b></td>"
	                    + "<td><b>Count<b></td>"
	                    + "</tr>");*/
			//message.setContent(msg, "text/html");
			
			 MimeBodyPart messageBodyPart = new MimeBodyPart();

		        Multipart multipart = new MimeMultipart();

		        messageBodyPart = new MimeBodyPart();
		        String file = "D:\\ROhit\\Rohit\\Automation\\Demo\\KamanProd\\Reports\\TestSummary_Report.html";
		        String fileName = "TestSummary_Report.html";
		        DataSource source = new FileDataSource(file);
		        messageBodyPart.setDataHandler(new DataHandler(source));
		        messageBodyPart.setFileName(fileName);
		        //messageBodyPart.setContent(msg, "text/html");
		        //messageBodyPart.setText("sdsadsasdjbhsadbhasdbsa");
		        multipart.addBodyPart(messageBodyPart);
		        
		        message.setContent(multipart, msg);
			
			System.out.println("sending...");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
