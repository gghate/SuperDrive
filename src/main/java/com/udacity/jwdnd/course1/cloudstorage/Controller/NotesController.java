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
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Controller
@RequestMapping("/Note")
public class NotesController {
    @Autowired
    private NotesService notesService;
    private User user;
    @Autowired
    private UserService userService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private FilesService filesService;
    public NotesController(NotesService notesService,UserService userService,CredentialsService credentialsService,FilesService filesService)
    {
        this.notesService=notesService;
        this.userService=userService;
        this.credentialsService=credentialsService;
        this.filesService=filesService;
    }

    @GetMapping
    public String getNotesPage(Model model)
    {
        setModelAttributes(model,"Notes");
        return "notes";
    }

    @PostMapping
    public String addNote(@ModelAttribute("newNote") Notes note, Model model,Authentication authentication)
    {
        if(note.getNoteid()==null)
        {
            user=userService.getUser(authentication.getName());
            note.setUserid(user.getUserid());
            notesService.createNote(note);
        }
        else
        {
            notesService.updateNote(note);
        }

        setModelAttributes(model,"Notes");
        return "notes";
    }
    @RequestMapping("/{id}")
    public String deleteNote(@PathVariable("id")Integer noteid, @ModelAttribute("newNote") Notes note, Model model,Authentication authentication)
    {
        user=userService.getUser(authentication.getName());
        notesService.deleteNote(noteid);
        setModelAttributes(model,"Notes");
        return "notes";
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
