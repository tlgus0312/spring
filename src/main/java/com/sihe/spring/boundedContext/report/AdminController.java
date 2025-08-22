package com.sihe.spring.boundedContext.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ReportService reportService;

    // 신고 목록 페이지
    @GetMapping("/reports")
    public String reportList(Model model) {
        List<Report> reports = reportService.findByStatus(ReportStatus.PENDING);
        model.addAttribute("reports", reports);
        return "admin/report_list"; // 관리자용 HTML 페이지
    }

    // 신고된 콘텐츠 삭제 처리
    @PostMapping("/reports/process/{reportId}/delete")
    public String processReportAndDelete(@PathVariable Integer reportId) {
        reportService.processReportAndDeleteContent(reportId);
        return "redirect:/admin/reports";
    }
}