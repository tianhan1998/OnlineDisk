package service;

import entity.Directory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryService {
    public List<Directory> getAllDirectory(final String path, final String username) {
        List<Directory> result;
        try {
            File file = new File(path);
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                result = new ArrayList<>();
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
                if(!path.equals("C:\\upload\\" + username)) {
                    final String back=path.substring(0,path.lastIndexOf("\\"));
                    result.add(new Directory(){{
                        this.setPath(back);
                        this.setdName("..");
                    }});
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
}
