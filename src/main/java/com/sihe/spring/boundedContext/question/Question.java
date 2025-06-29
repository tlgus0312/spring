package com.sihe.spring.boundedContext.question;

import com.sihe.spring.boundedContext.answer.Answer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity  // Spring Boot가 이 클래스를 엔티티로 인식
@ToString(exclude = "answerList")  // 순환 참조 방지를 위해 answerList는 제외
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @Column(length = 100)
    private String password;

    // ─── 카테고리 연관관계 ────────────────────
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")   // question 테이블의 category_id 컬럼과 매핑
    private Category category;

    // ─── 답변 연관관계 ───────────────────────
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList = new ArrayList<>();

    /** 답변 추가 유틸 메서드 */
    public void addAnswer(Answer a) {
        a.setQuestion(this);
        answerList.add(a);
    }
}
