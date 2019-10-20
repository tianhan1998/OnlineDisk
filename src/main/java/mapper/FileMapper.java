package mapper;

import entity.MyFile;

import java.util.List;

public interface FileMapper {
    void uploadFile(MyFile myFile);
    List<MyFile> listFile(String username);
}
