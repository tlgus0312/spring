package com.sihe.spring.boundedContext.report;

import com.sihe.spring.boundedContext.answer.Answer;
import com.sihe.spring.boundedContext.question.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Answer answer;

    @Enumerated(EnumType.STRING)
    private ReportReason reason;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String reporterIp;

    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.PENDING;

    private LocalDateTime createDate;
    private LocalDateTime processDate;

    private String targetTitle;
    @Column(length = 1000)
    private String targetContent;
}