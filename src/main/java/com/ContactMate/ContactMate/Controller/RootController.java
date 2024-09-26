package com.ContactMate.ContactMate.Controller;

import com.ContactMate.ContactMate.Entities.User;
import com.ContactMate.ContactMate.Helper.EmailHelper;
import com.ContactMate.ContactMate.Services.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class RootController {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null) {
            return;
        }
        System.out.println("Adding logged in user information to the model");

        String username = EmailHelper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}", username);
        // database se data ko fetch : get user from db :
        User user1 = userService.getUserByEmail(username);
        System.out.println(user1);
        System.out.println(user1.getName());
        System.out.println(user1.getEmail());
        model.addAttribute("loggedInUser", user1);


    }
}