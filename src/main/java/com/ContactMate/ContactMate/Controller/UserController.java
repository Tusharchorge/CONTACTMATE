package com.ContactMate.ContactMate.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {




     @RequestMapping(value ="/dashboard")
    public String UserDashBoard(){
        System.out.println("UserDashBoard");
        return "user/dashboard";
    }

    @RequestMapping(value = "/profile")
    public String UserProfile(Model model, Authentication authentication){
        return "user/profile";
    }




}
