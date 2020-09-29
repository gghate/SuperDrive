package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.Repository.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;
    public SignupController(UserService userService)
    {
        this.userService=userService;
    }
    @GetMapping()
    public String GetSignupPage(Model model)
    {
        model.addAttribute("userObject",new User());
        return "signup";
    }

    @PostMapping()
    public String signup(@ModelAttribute("userObject") User user, Model model)
    {
        String signupError=null;
        System.out.println(user.getUsername());
        if(!userService.isUsernameAvailable(user.getUsername()))
        {
            signupError="Username already exist";
        }
        if(signupError==null)
        {
            System.out.println("Signing up");
            int rows=userService.createUser(user);
            System.out.println("User created "+ rows);
            if(rows<0)
            {
                signupError="There was an error signing you up. Please try again.";
            }
        }


        if(signupError!=null)
        {
            model.addAttribute("signupError",signupError);
        }
        else {
            model.addAttribute("signupSuccess",true);
        }
        return "signup";
    }
}
