package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JumpController {
    @RequestMapping("/login")
    public String jump(){
        return "login";
    }
    @RequestMapping("/signUp")
    public String jump2(){
        return "signUp";
    }
    @RequestMapping("/upload")
    public String jump3(){
        return "uploadFile";
    }
}
