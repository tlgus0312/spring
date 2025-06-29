package com.sihe.spring.boundedContext.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/question")
@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = questionService.findAll();
        // 카테고리 목록 가져오기
        List<Category> categoryList = questionService.findAllCategories();

        model.addAttribute("questionList", questionList);
        model.addAttribute("categoryList", categoryList);

        return "question_home";
    }

    @GetMapping("/category/{categoryId}")
    public String questionsByCategory(@PathVariable("categoryId") Integer categoryId, Model model) {
        // 카테고리 정보 조회
        Category category = questionService.getCategoryById(categoryId);

        // 해당 카테고리의 질문들 조회
        List<Question> questionList = questionService.getQuestionsByCategory(categoryId);

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categoryName", category.getName());
        model.addAttribute("questionList", questionList);

        return "question_category";
    }

    @GetMapping("/search")
    public String searchQuestions(@RequestParam("keyword") String keyword,
                                  @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                  Model model) {
        List<Question> questionList;
        String categoryName = "전체";

        if (categoryId != null) {
            // 특정 카테고리 내에서 검색
            Category category = questionService.getCategoryById(categoryId);
            questionList = questionService.searchInCategory(keyword, categoryId);
            categoryName = category.getName();
            model.addAttribute("categoryId", categoryId);
        } else {
            // 전체 검색
            questionList = questionService.searchQuestions(keyword);
        }

        model.addAttribute("categoryName", categoryName + " 검색결과");
        model.addAttribute("questionList", questionList);
        model.addAttribute("keyword", keyword);

        return "question_category";
    }

    // 새글 작성 폼
    @GetMapping("/create")
    public String createForm(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                             Model model) {
        if (categoryId != null) {
            Category category = questionService.getCategoryById(categoryId);
            model.addAttribute("categoryId", categoryId);
            model.addAttribute("categoryName", category.getName());
        } else {
            // 기본 카테고리 설정 (자존감 마음건강)
            model.addAttribute("categoryId", 1);
            model.addAttribute("categoryName", "자존감 마음건강");
        }

        return "question_create";
    }

    // 새글 작성 처리
    @PostMapping("/create")
    public String createQuestion(@RequestParam("subject") String subject,
                                 @RequestParam("content") String content,
                                 @RequestParam("categoryId") Integer categoryId) {

        questionService.createQuestion(subject, content, categoryId);

        return "redirect:/question/category/" + categoryId;
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);

        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/subject/{subject}")
    public String listBySubject(@PathVariable String subject, Model model) {
        List<Question> filtered = questionService.findBySubjectLike(subject);
        model.addAttribute("questionList", filtered);
        model.addAttribute("selectedSubject", subject);
        return "question_subject_list";
    }
}