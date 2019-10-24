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
    public String uploadFiles(MultipartFile [] files,Model m,HttpServletRequest req) throws IOException {
        int i=0;//上传成功数
        int j=0;//上传失败数
        for(MultipartFile file:files){
            if(uploadFile(file,req,m)){
                i++;
            }else{
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
    public boolean uploadFile(MultipartFile file, HttpServletRequest request, Model m) throws IOException {
        if(!file.isEmpty()) {//判断文件是否存在
            String username = (String) request.getSession().getAttribute("username");
            String fakefilename = file.getOriginalFilename();//不带+序号判断的假名字
            String path = "C:\\upload\\" + username;
            MyFile myFile = new MyFile();
            myFile.setUsername(username);
            myFile.setSize(String.valueOf(file.getSize()));
            myFile.setFakename(fakefilename);
            fileService.uploadFile(myFile);
            String truefilename = myFile.getId() + "+" + fakefilename;//带+序号的实际文件名
            myFile.setPath(path + "\\" + truefilename);
            myFile.setFilename(truefilename);
            fileService.updateFile(myFile);
            File uploadfile = new File(path, myFile.getFilename());
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
    public String listFile(HttpServletRequest req,Model m){
        HttpSession session=req.getSession();
        String username= (String) session.getAttribute("username");
        if(username==null){
            return "redirect:/SignIn";
        }
        List<MyFile> myFiles=fileService.listFile(username);
        m.addAttribute("list",myFiles);
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
    @RequestMapping("/DeleteFile/{id}")
    @Transactional
    public ModelAndView deleteFile(HttpServletRequest req, @PathVariable("id")String id, Model m) throws Exception {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        MyFile myFile=fileService.findFile(Integer.valueOf(id));
        if(myFile==null||!(myFile.getUsername().equals(username))){//文件持有者检查
            session.setAttribute("Error","文件不存在");
        }
        String path =myFile.getPath();
        File file = new File(path);
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
        return new ModelAndView("redirect:/ListFile");
    }
}
