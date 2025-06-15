package com.sihe.spring.boundedContext.question;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class questionController {
    private final QuestionRepository questionRepository;
 @GetMapping("/question/list")
    public String list(Model model) {
     List<Question> questionList= questionRepository.findAll();

     model.addAttribute("questionList",questionList);
     return "question_list";
 }
}
