package com.sihe.spring.boundedContext.answer;

import com.sihe.spring.boundedContext.question.Question;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    @ToString.Exclude//ToString에서 제외
    private Question question;

    @ColumnDefault("0") // DB에 기본값을 0으로 설정
    private boolean adopted; // 추천 수를 저장할 필드
}
