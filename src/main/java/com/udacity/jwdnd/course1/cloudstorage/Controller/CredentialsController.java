package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Credentials")
public class CredentialsController {
    @Autowired
    private NotesService notesService;
    private User user;
    @Autowired
    private UserService userService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private FilesService filesService;
    public CredentialsController(NotesService notesService,UserService userService,CredentialsService credentialsService,FilesService filesService)
    {
        this.notesService=notesService;
        this.userService=userService;
        this.credentialsService=credentialsService;
        this.filesService=filesService;
    }
    @GetMapping
    public String getCredentialsPage(Model model)
    {
        setModelAttributes(model,"Cred");
        return "credentials";
    }
    @PostMapping
    public String createCredentials(@ModelAttribute("newCredential")Credentials credentials, Model model,Authentication authentication)
    {
        user=userService.getUser(authentication.getName());
        credentials.setUserid(user.getUserid());
        if(credentials.getCredentialid()==null)
        {

            credentialsService.createCredentials(credentials);
        }
        else
        {
            credentialsService.updateCredentials(credentials);
        }
        setModelAttributes(model,"Cred");
        return "credentials";
    }
    @RequestMapping(value = "/Edit/{id}",produces={MediaType.APPLICATION_JSON_VALUE},method = RequestMethod.GET)
    @ResponseBody
    public Credentials getCredentialById(@PathVariable("id")Integer id)
    {
        Credentials credential=credentialsService.getCrendentialsById(id);
        return credential;
    }
    @GetMapping("/Delete/{id}")
    public String deleteCredentials(@PathVariable("id") Integer id,Model model)
    {
        credentialsService.deleteCredentials(id);
        setModelAttributes(model,"Cred");
        return "credentials";
    }

    public void setModelAttributes(Model model,String activeTab)
    {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                user=userService.getUser(authentication.getName());
            }
            model.addAttribute("newNote",new Notes());
            model.addAttribute("newCredential",new Credentials());
            model.addAttribute(activeTab,true);
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
