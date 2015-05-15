package de.sonnenfeldt.lavisgrafix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
class FaviconController {
    @RequestMapping("/favicon.ico")
    String favicon() {
    	return "redirect:/";
    }
    
    @RequestMapping("/images/loadingAnimation.gif")
    String loadingAnimation() {
    	return "redirect:/";
    } 
}