package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HelloController {
    @RequestMapping("/")
    public String helloWorld(HttpServletRequest request){
        Cookie[] cookies=request.getCookies();
        if(cookies!=null) {
            Cookie target = null;
            for (Cookie c : cookies) {
                if (c.getName().equals("username")) {
                    target = c;
                }
            }
            if (target != null) {
                HttpSession session = request.getSession();
                session.setAttribute("username", target.getValue());
                return "redirect:/ListFile";
            }
        }

        return "login";
    }
}
