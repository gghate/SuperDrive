package com.udacity.jwdnd.course1.cloudstorage.Repository;

import com.udacity.jwdnd.course1.cloudstorage.Model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper extends Mapper {

    @Insert("insert into Notes(notetitle,notedescription,userid) values (#{notetitle},#{notedescription},#{userid})")
    @Options(useGeneratedKeys = true,keyProperty = "noteid")
    int createNote(Notes note);

    @Select("Select * from Notes where userid=#{userid}")
    List<Notes> getNotes(Integer userid);

    @Select("Select * from Notes where noteid=#{noteid} and userid=#{userid}")
    Notes getSingleNote(Integer noteid,Integer userid);

    @Update("Update Notes set notetitle=#{notetitle},notedescription=#{notedescription} where noteid=#{noteid}")
    int updateNote(Notes note);

    @Delete("Delete from Notes where noteid=#{noteid}")
    int deleteNote(int noteid);

}
