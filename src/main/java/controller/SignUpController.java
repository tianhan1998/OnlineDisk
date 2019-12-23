package controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import entity.User;
import org.springframework.web.bind.annotation.ResponseBody;
import service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SignUpController {
    @Autowired
    SignUpService service;
    @RequestMapping(value = "/SignUp",method = RequestMethod.POST)
    public ModelAndView signUp(@Valid User validuser){
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setViewName("signUp");//默认跳转signUp界面
        User user=service.signUpCheck(validuser.getUsername());
        if(user==null){//检查是否重复
            if(service.signUp(validuser.getUsername(),validuser.getPassword())){//sql插入
                modelAndView.setViewName("login");
            }else{
                modelAndView.addObject("Error","注册失败");
            }
        }else{//重复
            modelAndView.addObject("Error","用户名已存在");
        }
        return modelAndView;
    }
    @RequestMapping(value = "/SignUpCheck",method = RequestMethod.POST)
    @ResponseBody
    public Map signUp(String username){
        System.out.println(username);
        JSONObject json=new JSONObject();
        User user=service.signUpCheck(username);
        if(user!=null){
            json.put("Message","用户名已存在");
        }else{
            json.put("Message","此用户名可以注册");
        }
        return json;
    }


}
