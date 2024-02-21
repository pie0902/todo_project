package com.tryagain.tryagain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @Controller
    public class WebController {

        @GetMapping(value =  {"/", "/login","/signup","/test","/board/*"})
        public String forward() {
            return "forward:/index.html";
        }
    }
}