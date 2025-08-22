package com.sihe.spring.boundedContext.report;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    // 특정 상태의 신고만 찾아오는 메서드
    List<Report> findByStatus(ReportStatus status);
}