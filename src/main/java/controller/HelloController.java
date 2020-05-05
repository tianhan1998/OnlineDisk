package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HelloController {
    @RequestMapping("/")
    public String helloWorld(HttpServletRequest request, Model m){
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
                if(System.getProperty("os.name").contains("Windows")) {
                    m.addAttribute("path", "C:\\upload\\" + target.getValue());
                }else if(System.getProperty("os.name").contains("Linux")){
                    m.addAttribute("path","/home/upload/"+target.getValue());
                }
                return "redirect:/ListFile";
            }
        }

        return "login";
    }
}
