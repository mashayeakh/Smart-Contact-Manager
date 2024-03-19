package com.scm.Controller;

import com.scm.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Random;

@Controller
public class LoginController {

//    private UserService emailService;

//    public LoginController(UserService emailService){
//        this.emailService=emailService;
//    }

    //navigate to the login page
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("title", "login-scm");
        return "pages/loginPage";
    }

//    //forgot password get handler
//    @GetMapping("/forgot")
//    public String forgotPassword() {
//        return "pages/forgot-password";
//    }
//
//    //send otp
//    @PostMapping("/send-otp")
//    public String sendOtp(@RequestParam("email") String email, HttpSession session, RedirectAttributes redirectAttributes) {
//        // Generate OTP
//        int otp = generateOtp();
//
//        // Send OTP to user's email
//        boolean emailSent = emailService.sendOtpEmail(email, otp);
//
//        if (emailSent) {
//            // Store OTP and email in session
//            session.setAttribute("otp", otp);
//            session.setAttribute("email", email);
//            return "redirect:/verify-otp";
//        } else {
//            // Handle email sending failure
//            redirectAttributes.addFlashAttribute("error", "Failed to send OTP email. Please try again.");
//            return "redirect:/forgot-password";
//        }
//    }
//
//    @GetMapping("/verify-otp")
//    public String showVerifyOtpPage() {
//        // Show the verify OTP page
//        return "verify_otp";
//    }
//
//    @PostMapping("/verify-otp")
//    public String verifyOtp(@RequestParam("otp") String otp, HttpSession session) {
//        // Retrieve the OTP stored in the session
//        int storedOtp = (int) session.getAttribute("otp");
//
//        // Verify if the entered OTP matches the stored OTP
//        if (otp.equals(String.valueOf(storedOtp))) {
//            // OTP is correct, redirect to the reset password page
//            return "redirect:/reset-password";
//        } else {
//            // OTP is incorrect, redirect back to the verify OTP page with an error message
//            return "redirect:/verify-otp?error=incorrect";
//        }
//    }
//
//    private int generateOtp() {
//        Random random = new Random();
//        int max = 9999, min = 1000;
//        return random.nextInt(max - min + 1) + min;
//    }

}
