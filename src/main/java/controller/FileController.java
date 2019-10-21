package controller;

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
    @RequestMapping(value = "/UploadFile",method = RequestMethod.POST)
    public String uploadFile(MultipartFile file, HttpServletRequest request, Model m) throws IOException {
        String username= (String) request.getSession().getAttribute("username");
        String filename=file.getOriginalFilename();
        String path="C:\\upload\\"+username;
        MyFile myFile=new MyFile();
        myFile.setFilename(filename);
        myFile.setSize(String.valueOf(file.getSize()));
        myFile.setUsername(username);
        fileService.uploadFile(myFile);
        File uploadfile=new File(path,myFile.getId()+"+"+filename);
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
        String username= (String) session.getAttribute("username");
        if(username==null){
            return "redirect:/SignIn";
        }
        List<MyFile> myFiles=fileService.listFile(username);
        m.addAttribute("list",myFiles);
        return "listFile";
    }

    @RequestMapping(value = "/DownLoad/{filename:.*}")
    public void downloadFile(HttpServletResponse res,HttpServletRequest req,@PathVariable("filename") String filename) throws IOException {
        InputStream inputStream = null;
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(res.getOutputStream())) {
            HttpSession session = req.getSession();
            String username = (String) session.getAttribute("username");
            String path = "C:\\upload\\" + username + "\\" + filename;
            inputStream = new BufferedInputStream(new FileInputStream(new File(path)));
            StringBuffer stringBuffer = new StringBuffer(filename);
            stringBuffer = stringBuffer.delete(0, stringBuffer.indexOf("+") + 1);
            filename = stringBuffer.toString();
            filename = URLEncoder.encode(filename, "UTF-8");
            filename = filename.replaceAll("\\+", " ");
            res.addHeader("Content-Disposition", "attachment;filename=" + filename);
            res.setContentType("multipart/form-data");
            int len = 0;
            while ((len = inputStream.read()) != -1) {
                bufferedOutputStream.write(len);
                bufferedOutputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @RequestMapping("/DeleteFile/{filename:.*}")
    @Transactional
    public ModelAndView deleteFile(HttpServletRequest req, @PathVariable("filename")String filename, Model m) throws Exception {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        String path = "C:\\upload\\" + username + "\\" + filename;
        File file = new File(path);
        StringBuffer stringBuffer = new StringBuffer(filename);
        stringBuffer = stringBuffer.delete(0, stringBuffer.indexOf("+") + 1);
        filename = stringBuffer.toString();
        if (!file.exists()) {
            m.addAttribute("Error", "文件不存在");
        } else {
            if (fileService.deleteFile(filename, username)) {
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
        return new ModelAndView("redirect:/ListFile");
    }
}
