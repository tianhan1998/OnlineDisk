package controller;

import com.mchange.v1.util.ListUtils;
import entity.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import service.CommonService;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
public class CommonController {
    @Autowired
    CommonService service;
    @RequestMapping("/listCommon")
    public String listCommon(Model m){
        List<Common> commons = service.listCommon();
        Collections.reverse(commons);
        m.addAttribute("commons",commons);
        return "listFile";
    }
    @RequestMapping("/insertCommon")
    public String insertCommon(Model m, String username, String text, long good_number,Date comment_day, HttpServletRequest req){
        Common common=new Common();
        common.setUsername(username);
        common.setText(text);
        common.setGood_number(good_number);
        common.setComment_day(comment_day);
        if(service.insertCommon(common)){
            m.addAttribute("Message","评论成功");
        }else{
            m.addAttribute("Error","评论失败");
        }
        return "forward:/ListFile";
    }
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        //转换日期
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

}
