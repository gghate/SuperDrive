package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Model.FilesData;
import com.udacity.jwdnd.course1.cloudstorage.Repository.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class FilesService {
    private FileMapper fileMapper;

    public  FilesService(FileMapper fileMapper)
    {
        this.fileMapper=fileMapper;
    }

    public int createFilesData(MultipartFile file,Integer userid)
    {
        FilesData filesData=new FilesData();
        filesData.setUserid(userid);
        filesData.setContenttype(file.getContentType());
        filesData.setFilename(file.getOriginalFilename());
        filesData.setFilesize(""+file.getSize());
        try
        {
            filesData.setFiledata(file.getBytes());
        }
        catch (Exception e)
        {

        }


        return fileMapper.insertFile(filesData);
    }

    public List<FilesData> getFilesData(Integer userid)
    {
        return fileMapper.getFileData(userid);
    }

    public FilesData getFileById(Integer fileId)
    {
        return fileMapper.getFileByData(fileId);
    }

    public Integer deleteFile(Integer fileId)
    {
        return fileMapper.deleteFileData(fileId);
    }

    public FilesData getFileByName(String filename){ return fileMapper.getFileByName(filename);}
}
