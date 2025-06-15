package com.sihe.spring.boundedContext.answer;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    @Modifying //INSERT ,UPDATE DELETE와 같은 데이터가 변경 작업에서만 사용
    @Transactional//데이터 COMMIT
    @Query(value = "ALTER TABLE answer AUTO_INCREMENT=1", nativeQuery = true)
    void clearAutoIncrement();

}
