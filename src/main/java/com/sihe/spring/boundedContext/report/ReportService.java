package com.sihe.spring.boundedContext.report;

import com.sihe.spring.boundedContext.answer.Answer;
import com.sihe.spring.boundedContext.answer.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final AnswerService answerService; // 답변을 삭제하기 위해 필요

    // 새로운 신고를 생성하는 메서드
    public void create(Answer answer, String content) {
        Report report = new Report();
        report.setAnswer(answer);
        report.setContent(content);
        report.setStatus(ReportStatus.PENDING); // 신고 접수 시 '처리 대기' 상태
        report.setCreateDate(LocalDateTime.now());
        reportRepository.save(report);
    }

    // 특정 상태의 신고 목록을 가져오는 메서드
    public List<Report> findByStatus(ReportStatus status) {
        return reportRepository.findByStatus(status);
    }

    // 신고를 처리하고, 연결된 답변을 삭제하는 메서드
    @Transactional
    public void processReportAndDeleteContent(Integer reportId) {
        // 1. 신고 ID로 신고 정보를 가져옵니다.
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid report Id:" + reportId));

        // 2. 신고된 답변을 가져옵니다.
        Answer answerToDelete = report.getAnswer();

        // 3. 답변을 삭제합니다. (AnswerService에 delete 메서드가 필요합니다)
        if (answerToDelete != null) {
            answerService.delete(answerToDelete);
        }

        // 4. 신고 상태를 '처리 완료'로 변경합니다.
        report.setStatus(ReportStatus.PROCESSED);
        reportRepository.save(report);
    }
}