package com.scm.Controller;

import com.scm.Entities.RegisteredUser;
import com.scm.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserRegistrationController {

    private final UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    //navigate to the registration page
    @GetMapping("/registration")
    public String registrationPage(Model model) {

        RegisteredUser rUser = new RegisteredUser();

        model.addAttribute("user", rUser);
        return "pages/registrationPage";
    }

    //handler for registering user
    @PostMapping("/do_register")
    public String submitRegistration(Model model, @Valid
    @ModelAttribute("user") RegisteredUser rUser
            , BindingResult result) {


        if (result.hasErrors()) {
            result.getAllErrors().forEach(errors -> {
                System.out.println(((FieldError) errors).getField());
                System.out.println("Message" + errors.getDefaultMessage());
            });
            model.addAttribute("user", rUser);
            return "pages/registrationPage";
        }

        System.out.println("\n\nuser" + rUser + "\n\n");

        rUser.setRole("ROLE_USER");
        rUser.setEnabled(true);

        rUser.setImageUrl("default.jpg");

        this.userService.saveUser(rUser);

        //model.addAttribute("user", new RegisteredUser());
        return "redirect:/registration?success";
    }

}