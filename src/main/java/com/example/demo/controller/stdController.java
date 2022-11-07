package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.stdLog;
import com.example.demo.model.student;
import com.example.demo.stdService.serviceDb;

@Controller
public class stdController {
@Autowired
serviceDb sdb;
	// index returning ..........................................
	@GetMapping("/index")
	public String indexreturnPage() {
		return "index";
	}

	@RequestMapping("/")
	public String indexPage() {
		return "index";
	}
	//register user here ...............................................
	@GetMapping("login")
	public String loginPage() {
		return "login";
	}
	@RequestMapping("regUser")
	public String regUserPage(Model m) {
		m.addAttribute("student",new student());
		m.addAttribute("stdLog", new stdLog());
		return "registerUser";
	}
	@PostMapping("registerUser")
	public String registerUser(@ModelAttribute("student")student s,@ModelAttribute("stdLog")stdLog sl) {
		sdb.regUserData(s,sl);
		return "index";
	}
	
	//login page..................................................
	
	@PostMapping("logUser")
	public String LoginUser(@RequestParam String stdEmail,@RequestParam String stdpassword,HttpSession session,Model m) {
		List<stdLog> log=sdb.chkLogStd(stdEmail);
		if(log.isEmpty()) {
			return "index";
		}
		else {
			stdLog logdtl=log.get(0);
						String email=logdtl.getStdEmail();
			String pass=logdtl.getStdpassword();
			session.setAttribute("studentDetails", logdtl);
			if(email.equals("admin@gmail.com")) {
				if(pass.equals("admin")) {
				List<stdLog> alldtl=sdb.getAllDetails();
				m.addAttribute("studentAdminUser", alldtl);
				return "admin";
				
			}
			else {
				
				return "index";
			}}
		else {
				m.addAttribute("studentdetals", logdtl);
				return "profile";
			}
		}
		
	}
}
