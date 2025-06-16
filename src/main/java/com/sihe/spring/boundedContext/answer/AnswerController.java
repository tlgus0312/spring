package com.sihe.spring.boundedContext.answer;

import ch.qos.logback.core.model.Model;
import com.sihe.spring.boundedContext.question.Question;
import com.sihe.spring.boundedContext.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content) {
        Question question = this.questionService.getQuestion(id);
        // TODO: 답변을 저장한다.
        return String.format("redirect:/question/detail/%s", id);
    }

}
