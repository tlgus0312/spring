package com.sihe.spring.boundedContext.answer;

import com.sihe.spring.boundedContext.question.Question;
import com.sihe.spring.service.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;


    public  Answer create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answerRepository.save(answer);

        return answer;
    }

    // 답변 ID로 답변 객체를 찾아주는 메서드
    public Answer getAnswer(Integer id) {
        return this.answerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Answer not found"));
    }

    // 채택 로직
    @Transactional
    public void adopt(Integer answerId) {
        Answer answerToAdopt = this.getAnswer(answerId);
        Question question = answerToAdopt.getQuestion();

        // 이 질문에 달린 다른 모든 답변들을 '미채택' 상태로 변경
        question.getAnswerList().forEach(answer -> answer.setAdopted(false));

        // 현재 선택한 답변을 '채택' 상태로 변경
        answerToAdopt.setAdopted(true);

        // 변경된 모든 답변 상태를 DB에 저장 (명시적 저장)
        this.answerRepository.saveAll(question.getAnswerList());
    }
    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
    public void cancelAdopt(Integer answerId) {
        Optional<Answer> answerOpt = answerRepository.findById(answerId);
        if (answerOpt.isPresent()) {
            Answer answer = answerOpt.get();
            answer.setAdopted(false);
            answerRepository.save(answer);
        }
    }
    // AnswerService.java에 추가
    public boolean cancelAdoption(Integer answerId, String password) {
        Optional<Answer> answerOpt = answerRepository.findById(answerId);
        if (answerOpt.isEmpty()) {
            return false;
        }

        Answer answer = answerOpt.get();
        Question question = answer.getQuestion();

        // 질문 작성자의 비밀번호 확인
        if (!BCrypt.checkpw(password, question.getPassword())) {
            return false;
        }

        // 채택 취소
        answer.setAdopted(false);
        answerRepository.save(answer);

        return true;
    }

}