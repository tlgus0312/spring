package com.sihe.spring.boundedContext.question;


import com.sihe.spring.boundedContext.answer.Answer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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
    private List<Answer> answerList= new ArrayList<>();

    //외부에서 answerList필드에 접근을 막는다.
    public void addAnswer(Answer a){
        a.setQuestion(this);//question객체에 answer를 추가
        answerList.add(a);//answer 객체에 question추가
    }






}
