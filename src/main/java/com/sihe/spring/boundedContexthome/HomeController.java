package com.sihe.spring.boundedContexthome;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @GetMapping("/home/main")
    @ResponseBody
    public String home() {
        return "안녕";
    }
}
