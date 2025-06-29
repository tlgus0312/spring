package com.sihe.spring.boundedContext.question;

import com.sihe.spring.boundedContext.answer.Answer;
import com.sihe.spring.boundedContext.answer.AnswerRepository;
import com.sihe.spring.service.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final CategoryRepository categoryRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question getQuestion  (Integer id) throws DataNotFoundException {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isEmpty()) throw new DataNotFoundException("question not found");

        return question.get();

    }
    public List<Question> findBySubjectLike(String subject) {
        return questionRepository.findBySubjectLike("%" + subject + "%");
    }

    public Answer create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answerRepository.save(answer);

        return answer;
    }
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
    public Category getCategoryById(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) throw new DataNotFoundException("category not found");
        return category.get();
    }



    public List<Question> getQuestionsByCategory(Integer categoryId) {
        return questionRepository.findByCategoryId(categoryId);
    }

    public List<Question> searchQuestions(String keyword) {
        return questionRepository.findBySubjectContainingOrContentContaining(keyword, keyword);
    }

    public List<Question> searchInCategory(String keyword, Integer categoryId) {
        return questionRepository.findByCategoryIdAndSubjectContainingOrContentContaining(
                categoryId, keyword, keyword);
    }

    public Question createQuestion(String subject, String content, Integer categoryId) {
        Category category = getCategoryById(categoryId);

        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCategory(category);
        question.setCreateDate(LocalDateTime.now());

        return questionRepository.save(question);
    }
}

