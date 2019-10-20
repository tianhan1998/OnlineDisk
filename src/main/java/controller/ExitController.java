package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.*;

@Controller
public class ExitController {
    @RequestMapping("/Exit")
    public String exit(HttpServletResponse response, HttpServletRequest request){
        HttpSession session=request.getSession();
        session.removeAttribute("username");
        Cookie cookie=new Cookie("username",null);//清除cookie
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
        return "exit";
    }

}
