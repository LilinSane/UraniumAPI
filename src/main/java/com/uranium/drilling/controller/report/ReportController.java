package com.uranium.drilling.controller.report;

import com.uranium.drilling.entity.report.DrillingPlanReport;
import com.uranium.drilling.entity.report.ProductionReport;
import com.uranium.drilling.service.report.ReportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reports")
@CrossOrigin(origins = "http://localhost:4200")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/production-report")
    public ResponseEntity<List<ProductionReport>> getProductionReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to
    ){
        return ResponseEntity.ok(reportService.getProductionReport(from, to));
    }

    @GetMapping("/production-report/export")
    public ResponseEntity<Resource> exportProductionReportToExcel(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to
    ) {
        byte[] excelData = reportService.exportProductionReportToExcel(from, to);
        ByteArrayResource resource = new ByteArrayResource(excelData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=production_report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(excelData.length)
                .body(resource);
    }

    @GetMapping("/drilling-plan-report")
    public ResponseEntity<List<DrillingPlanReport>> getDrillingPlanReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to
    ) {
        return ResponseEntity.ok(reportService.getDrillingPlanReport(from, to));
    }

    @GetMapping("/drilling-plan-report/export")
    public ResponseEntity<Resource> exportDrillingPlanReportToExcel(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to
    ) {
        byte[] excelData = reportService.exportDrillingPlanReportToExcel(from, to);
        ByteArrayResource resource = new ByteArrayResource(excelData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=drilling_plan_report.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(excelData.length)
                .body(resource);
    }
}
