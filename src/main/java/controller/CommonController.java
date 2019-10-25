package controller;

import com.alibaba.fastjson.JSONObject;
import com.mchange.v1.util.ListUtils;
import entity.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import service.CommonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CommonController {
    @Autowired
    CommonService service;
    @RequestMapping("/listCommon")
    public String listCommon(Model m,HttpSession session){
        String username= (String) session.getAttribute("username");
        List<Common> commons = service.listCommon(username);
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
    @RequestMapping("/deleteCommon/{id}")
    public String deleteCommon(@PathVariable("id")String id,HttpServletRequest req,Model m){
        Integer cid=Integer.parseInt(id);
        Common common=service.findId(cid);
        HttpSession session=req.getSession();
        if(common!=null){
            String username= (String) session.getAttribute("username");
            if(common.getUsername().equals(username)){
                if(service.deleteCommon(cid)){
                    m.addAttribute("Message","删除评论成功");
                }else{
                    m.addAttribute("Error","删除评论失败");
                }
            }else{
                m.addAttribute("Error","无法删除评论(权限错误)");
            }
        }else{
            m.addAttribute("Error","未找到此评论");
        }
        return "forward:/ListFile";
    }
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
    @RequestMapping(value = "/goodCommon",method = RequestMethod.POST)
    @ResponseBody
    public Map goodCommon(Integer id, HttpServletRequest req) {
        JSONObject json = new JSONObject();
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        Common common = service.findId(id);
        if (common != null) {
            if(!service.findGood(username,id)){
                if (!common.isGood()) {
                    if (service.insertGood(username, id)) {
                        if (service.updateCommonGood(id)) {
                            json.put("Message", "点赞成功");
                            json.put("success", "true");
                        } else {
                            json.put("Error", "评论态修改失败");
                            json.put("success", "false");
                        }
                    } else {
                        json.put("Error", "点赞失败");
                        json.put("success", "false");
                    }
                } else {
                    json.put("Error", "您已点赞");
                    json.put("success", "false");
                }
            }else{
                json.put("Error", "您已点赞");
                json.put("success", "false");
            }
        }else{
            json.put("Error","评论不存在");
            json.put("success","false");
        }
        return json;
    }
    @RequestMapping(value = "/unGoodCommon",method = RequestMethod.POST)
    @ResponseBody
    public Map unGoodCommon(Integer id, HttpServletRequest req) {
        JSONObject json = new JSONObject();
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        Common common = service.findId(id);
        if (common != null) {
            if(service.findGood(username,id)){
                if (common.isGood()) {
                    if (service.deleteGood(username, id)) {
                        if (service.unUpdateCommonGood(id)) {
                            json.put("Message", "已取消赞");
                            json.put("success", "true");
                        } else {
                            json.put("Error", "评论态修改失败");
                            json.put("success", "false");
                        }
                    } else {
                        json.put("Error", "取消赞失败");
                        json.put("success", "false");
                    }
                } else {
                    json.put("Error", "您已取消");
                    json.put("success", "false");
                }
            }else{
                json.put("Error", "您已取消");
                json.put("success", "false");
            }
        }else{
            json.put("Error","评论不存在");
            json.put("success","false");
        }
        return json;
    }
}
