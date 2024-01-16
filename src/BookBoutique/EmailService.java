package BookBoutique;

import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;

import java.util.*;

// Import the javax.activation.DataHandler class
import javax.activation.DataHandler;
public class EmailService {
	    public static void send(String email, String subjet, String content) {
	        // Your email and password
	        String username = "znatni.yasmine@gmail.com";
	        String password = "sikqtvzfotbowcil";

	        // Set up the properties for the mail session
	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.starttls.required", "true");
	        props.put("mail.smtp.host", "smtp.gmail.com");
	        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	        props.put("mail.smtp.debug", "true");

	        // Create a session with an authenticator
	        Session session = Session.getInstance(props, new Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	        });

	        try {
	            // Create a MimeMessage object
	            Message message = new MimeMessage(session);

	            // Set the sender and recipient addresses
	            message.setFrom(new InternetAddress(username));
	            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

	            // Set the email subject and body
	            message.setSubject(subjet);

	            // Use a DataHandler to handle the email body
	            try {
	                DataHandler handler = new DataHandler(new ByteArrayDataSource(content, "text/plain"));
	                message.setDataHandler(handler);
	                
	                // Send the email
	                Transport.send(message);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }



	            System.out.println("Email sent successfully!");

	        } catch (MessagingException e) {
	            // Handle the exception appropriately, log or perform specific actions
	            e.printStackTrace();
	        }
	    }
}

