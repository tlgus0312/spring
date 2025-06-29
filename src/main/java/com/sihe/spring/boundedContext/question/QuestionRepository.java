package com.sihe.spring.boundedContext.question;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);


    // 카테고리별 질문 조회 - 새로 추가된 메서드
    @Query("SELECT q FROM Question q WHERE q.category.id = :categoryId ORDER BY q.createDate DESC")
    List<Question> findByCategoryId(@Param("categoryId") Integer categoryId);

    @Modifying //INSERT ,UPDATE DELETE와 같은 데이터가 변경 작업에서만 사용
    @Transactional//데이터 COMMIT
    @Query(value = "ALTER TABLE question AUTO_INCREMENT=1", nativeQuery = true)
    void clearAutoIncrement();
// 기존 메서드들은 그대로 두고, 이것들만 추가하세요

    // 전체 검색 (제목 또는 내용)
    @Query("SELECT q FROM Question q WHERE q.subject LIKE %:keyword% OR q.content LIKE %:keyword% ORDER BY q.createDate DESC")
    List<Question> findBySubjectContainingOrContentContaining(@Param("keyword") String keyword1, @Param("keyword") String keyword2);

    // 카테고리 내 검색
    @Query("SELECT q FROM Question q WHERE q.category.id = :categoryId AND (q.subject LIKE %:keyword% OR q.content LIKE %:keyword%) ORDER BY q.createDate DESC")
    List<Question> findByCategoryIdAndSubjectContainingOrContentContaining(
            @Param("categoryId") Integer categoryId,
            @Param("keyword") String keyword1,
            @Param("keyword") String keyword2);

}
