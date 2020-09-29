package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {
    @Autowired
    private NotesService notesService;
    @Autowired
    private UserService userService;
    @Autowired
    private CredentialsService credentialsService;
    private User user;
    @Autowired
    private FilesService filesService;

    @RequestMapping("/*")
    public String fallbackUrl(Model model)
    {
        model.addAttribute("error","URL Not Found");
        setModelAttributes(model);
        return "home";
    }

    public void setModelAttributes(Model model)
    {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                user=userService.getUser(authentication.getName());
            }
            model.addAttribute("newNote",new Notes());
            model.addAttribute("newCredential",new Credentials());
            model.addAttribute("Notes",notesService.getAllNotes(user.getUserid()));
            model.addAttribute("credentials",credentialsService.getCredentials(user.getUserid()));
            model.addAttribute("newFiles",filesService.getFilesData(user.getUserid()));
        }
        catch (Exception e)
        {
            System.out.println(" msg "+e);
        }

    }
}
