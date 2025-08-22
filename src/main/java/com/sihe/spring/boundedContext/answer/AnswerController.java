package com.sihe.spring.boundedContext.answer;

import ch.qos.logback.core.model.Model;
import com.sihe.spring.boundedContext.question.Question;
import com.sihe.spring.boundedContext.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    @GetMapping("/adopt/{id}")
    public String adoptAnswer(@PathVariable("id") Integer id) {
        // 1. id로 답변 정보를 가져옵니다.
        Answer answer = answerService.getAnswer(id);

        // 2. 서비스에 채택 처리를 위임합니다.
        this.answerService.adopt(id);

        // 3. 채택 후, 원래 질문 상세 페이지로 돌아갑니다.
        return "redirect:/question/detail/" + answer.getQuestion().getId();
    }

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content) {
        Question question = questionService.getQuestion(id);
        // TODO: 답변을 저장한다.
        Answer answer= answerService.create(question,content);

        //return "%d번 질문에 대한 답변이 생성되었습니다.(답변 번호:%d)".formatted(id, question.getId());
        return String.format("redirect:/question/detail/%s", id);
    }
    // ✅ 1. '채택취소' 링크(GET)를 눌렀을 때 비밀번호 확인 페이지를 보여주는 메서드
    @GetMapping("/cancel-adoption/{id}")
    public String showCancelAdoptionPasswordForm(@PathVariable("id") Integer id, org.springframework.ui.Model model) {
        Answer answer = answerService.getAnswer(id);

        model.addAttribute("actionTitle", "채택 취소");
        model.addAttribute("formAction", "/answer/cancel-adoption/" + id); // 폼이 제출될 POST 경로
        model.addAttribute("questionId", answer.getQuestion().getId());

        return "adoption_password_form"; // 비밀번호 입력 폼 HTML
    }

    // ✅ 2. 비밀번호 확인 후 '채택 취소'를 실제로 처리하는 메서드
    @PostMapping("/cancel-adoption/{id}") // ✅ PostMapping으로 변경하고 경로 수정
    public String cancelAdoption(@PathVariable("id") Integer id, @RequestParam String password) {
        Answer answer = answerService.getAnswer(id);
        boolean success = answerService.cancelAdoption(id, password); // 서비스 로직 호출

        if (!success) {
            // 실패 시 다시 비밀번호 확인 페이지로
            return "redirect:/answer/cancel-adoption/%d?error=true".formatted(id);
        }

        // 성공 시 질문 상세 페이지로
        return "redirect:/question/detail/" + answer.getQuestion().getId();
    }


}
