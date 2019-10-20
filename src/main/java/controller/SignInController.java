package controller;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import service.SignInService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes(value = "username",types = String.class)//将塞进Model中的User放入session
public class SignInController {
    @Autowired
    SignInService signInService;
    @Autowired
    HttpServletRequest request;
    @RequestMapping(value = "/SignIn")
    public String signIn(HttpServletResponse response,Model m, String username, String password,String autologin){
        HttpSession session=request.getSession();
        if(session.getAttribute("username")!=null){//session有记录直接进入查询
            return "forward:/ListFile";
        }
        User user=signInService.signIn(username, password);
        if(user!=null){//登录是否成功
            m.addAttribute("username",username);
            if("true".equals(autologin)){//cookie保存30天记录
                Cookie cookie =new Cookie("username",username);
                cookie.setPath(request.getContextPath());
                cookie.setMaxAge(3600*24*30);
                response.addCookie(cookie);
            }
            return "redirect:/ListFile";
        }else{
            m.addAttribute("Error","用户名或密码错误");
        }
        return "login";
    }
}
