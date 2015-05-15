package de.sonnenfeldt.lavisgrafix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
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
	public String getLogoutPage(HttpServletRequest request, HttpServletResponse response) {
		    CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
		    SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
		    cookieClearingLogoutHandler.logout(request, response, null);
		    securityContextLogoutHandler.logout(request, response, null);
			
		String view = LOGIN_VIEW;
		log.debug("LoginController::getLogoutPage() returns view: " + view);
		return "redirect:/";
	}			
}
