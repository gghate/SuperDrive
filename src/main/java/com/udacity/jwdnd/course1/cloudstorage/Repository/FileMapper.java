package com.udacity.jwdnd.course1.cloudstorage.Repository;

import com.udacity.jwdnd.course1.cloudstorage.Model.FilesData;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper extends Mapper {

    @Insert("Insert into FILES (filename,contenttype,filesize,userid,filedata) values(#{filename},#{contenttype},#{filesize},#{userid},#{filedata})")
    @Options(useGeneratedKeys = true,keyProperty = "fileId")
    Integer insertFile(FilesData filesData);

    @Select("Select * from FILES where userid=#{userid}")
    List<FilesData>  getFileData(Integer userid);

    @Select("Select * from FILES where fileId=#{fileId}")
    FilesData getFileByData(Integer fileId);

    @Select("Select * from FILES where filename=#{filename}")
    FilesData getFileByName(String filename);

    @Delete("Delete from FILES where fileID=#{fileId}")
    Integer deleteFileData(Integer fileId);
}
