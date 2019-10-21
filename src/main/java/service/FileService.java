package service;

import entity.MyFile;
import mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    @Autowired
    FileMapper fileMapper;
    public void uploadFile(MyFile myFile){
        fileMapper.uploadFile(myFile);
    }
    public List<MyFile>listFile(String username){
        return fileMapper.listFile(username);
    }
    public boolean deleteFile(Integer id){
        return fileMapper.deleteFile(id);
    }
    public MyFile findFile(Integer id){
        return fileMapper.findFile(id);
    }
    public boolean updateFile(MyFile myFile){
        return fileMapper.updateFile(myFile);
    }
}
