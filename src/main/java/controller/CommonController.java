package controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mchange.v1.util.ListUtils;
import entity.Common;
import entity.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import service.CommonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    @ResponseBody
    public Map insertCommon(@Valid Common validcommon, BindingResult result, HttpServletRequest req){
        JSONObject json = (JSONObject) JSON.toJSON(validcommon);
        if(result.hasErrors()){
            json.put("success","false");
            json.put("Error",result.getFieldError().getDefaultMessage());
        }else {
            if (service.insertCommon(validcommon)>0) {
                json.put("success", "true");
                json.put("Message", "评论成功");
                json.put("id", validcommon.getId());
            } else {
                json.put("success", "false");
                json.put("Error", "评论失败");
            }
        }
        return json;
    }
    @RequestMapping("/deleteCommon")
    public String deleteCommon(String id,HttpServletRequest req,Model m,String path){
        Integer cid=Integer.parseInt(id);
        Common common=service.findId(cid);
        HttpSession session=req.getSession();
        if(common!=null){
            String username= (String) session.getAttribute("username");
            if(common.getUsername().equals(username)){
                if(service.deleteCommon(cid)>0){
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
        m.addAttribute("path",path);
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
            if(service.findGood(username,id)==0) {
                if (service.insertGood(username, id)>0) {
                    if (service.updateCommonGood(id)>0) {
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
        Good good;
        if (common != null) {
            if(service.findGood(username,id)>0) {
                good=service.findGoodByNameAndId(username,id);
                if (service.deleteGood(good.getGid())>0) {
                    if (service.unUpdateCommonGood(id)>0) {
                        json.put("Message", "取消成功");
                        json.put("success", "true");
                    } else {
                        json.put("Error", "评论态修改失败");
                        json.put("success", "false");
                    }
                } else {
                    json.put("Error", "取消赞失败");
                    json.put("success", "false");
                }
            }else{
                json.put("Error", "您未点赞");
                json.put("success", "false");
            }
        }else{
            json.put("Error","评论不存在");
            json.put("success","false");
        }
        return json;
    }
}
