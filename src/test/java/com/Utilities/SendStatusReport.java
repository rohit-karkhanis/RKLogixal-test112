package com.Utilities;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.operations.Common.FireClass;
import com.operations.Common.ReadUserconfig;
import com.testcases.driverscripts.Execute_MainScript;

public class SendStatusReport {
	//public static void main(String[] args) {

	public void performTask() throws IOException {


		Date date;
		SimpleDateFormat Time;
		Date Sdate=Execute_MainScript.Startdate;
		Date edate=Execute_MainScript.Enddate;
		int passcount = Execute_MainScript.Passcounter;
		int skipcount = Execute_MainScript.skipcounter;
		int failcount = FireClass.failcounter;
		int TotalTC = Execute_MainScript.TotalTCcounter;
		String ExecutionStatus = null;
		String filename = System.getProperty("user.dir")+"/test-output/TestSummary_Report.html";

		ReadUserconfig ruser = new ReadUserconfig();
		ruser.getUserConfig();
		String msg="Test execution status as below :"+"<br />"+"<br />"
				+ "<b>Test execution Start Time : </b>"+Sdate+"<br />"
				+ "<b>Test execution End Time : </b>"+edate+"<br />"+"<br />"
				+ "<table width='60%' border='1' align='Left'>"
				+ "<tr align='center'>"
				+ "<th bgcolor='#D3D3D3'><b>Total number of testcases<b></th>"
				+ "<th bgcolor='#00FF00'><b>Passed Testcases<b></th>"
				+ "<th bgcolor='#FF0000'><b>Failed testcases<b></th>"
				+ "<th bgcolor='#FFA500'><b>Skipped testcases<b></th>"
				+ "</tr>"
				+ "<tbody>"
				+ "<tr align='center'>"
				+ "<td>"+TotalTC+"</td>"
				+ "<td>"+passcount+"</td>"
				+ "<td>"+failcount+"</td>"
				+ "<td>"+skipcount+"</td></tr></tbody></table>"
				+ "<br />"+"<br />"+"<br />"+"<br />"+"<br />"+"<br />"
				+ "<p>Kindly find attached HTML report for more information about Test execution summary.</p>"+"<br />"+"<br />";

		/*Recipient's email ID 
		String to = "rohit.karkhanis@logixal.com";
		 Sender's email ID 
		String from = "jenkins@logixal.com";
		final String username = "jenkins@logixal.com";
		final String password = "Qwert@123";
		String host = "smtp.office365.com";*/

		String to = ruser.emailTo;
		String from = ruser.emailFrom;
		final String username = ruser.SMTPUser;
		final String password = ruser.SMTPPassword;
		String host = ruser.SMTPHost;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		date = new Date() ;
		Time = new SimpleDateFormat("MMMM dd yyyy") ;



		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);


			message.setFrom(new InternetAddress(from));

			InternetAddress[] parse = InternetAddress.parse(to , true);
			message.setRecipients(javax.mail.Message.RecipientType.TO,  parse);
			/*message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));*/

			int SendPassStatus = PassTCpercent(passcount,TotalTC);
			int SendFailStatus = FailTCpercent(failcount,TotalTC);

			if(SendPassStatus == 100) {

				ExecutionStatus = "PASSED";
			}

			else if (SendPassStatus >= 80){

				ExecutionStatus = "CONDITIONALLY PASSED";
			}

			else if (SendPassStatus < 80) {

				ExecutionStatus = "FAILED";
			}


			//message.setSubject("ECTEST Sanity : Automation Test Status as on "+Time.format(date)+" - "+ ExecutionStatus+" : "+SendPassStatus+"% Passed"+","+SendFailStatus+"% Failed");

			message.setSubject("ECTEST Sanity : Automation Test Status as on "+Time.format(date)+" - "+ ExecutionStatus);

			//System.out.println("ECTEST Sanity : Automation Test Status as on "+Time.format(date)+" - "+ ExecutionStatus);


			BodyPart messageBodyPart = new MimeBodyPart();


			messageBodyPart.setText(msg);
			messageBodyPart.setContent(msg,"text/html");


			Multipart multipart = new MimeMultipart();


			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();

			String file = "TestSummary_Report.html";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(file);
			multipart.addBodyPart(messageBodyPart);


			message.setContent(multipart);


			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static int PassTCpercent(int passTCcount , int TotalTCcount) {
		float Passpercentage; 
		float fpassTCcount = passTCcount;
		float fTotalTCcount = TotalTCcount;

		Passpercentage = (float)(fpassTCcount/fTotalTCcount*100);
		System.out.println(Passpercentage);

		Math.round(Passpercentage);

		//System.out.println(Math.round(Passpercentage));

		return Math.round(Passpercentage);
	}

	public static  int FailTCpercent(int FailTCcount , int TotalTCcount) {
		float Failpercentage;
		float fFailTCcount = FailTCcount;
		float fTotalTCcount = TotalTCcount;

		Failpercentage = (float)(fFailTCcount/fTotalTCcount*100);

		Math.round(Failpercentage);

		//System.out.println(Math.round(Failpercentage));

		return Math.round(Failpercentage);
	}
}