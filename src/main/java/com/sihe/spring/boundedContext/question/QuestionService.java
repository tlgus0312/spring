package com.sihe.spring.boundedContext.question;

import com.sihe.spring.boundedContext.answer.Answer;
import com.sihe.spring.boundedContext.answer.AnswerRepository;
import com.sihe.spring.service.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

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
    //private final PasswordEncoder passwordEncoder;
    //private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    // 새글 작성 (비밀번호 포함) - 수정됨
    public Question createQuestion(String subject, String content, Integer categoryId, String password) {
        Category category = getCategoryById(categoryId);

        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCategory(category);
        question.setCreateDate(LocalDateTime.now());
        // BCrypt로 비밀번호 암호화
        // ✅ BCrypt.hashpw()를 사용해 비밀번호 암호화
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        question.setPassword(hashedPassword);

        return questionRepository.save(question);
    }


    // 질문 삭제 (비밀번호 확인) - 수정됨
    public boolean deleteQuestion(Integer questionId, String plainPassword) {
        Optional<Question> questionOpt = questionRepository.findById(questionId);
        if (questionOpt.isEmpty()) {
            return false;
        }

        Question question = questionOpt.get();
        String hashedPassword = question.getPassword();

        // BCrypt로 비밀번호 확인
        if (BCrypt.checkpw( plainPassword, hashedPassword)) {
            questionRepository.delete(question); // 일치하면 삭제
            return true;
        }

        return false;
    } public boolean modifyQuestion(Integer questionId, String subject, String content, String plainPassword) {
        // 1. ID로 질문을 찾습니다.
        Optional<Question> questionOpt = questionRepository.findById(questionId);
        if (questionOpt.isEmpty()) {
            return false; // 질문이 없으면 실패
        }

        Question question = questionOpt.get();
        String hashedPassword = question.getPassword();

        // 2. 비밀번호를 확인합니다.
        if (!BCrypt.checkpw(plainPassword, hashedPassword)) {
            return false; // 비밀번호가 틀리면 실패
        }

        // 3. 비밀번호가 맞으면 내용을 수정하고 저장합니다.
        question.setSubject(subject);
        question.setContent(content);
        questionRepository.save(question);

        return true; // 성공
    }

}

