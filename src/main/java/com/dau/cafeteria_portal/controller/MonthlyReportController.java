package com.dau.cafeteria_portal.controller;

import com.dau.cafeteria_portal.dto.MonthlyReportDTO;
import com.dau.cafeteria_portal.service.MonthlyReportService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/admin/reports")
public class MonthlyReportController {

    private final MonthlyReportService monthlyReportService;

    public MonthlyReportController(MonthlyReportService monthlyReportService) {
        this.monthlyReportService = monthlyReportService;
    }

    @GetMapping("/monthly")
    public MonthlyReportDTO generateMonthlyReport(
            @RequestParam("month") @DateTimeFormat(pattern = "yyyy-MM") YearMonth month
    ) {
        return monthlyReportService.generateMonthlyReport(month);
    }
}
