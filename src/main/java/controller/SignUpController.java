package controller;

import entity.User;
import service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignUpController {
    @Autowired
    SignUpService service;
    @RequestMapping(value = "/SignUp",method = RequestMethod.POST)
    public ModelAndView signUp(String username,String password){
        ModelAndView modelAndView =new ModelAndView();
        modelAndView.setViewName("signUp");//默认跳转signUp界面
        User user=service.signUpCheck(username);
        if(user==null){//检查是否重复
            if(service.signUp(username,password)){//sql插入
                modelAndView.setViewName("login");
            }else{
                modelAndView.addObject("Error","注册失败");
            }
        }else{//重复
            modelAndView.addObject("Error","用户名已存在");
        }
        return modelAndView;
    }
}
