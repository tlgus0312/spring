package com.sihe.spring.boundedContext.question;


import com.sihe.spring.boundedContext.answer.Answer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity//spring boot가 question을 entity로 봄
public class Question {
    @Id//primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Auto_increment
    private Integer id;//int id

    @Column(length = 200)//varchar(200)
    private String subject;

    @Column(columnDefinition = "TEXT")//text
    private String content;

    private LocalDateTime createDate;//datetime

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;






}
