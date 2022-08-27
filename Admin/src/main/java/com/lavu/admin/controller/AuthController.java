package com.lavu.admin.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lavu.library.dto.AdminForm;
import com.lavu.library.model.Admin;
import com.lavu.library.service.AdminService;
import com.lavu.library.service.EmailService;

@Controller
public class AuthController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("title", "Đăng nhập quản trị");
		return "login";
	}

	@GetMapping("/register")
	public String addUser(Model model) {
		model.addAttribute("title", "Đăng ký quản trị");
		model.addAttribute("adminDto", new AdminForm());
		return "register";
	}

	@PostMapping("/do-register")
	public String doAddUser(@Valid @ModelAttribute("adminDto") AdminForm dto, BindingResult result, Model model) {
		try {
			if (result.hasErrors()) {
				model.addAttribute("adminDto", dto);
				result.toString();
				return "register";
			}
			model.addAttribute("title", "Đăng ký quản trị");
			String username = dto.getUsername();
			Admin admin = adminService.findByUsername(username);

			if (admin != null) {
				model.addAttribute("adminDto", dto);
				model.addAttribute("emailError", "Email đã tồn tại!");
				return "register";
			}

			if (dto.getPassword().equals(dto.getRepeatPassword())) {
				dto.setPassword(passwordEncoder.encode(dto.getPassword()));
				adminService.save(dto);
				model.addAttribute("success", "Đăng ký người dùng thành công!");
				model.addAttribute("adminDto", dto);
			} else {
				model.addAttribute("adminDto", dto);
				model.addAttribute("passwordError", "Mật khẩu kiểm tra lại không khớp!");
				return "register";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("failed", "Lỗi server!");
		}
		return "register";
	}

	
	@RequestMapping(value = "/forgot", method = RequestMethod.GET)
	public String displayForgotPasswordPage() {
		return "forgot-password";
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public String processForgotPasswordForm(Model model, @RequestParam("username") String username,
			HttpServletRequest request) {
		Admin admin = adminService.findByUsername(username);
		if (admin == null) {
			model.addAttribute("error", "Email không chính xác hoặc không tồn tại");
		} else {
			admin.setResetToken(UUID.randomUUID().toString());
			adminService.updateToken(admin);
			String appUrl = request.getScheme() + "://" + request.getServerName()+":8019";
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("vuanhle1509@gmail.com");
			passwordResetEmail.setTo(admin.getUsername());
			passwordResetEmail.setSubject("Yêu cầu reset password");
			passwordResetEmail.setText("Vui lòng click đường dẫn dưới đây:\n" + appUrl + "/reset?token="
					+ admin.getResetToken());

			emailService.sendEmail(passwordResetEmail);
			model.addAttribute("success", "Reset password đường dẫn đã được gửi đến: " + username);
		}

		return "forgot-password";
	}


	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public String displayResetPasswordPage(Model model, @RequestParam("token") String token) {

		Admin admin = adminService.findUserByResetToken(token);

		if (admin!=null) {
			model.addAttribute("token", token);
		} else {
			model.addAttribute("error", "Lỗi! Server");
		}
		return "reset-password";
	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public String setNewPassword(Model model, @RequestParam Map<String, String> requestParams,
			RedirectAttributes redir,HttpServletRequest request) {

		Admin resetAdmin = adminService.findUserByResetToken(requestParams.get("token"));
		if (resetAdmin != null) {
			resetAdmin.setPassword(passwordEncoder.encode(requestParams.get("password")));
			resetAdmin.setResetToken(null);
			adminService.updateToken(resetAdmin);
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
