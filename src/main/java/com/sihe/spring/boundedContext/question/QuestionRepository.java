package com.sihe.spring.boundedContext.question;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    @Modifying //INSERT ,UPDATE DELETE와 같은 데이터가 변경 작업에서만 사용
    @Transactional//데이터 COMMIT
    @Query(value = "ALTER TABLE question AUTO_INCREMENT=1", nativeQuery = true)
    void clearAutoIncrement();
}
