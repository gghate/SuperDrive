package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.Repository.NotesMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesService {

    private NotesMapper notesMapper;

    public NotesService(NotesMapper notesMapper)
    {
        this.notesMapper=notesMapper;
    }

    public List<Notes> getAllNotes(Integer  userid)
    {
        return notesMapper.getNotes(userid);
    }
    public Notes getSingleNotes(Integer noteid,Integer userid)
    {
        return notesMapper.getSingleNote(noteid,userid);
    }

    public int createNote(Notes note)
    {
        return notesMapper.createNote(new Notes(null,note.getNotetitle(),note.getNotedescription(),note.getUserid()));
    }

    public int updateNote(Notes note)
    {
        return notesMapper.updateNote(note);
    }

    public int deleteNote(Integer noteid)
    {
        return notesMapper.deleteNote(noteid);
    }
}
