package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.Model.FilesData;
import com.udacity.jwdnd.course1.cloudstorage.Model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.Model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FilesService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private NotesService notesService;
    @Autowired
    private UserService userService;
    @Autowired
    private CredentialsService credentialsService;
    private User user;
    @Autowired
    private FilesService filesService;


    @GetMapping()
    public String getHomePage(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user=userService.getUser(authentication.getName());
        }
        setModelAttributes(model,"home");
        return "home";
    }


    @PostMapping("/File/Upload")
    public String upload(@RequestParam("fileUpload")MultipartFile file, Model model)
    {
        String error=null;

        FilesData filesData=filesService.getFileByName(file.getOriginalFilename());
        if(filesData!=null)
        {
            error="File already exist";
            model.addAttribute("error",error);
        }
        else if(file.getSize()>2000000)
        {

            error="Too large File to upload";
            model.addAttribute("error",error);
        }
        else
        {
            filesService.createFilesData(file, user.getUserid());
        }
        setModelAttributes(model,"home");
        return "home";
    }
    @GetMapping("/File/Download/{id}")

    public ResponseEntity<InputStreamSource> download(@PathVariable("id")Integer id, Model model)
    {
        FilesData filesData=filesService.getFileById(id);
        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(filesData.getFiledata()));
        MediaType mediaType=MediaType.parseMediaType(filesData.getContenttype());
        return ResponseEntity.ok()
                      .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filesData.getFilename())
                      .contentType(mediaType)
                      .contentLength(Long.parseLong(filesData.getFilesize())) //
                      .body(resource);
    }
    @GetMapping("/File/Delete/{id}")
    public String deleteFile(@PathVariable("id")Integer fileId ,Model model)
    {
        filesService.deleteFile(fileId);
        setModelAttributes(model,"home");
        return "home";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String throwIllegalStateException(IllegalStateException err,Model model)
    {
        model.addAttribute("error","File too large too large too upload");
        setModelAttributes(model,"home");
        return "home";
    }
    @RequestMapping
    public String fallBackUrl(Model model)
    {
        setModelAttributes(model,"home");
        return "home";
    }
    public void setModelAttributes(Model model,String alert)
    {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!(authentication instanceof AnonymousAuthenticationToken)) {
                user=userService.getUser(authentication.getName());
            }
            model.addAttribute("newNote",new Notes());
            model.addAttribute("newCredential",new Credentials());
            model.addAttribute("alert",alert);
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
