package com.example.demo.email;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailUtil {
	@Autowired
    private static JavaMailSender javaMailSender;
	
	 public static void sendEmail(String emailId) {

	        SimpleMailMessage msg = new SimpleMailMessage();
	        //msg.setTo("example_1@gmail.com", "example_2gmail.com");
	        msg.setTo(emailId);

	        msg.setSubject("Testing Email from Spring Boot");
	        msg.setText("Test Email from Spring boot");

	        javaMailSender.send(msg);

	    }
	 
	 void sendEmailWithAttachment() throws MessagingException, IOException {

	        MimeMessage msg = javaMailSender.createMimeMessage();

	        // true = multipart message
	        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
	        helper.setTo("example_1@gmail.com");

	        helper.setSubject("Testing send Email from Spring Boot");

	        // default = text/plain
	        //helper.setText("Check attachment for image!");

	        // true = text/html
	        helper.setText("<h1>Check attachment for image!</h1>", true);

	        helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));

	        javaMailSender.send(msg);

	    }

}
