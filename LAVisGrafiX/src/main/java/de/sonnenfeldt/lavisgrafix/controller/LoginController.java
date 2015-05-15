package de.sonnenfeldt.lavisgrafix.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	static Logger log = Logger.getLogger(LoginController.class.getName());	

	public static String LOGIN_VIEW = "login";
	public static String LOGIN_FAILED_VIEW = "login_failed";
	public static String LOGOUT_VIEW = "logout";
	
	public LoginController() {		
	}

	@RequestMapping({"/login"})
	public String getLoginPage(Model model) {
		String view = LOGIN_VIEW;
		log.debug("LoginController::getLoginPage() returns view: " + view);
		return view;
	}		
		
	@RequestMapping({"/logout"})
	public String getLogoutPage(Model model) {
		String view = LOGIN_VIEW;
		log.debug("LoginController::getLogoutPage() returns view: " + view);
		return view;
	}			
}
