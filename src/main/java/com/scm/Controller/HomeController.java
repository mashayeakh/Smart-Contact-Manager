package com.scm.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //navigate to index page
    @GetMapping("/")
    public String homePage(Model model){

        model.addAttribute("title","Home-SCM");
        return "pages/index";
    }

    //navigate to about page
    @GetMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("title","About-SCM");
        return "pages/aboutPage";
    }

//    @GetMapping("/not_found")
//    public String notFoundPage(Model model){
//        model.addAttribute("title","Not Found");
//        return "pages/notFoundPage";
//    }

    @GetMapping("/**")
    public String handleNotFound() {
        // Redirect to the custom "not found" page
        return "pages/notFoundPage";
    }
}
