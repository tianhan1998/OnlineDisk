package mapper;

import entity.MyFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileMapper {
    void uploadFile(MyFile myFile);
    List<MyFile> listFile(String username);
    boolean deleteFile(Integer id);
    MyFile findFile(Integer id);
    boolean updateFile(MyFile myFile);
}
