package com.Contax.Controller;

import com.Contax.Entities.User;
import com.Contax.Helper.EmailHelper;
import com.Contax.Services.UserService;
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
        System.out.println("Adding logged-in user information to the model");

        String username = EmailHelper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}", username);

        // Fetch user from database:
        User user1 = userService.getUserByEmail(username);

        // Null check to avoid NullPointerException:
        if (user1 == null) {
            logger.error("No user found with email: {}", username);
            return;
        }

        System.out.println(user1.getName());
        System.out.println(user1.getEmail());
        model.addAttribute("loggedInUser", user1);
    }
}
