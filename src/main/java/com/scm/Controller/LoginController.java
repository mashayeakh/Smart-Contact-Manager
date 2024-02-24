package com.scm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    //navigate to the login page
    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("title","login-scm");
        return "pages/loginPage";
    }

    //forgot password get handler
    @GetMapping("/forgot")
    public String forgotPassword(){
        return "pages/forgot-password";
    }

    //send otp
    @PostMapping("/send_otp")
    public String send_otp(@RequestParam("email")String email){
        System.out.println("OTP for email: "+email);
        return "";
    }
}
