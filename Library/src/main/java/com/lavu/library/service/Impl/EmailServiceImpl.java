package com.lavu.library.service.Impl;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.lavu.library.model.Order;
import com.lavu.library.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Async
	public void sendEmail(SimpleMailMessage email) {
		mailSender.send(email);
	}
	
    @Override
	public void sendHtmlMessage(Order order) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        helper.setFrom("choithugame3x@gmail.com");
        helper.setTo(order.getCustomer().getUsername().trim());
        helper.setSubject("Hóa đơn mua hàng");
        Context context = new Context();
        context.setVariable("order",order);
        context.setVariable("list",order.getOrderDetail());
        String html = templateEngine.process("order-mail",context);
        helper.setText(html, true);
        mailSender.send(message);
    }
    
	
}
