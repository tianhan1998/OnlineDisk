package controller;

import entity.MyFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import service.FileService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class FileController {
    @Autowired
    FileService fileService;
    @RequestMapping("/UploadFile")
    public String uploadFile(MultipartFile file, HttpServletRequest request, Model m) throws IOException {
        String username= (String) request.getSession().getAttribute("username");
        String filename=file.getOriginalFilename();
        String path="C:\\upload\\"+username;
        MyFile myFile=new MyFile();
        myFile.setFilename(filename);
        myFile.setSize(String.valueOf(file.getSize()));
        myFile.setUsername(username);
        fileService.uploadFile(myFile);
        File uploadfile=new File(path,myFile.getId()+filename);
        if(!uploadfile.exists()){
            uploadfile.mkdirs();
        }
        file.transferTo(uploadfile);
        m.addAttribute("Message","上传成功");
        return "uploadFile";
    }
    @RequestMapping("/ListFile")
    public String listFile(HttpServletRequest req,Model m){
        HttpSession session=req.getSession();
        List<MyFile> myFiles=fileService.listFile((String) session.getAttribute("username"));
        m.addAttribute("list",myFiles);
        return "listFile";
    }

    @RequestMapping(value = "/DownLoad/{filename:.*}")
    public void downloadFile(HttpServletResponse res,HttpServletRequest req,@PathVariable("filename") String filename) throws IOException {
        HttpSession session=req.getSession();
        String username= (String) session.getAttribute("username");
        String path="C:\\upload\\"+username+"\\"+filename;
        InputStream inputStream=new BufferedInputStream(new FileInputStream(new File(path)));
        filename= URLEncoder.encode(filename,"UTF-8");
        StringBuffer stringBuffer=new StringBuffer(filename);
        stringBuffer=stringBuffer.deleteCharAt(0);
        filename=stringBuffer.toString();
        filename=filename.replaceAll("\\+"," ");
        res.addHeader("Content-Disposition", "attachment;filename=" + filename);
        res.setContentType("multipart/form-data");
        BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(res.getOutputStream());
        int len=0;
        while((len=inputStream.read())!=-1){
            bufferedOutputStream.write(len);
            bufferedOutputStream.flush();
        }
        bufferedOutputStream.close();
    }
}
