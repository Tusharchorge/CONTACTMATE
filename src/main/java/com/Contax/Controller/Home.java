package com.Contax.Controller;

import com.Contax.Entities.User;
import com.Contax.Forms.UserForm;
import com.Contax.Services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Home {

    @Autowired
    private UserService userService;


    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/services")
    public String services() {
        return "services";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/register")
    public String signup(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("user", userForm);
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegistration(@Valid @ModelAttribute("user")UserForm userForm,BindingResult result, HttpSession session) {

        if (result.hasErrors()) {
            return "register";
        }

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setProfilePic("https://i2.pngimg.me/thumb/f/720/c3f2c592f9.jpg");

        userService.saveUser(user);

        session.setAttribute("message", "Registration Successful");

        return "redirect:/register";
    }
    @PostMapping("/clear-message")
    public String clearMessage(HttpSession session) {
        session.removeAttribute("message");
        return "redirect:/register";
    }


}
