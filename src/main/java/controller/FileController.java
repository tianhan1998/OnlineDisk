package controller;

import com.mysql.cj.xdevapi.Collection;
import entity.Directory;
import entity.MyFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.DirectoryService;
import service.FileService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class FileController {
    @Autowired
    FileService fileService;

    @Resource
    DirectoryService directoryService;

    @RequestMapping(value = "/UploadFile",method = RequestMethod.POST)
    public String uploadFiles(MultipartFile [] files,Model m,HttpServletRequest req,String path) throws IOException {
        int i=0;//上传成功数
        int j=0;//上传失败数
        for(MultipartFile file:files) {
            if (uploadFile(file, req, m, path)) {
                i++;
            } else {
                j++;
            }
        }
        if(i!=0) {
            m.addAttribute("Message", "上传" + i + "个文件成功!");
        }
        if(j!=0){
            m.addAttribute("Error","上传"+j+"个文件失败");
        }
        return "uploadFile";
    }
    @Transactional
    public boolean uploadFile(MultipartFile file, HttpServletRequest request, Model m,String path) throws IOException {
        if(!file.isEmpty()) {//判断文件是否存在
            String username = (String) request.getSession().getAttribute("username");
            String fakefilename = file.getOriginalFilename();//不带+序号判断的假名字
            StringBuilder newPath = null;
            if(System.getProperty("os.name").contains("Windows")) {
                String[] split = path.split("\\\\");
                newPath = new StringBuilder();
                if (split[0].equals("C:") && split[1].equals("upload")) {//处理路径字符串
                    split[2] = username;
                    for (String tPath : split) {
                        newPath.append(tPath).append(File.separator);
                    }
                } else {
                    return false;
                }
            }else if(System.getProperty("os.name").contains("Linux")){
                String[] split = path.split("/");
                newPath= new StringBuilder("/");
                if(split[1].equals("home")&&split[2].equals("upload")){//处理路径字符串
                    split[3]=username;
                    for(String tPath:split){
                        newPath.append(tPath).append(File.separator);
                    }
                }else{
                    return false;
                } 
            }
            MyFile myFile = new MyFile();
            myFile.setUsername(username);
            myFile.setSize(String.valueOf(file.getSize()));
            myFile.setFakename(fakefilename);
            fileService.uploadFile(myFile);
            String truefilename = myFile.getId() + "+" + fakefilename;//带+序号的实际文件名
            myFile.setPath(newPath + truefilename);
            myFile.setFilename(truefilename);
            fileService.updateFile(myFile);
            File uploadfile = new File(String.valueOf(newPath), myFile.getFilename());
            if (!uploadfile.exists()) {
                uploadfile.mkdirs();
            }
            try {
                file.transferTo(uploadfile);
            }catch(Exception e){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚
                return false;
            }
        }else{
            return false;
        }
        return true;
    }
    @RequestMapping("/ListFile")
    public String listFile(HttpServletRequest req,Model m,String path){
        boolean checkFlag=true;
        String system=System.getProperty("os.name");
        HttpSession session=req.getSession();
        String systemPrePath=null;
        String username= (String) session.getAttribute("username");
        List<MyFile> result = null;
        if(username==null){
            return "redirect:/SignIn";
        }
        File file=new File(path);
        if(system.contains("windows")) {
            String[] splits = path.split("\\\\");
            if(!splits[0].equals("C:")&&!splits[1].equals("upload")&&!splits[2].equals(username)) {
                m.addAttribute("Error", "路径错误");
                checkFlag=false;
            }
        }else if(system.contains("Linux")) {
            String[] splits = path.split("/");
            if(!splits[1].equals("home")&&!splits[2].equals("upload")&&!splits[3].equals(username)) {
                m.addAttribute("Error", "路径错误");
                checkFlag=false;
            }
        }
        if(checkFlag) {
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null && listFiles.length > 0) {
                    result = new ArrayList<>();
                    for (File tempFile : listFiles) {
                        if (tempFile.isFile()) {
                            MyFile myFile = fileService.findFileByNameAndUsername(tempFile.getName(), username);
                            if (myFile != null) {
                                result.add(myFile);
                            }
                        }
                    }
                }
            }
            if (system.contains("Linux")) {
                systemPrePath = "/home/upload/" + username;
            } else if (system.contains("Windows")) {
                systemPrePath = "C:\\upload\\" + username;
            }
            List<Directory> directories = directoryService.getAllDirectory(path, systemPrePath);
            m.addAttribute("directories", directories);
            m.addAttribute("list", result);
        }
        return "forward:listCommon";

    }

    @RequestMapping(value = "/DownLoad/{id}")
    public void downloadFile(HttpServletResponse res, HttpServletRequest req, @PathVariable("id") String id) throws IOException {
        InputStream inputStream = null;
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(res.getOutputStream())) {
            HttpSession session = req.getSession();
            String username = (String) session.getAttribute("username");
            MyFile myFile=fileService.findFile(Integer.valueOf(id));
            if(myFile==null||!(myFile.getUsername().equals(username))){//文件持有者检查
                req.setAttribute("Error","文件不存在");
            }
            else {
                String filename=myFile.getFakename();//下载框显示假名字
                String path = myFile.getPath();
                inputStream = new BufferedInputStream(new FileInputStream(new File(path)));
                filename = URLEncoder.encode(filename, "UTF-8");
                filename = filename.replaceAll("\\+", " ");
                res.addHeader("Content-Disposition", "attachment;filename=" + filename);
                res.addHeader("Content-Length", myFile.getSize());
                res.setContentType("multipart/form-data");
                int len = 0;
                while ((len = inputStream.read()) != -1) {
                    bufferedOutputStream.write(len);
//                    bufferedOutputStream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @RequestMapping("/DeleteFile")
    @Transactional
    public String deleteFile(HttpServletRequest req, String id, Model m,String path) throws Exception {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        MyFile myFile=fileService.findFile(Integer.valueOf(id));
        if(myFile==null||!(myFile.getUsername().equals(username))){//文件持有者检查
            session.setAttribute("Error","文件不存在");
        }
        String filePath =myFile.getPath();
        File file = new File(filePath);
        if (!file.exists()) {
            m.addAttribute("Error", "文件不存在");
        } else {
            if (fileService.deleteFile(myFile.getId())) {
                if (file.delete()) {
                    session.setAttribute("Message", "删除成功");
                } else {
                    session.setAttribute("Error", "删除文件失败");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//手动回滚
                }
            } else {
                session.setAttribute("Error", "删除数据库失败");
            }
        }
        m.addAttribute("path",path);
        return "redirect:/ListFile";
    }
    @RequestMapping("/DeleteDir")
    public String deleteDirectory(String path,String oldPath,Model m){
        try{
            m.addAttribute("path",oldPath);
            if(directoryService.deleteDirectory(path)){
                m.addAttribute("Message","删除目录成功");
            }else{
                m.addAttribute("Error","删除失败");
            }
            return "redirect:/ListFile";
        }catch(Exception e){
            e.printStackTrace();
            m.addAttribute("Error",e.getMessage());
        }
        return "redirect:/ListFile";
    }
    @RequestMapping("/createDir")
    public String createDir(String name,String path,Model m){
        try {
            m.addAttribute("path",path);
            File file=new File(path+File.separator+name);
            if(!file.exists()){
                if(file.mkdirs()){
                    m.addAttribute("Message","创建目录成功");
                }else{
                    m.addAttribute("Error","创建目录失败");
                }
            }else{
                m.addAttribute("Error","创建目录错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            m.addAttribute("Error",e.getMessage());
        }
        return "redirect:/ListFile";
    }
}
