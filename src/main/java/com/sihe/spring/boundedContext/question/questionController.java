package com.sihe.spring.boundedContext.question;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class questionController {

 @GetMapping("/question/list")
    public String list(){
     return "question_list";
 }
}
