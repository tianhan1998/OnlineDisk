package service;

import entity.Directory;
import entity.MyFile;
import mapper.FileMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryService {
    @Resource
    FileService fileService;
    public List<Directory> getAllDirectory(final String path, String systemPrePath) {
        List<Directory> result;
        String back=null;
        try {
            File file = new File(path);
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                result = new ArrayList<>();
                if(!path.equals(systemPrePath)) {
                    if(System.getProperty("os.name").contains("Windows")) {
                        back = path.substring(0, path.lastIndexOf("\\"));
                    }else if(System.getProperty("os.name").contains("Linux")){
                        back = path.substring(0, path.lastIndexOf("/"));
                    }
                    final String finalBack = back;
                    result.add(new Directory(){{
                        this.setPath(finalBack);
                        this.setdName("..");
                    }});
                }
                if (listFiles != null && listFiles.length > 0) {
                    for (final File tempFile : listFiles) {
                        if (tempFile.isDirectory()) {
                            result.add(new Directory() {{
                                this.setdName(tempFile.getName());
                                this.setPath(tempFile.getPath());
                            }});
                        }
                    }
                }
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }
    @Transactional
    public boolean deleteDirectory(String path) {
        try {
            File file = new File(path);
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    for (File temp : listFiles) {
                        if (temp.isDirectory()) {
                            deleteDirectory(temp.getAbsolutePath());
                        } else {
                            if (fileService.deleteFileByName(temp.getName()) > 0) {
                                if(!temp.delete()){
                                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//删除文件失败数据库回滚
                                }
                            }
                        }
                    }
                }
                return file.delete();//删除所有后删除本体
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
