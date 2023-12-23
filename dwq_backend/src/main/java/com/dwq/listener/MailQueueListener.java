package com.dwq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Map;

@Component
@RabbitListener(queues = "mail")
public class MailQueueListener {

    private static final Logger logger = LoggerFactory.getLogger(MailQueueListener.class);

    @Resource
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Handles sending email messages.
     * @param data Email information.
     */
    @RabbitHandler
    public void sendMailMessage(Map<String, Object> data) {
        try {
            String email = (String) data.get("email");
            Integer code = (Integer) data.get("code");
            String type = (String) data.get("type");

            if (email == null || code == null || type == null) {
                logger.error("Missing email data: {}", data);
                return;
            }

            SimpleMailMessage message = createMessage(type, code, email);
            if (message != null) {
                mailSender.send(message);
                logger.info("Email sent successfully to {}", email);
            }
        } catch (Exception e) {
            logger.error("Error sending email: {}", e.getMessage(), e);
        }
    }

    /**
     * Creates a SimpleMailMessage based on the type.
     * @param type The type of email to send.
     * @param code The code to include in the email.
     * @param email The recipient's email address.
     * @return A SimpleMailMessage or null if type is unknown.
     */
    private SimpleMailMessage createMessage(String type, Integer code, String email) {
        switch (type) {
            case "register":
                return buildMessage("Welcome to Our Website",
                        "Your email registration code is: " + code + ". Valid for 3 minutes. Please do not share this code.",
                        email);
            case "reset":
                return buildMessage("Your Password Reset Email",
                        "Hello, you are performing a password reset. Your code is: " + code + ". Valid for 3 minutes. If this wasn't you, please ignore this email.",
                        email);
            default:
                logger.warn("Unknown email type: {}", type);
                return null;
        }
    }

    /**
     * Builds a SimpleMailMessage.
     * @param subject The subject of the email.
     * @param content The content of the email.
     * @param to The recipient's email address.
     * @return A SimpleMailMessage.
     */
    private SimpleMailMessage buildMessage(String subject, String content, String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        return message;
    }
}
