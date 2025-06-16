package com.sihe.spring.boundedContext.question;

import com.sihe.spring.service.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question getQuestion  (Integer id) throws DataNotFoundException {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isEmpty()) throw new DataNotFoundException("question not found");

            return question.get();

    }
}

