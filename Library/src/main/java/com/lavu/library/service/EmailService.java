package com.lavu.library.service;

import javax.mail.MessagingException;

import org.springframework.mail.SimpleMailMessage;

import com.lavu.library.model.Order;

public interface EmailService {

	public void sendEmail(SimpleMailMessage email);

	void sendHtmlMessage(Order order) throws MessagingException;

}
