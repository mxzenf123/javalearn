package org.yangxin.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Controller1 {

    @RequestMapping("/hello")
    public ModelAndView hello(){
        System.out.println("hello 大大");
        return null;
    }

    @RequestMapping("/say")
    public ModelAndView say(@RequestParam String name,@Token String token){

        System.out.println("say: "+name + token);
        return null;
    }
}
