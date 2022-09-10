package com.lavu.customer.controller;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lavu.library.model.Customer;
import com.lavu.library.service.CategoryService;
import com.lavu.library.service.CustomerService;
import com.lavu.library.service.EmailService;

@Controller
public class AccountController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/account")
	public String accountHome(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		model.addAttribute("customer", customer);
		model.addAttribute("title", "Thông tin tài khoản");
        model.addAttribute("categories", categoryService.getCategoriesByParentIsNull());
		return "account-infor";
	}

	@RequestMapping(value = "/account/update-infor", method = { RequestMethod.GET, RequestMethod.PUT })
	public String updateCustomer(@ModelAttribute("customer") Customer customer, Model model,
			RedirectAttributes redirectAttributes, Principal principal) {
		if (principal == null) {
			return "redirect:/login";
		}
		Customer customerSaved = customerService.saveInfor(customer);
		model.addAttribute("title", "Thông tin tài khoản");
		redirectAttributes.addFlashAttribute("customer", customerSaved);
		redirectAttributes.addFlashAttribute("success", "Cập nhật thành công!");
		return "redirect:/account";
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public String displayForgotPasswordPage(Model model) {
		model.addAttribute("title", "Quên mật khẩu");
        model.addAttribute("categories", categoryService.getCategoriesByParentIsNull());
		return "forgot-password";
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public String processForgotPasswordForm(Model model, @RequestParam("username") String username,
			HttpServletRequest request) {
		Customer customer = customerService.findByUsername(username);
		if (customer == null) {
			model.addAttribute("error", "Email không chính xác hoặc không tồn tại");
		} else {
			customer.setResetToken(UUID.randomUUID().toString());
			customerService.updateToken(customer);
			String appUrl = request.getScheme() + "://" + request.getServerName() + ":8020";
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			
			passwordResetEmail.setFrom("choithugame3x@gmail.com");
			passwordResetEmail.setTo(customer.getUsername());
			passwordResetEmail.setSubject("Yêu cầu reset password");
			passwordResetEmail.setText(
					"Vui lòng click đường dẫn dưới đây:\n" + appUrl + "/reset?token=" + customer.getResetToken());

			emailService.sendEmail(passwordResetEmail);
			model.addAttribute("success", "Reset password đường dẫn đã được gửi đến: " + username);
		}

		return "forgot-password";
	}

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public String displayResetPasswordPage(Model model, @RequestParam("token") String token) {
		Customer customer = customerService.findUserByResetToken(token);
		if (customer != null) {
			model.addAttribute("token", token);
		} else {
			model.addAttribute("error", "Lỗi! Server");
		}
		model.addAttribute("title", "Mật khẩu mới");
        model.addAttribute("categories", categoryService.getCategoriesByParentIsNull());
		return "reset-password";
	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public String setNewPassword(Model model, @RequestParam Map<String, String> requestParams, RedirectAttributes redir,
			HttpServletRequest request) {

		Customer resetCustomer = customerService.findUserByResetToken(requestParams.get("token"));
		if (resetCustomer != null) {
			resetCustomer.setPassword(passwordEncoder.encode(requestParams.get("password")));
			resetCustomer.setResetToken(null);
			customerService.updateToken(resetCustomer);
			redir.addFlashAttribute("success", "Reset password thành công. Bạn có thể đăng nhập lại.");
			return "redirect:/login";
		} else {
			model.addAttribute("error", "Lỗi! Server");
			return "reset-password";
		}
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public String handleMissingParams(MissingServletRequestParameterException ex) {
		return "redirect:/login";
	}
}
