package com.uranium.drilling.service.report;

import com.uranium.drilling.entity.report.DrillingPlanReport;
import com.uranium.drilling.entity.report.ProductionReport;
import com.uranium.drilling.entity.drillingAct.Detail;
import com.uranium.drilling.repository.drillingAct.DetailRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final DetailRepository detailRepository;

    public ReportService(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public List<ProductionReport> getProductionReport(Date from, Date to){
        List<Detail> details = detailRepository.findByDateBetween(from, to);

        Map<String, List<Detail>> grouped = details.stream().collect(Collectors.groupingBy(
                d -> d.getDrillingUnit().getName() + "|" + d.getHeader().getDrillHole().getName()
        ));

        List<ProductionReport> productionReports = new ArrayList<>();

        for (List<Detail> group : grouped.values()) {
            if (group.isEmpty()) continue;

            Detail any = group.get(0);

            ProductionReport productionReport = ProductionReport.builder()
                    .drillHoleId(any.getHeader().getDrillHole().getSystemId())
                    .drillingUnitName(any.getDrillingUnit().getName())
                    .areaName(any.getHeader().getDrillHole().getArea().getName())
                    .customerName(any.getHeader().getDrillHole().getArea().getCustomer().getName())
                    .drillingTypeName(any.getHeader().getDrillingType().getName())
                    .drilledDrillHoles(1)
                    .drilledDepth(
                            group.isEmpty() ? 0.0 : group.get(group.size() - 1).getDepth()
                    )
                    .actedDrillHoles(any.getHeader().getDrillHole().getIsActive() == Boolean.TRUE ? 1 : 0)
                    .actedDepth(
                            any.getHeader().getDrillHole().getIsActive() == Boolean.TRUE
                                    ? group.stream()
                                    .filter(Objects::nonNull)
                                    .mapToDouble(d -> d.getActed() != null ? d.getActed() : 0)
                                    .sum()
                                    : 0
                    )
                    .workedTime(group.stream().filter(detail ->
                            !"Простой".equalsIgnoreCase(detail.getWorkSubType().getWorkType().getName()))
                            .mapToDouble(Detail::getWorkedTime).sum())
                    .downtime(group.stream().filter(detail ->
                            "Простой".equalsIgnoreCase(detail.getWorkSubType().getWorkType().getName()))
                            .mapToDouble(Detail::getWorkedTime).sum())
                    .build();
            productionReports.add(productionReport);
        }

        return productionReports;
    }

    public byte[] exportProductionReportToExcel(Date from, Date to) {
        List<ProductionReport> productionReports = getProductionReport(from, to);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Production Report");

            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "Номер скважины", "Агрегат", "Участок", "Заказчик", "Вид бурения",
                    "Скважин пробурено", "Пробурено ПМ", "Скважин актировано", "Актировано ПМ",
                    "БРВ, вахто/час", "Простои, вахто/час"
            };
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (ProductionReport r : productionReports) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(r.getDrillHoleId());
                row.createCell(1).setCellValue(r.getDrillingUnitName());
                row.createCell(2).setCellValue(r.getAreaName());
                row.createCell(3).setCellValue(r.getCustomerName());
                row.createCell(4).setCellValue(r.getDrillingTypeName());
                row.createCell(5).setCellValue(r.getDrilledDrillHoles());
                row.createCell(6).setCellValue(r.getDrilledDepth());
                row.createCell(7).setCellValue(r.getActedDrillHoles());
                row.createCell(8).setCellValue(r.getActedDepth());
                row.createCell(9).setCellValue(r.getWorkedTime());
                row.createCell(10).setCellValue(r.getDowntime());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при экспорте отчёта в Excel", e);
        }
    }

    public List<DrillingPlanReport> getDrillingPlanReport(Date from, Date to) {
        List<Detail> details = detailRepository.findByDateBetween(from, to);

        Map<String, List<Detail>> grouped = details.stream().collect(Collectors.groupingBy(
                d -> d.getDrillingUnit().getName() + "|" + d.getHeader().getDrillHole().getName()
        ));

        List<DrillingPlanReport> drillingPlanReports = new ArrayList<>();

        for (List<Detail> group : grouped.values()) {
            if (group.isEmpty()) continue;

            Detail any = group.get(0);

            DrillingPlanReport report = DrillingPlanReport.builder()
                    .drillHoleId(any.getHeader().getDrillHole().getSystemId())
                    .drillingUnitName(any.getDrillingUnit().getName())
                    .areaName(any.getHeader().getDrillHole().getArea().getName())
                    .customerName(any.getHeader().getDrillHole().getArea().getCustomer().getName())
                    .drillingTypeName(any.getHeader().getDrillingType().getName())
                    .planedDepth(any.getHeader().getDrillHole().getDepth())
                    .drilledDepth(group.get(group.size() - 1).getDepth())
                    .actedDepth(group.stream()
                            .filter(Objects::nonNull)
                            .mapToDouble(d -> d.getActed() != null ? d.getActed() : 0)
                            .sum())
                    .workedTime(group.stream().mapToDouble(Detail::getWorkedTime).sum())
                    .build();

            drillingPlanReports.add(report);
        }

        return drillingPlanReports;
    }

    public byte[] exportDrillingPlanReportToExcel(Date from, Date to) {
        List<DrillingPlanReport> reports = getDrillingPlanReport(from, to);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Drilling Plan Report");

            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "Номер скважины", "Агрегат", "Участок", "Заказчик", "Вид бурения",
                    "План ПМ", "Пробурено", "Актировано", "Отработано (вахто/час)"
            };
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowIdx = 1;
            for (DrillingPlanReport r : reports) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(r.getDrillHoleId());
                row.createCell(1).setCellValue(r.getDrillingUnitName());
                row.createCell(2).setCellValue(r.getAreaName());
                row.createCell(3).setCellValue(r.getCustomerName());
                row.createCell(4).setCellValue(r.getDrillingTypeName());
                row.createCell(5).setCellValue(r.getPlanedDepth());
                row.createCell(6).setCellValue(r.getDrilledDepth());
                row.createCell(7).setCellValue(r.getActedDepth());
                row.createCell(8).setCellValue(r.getWorkedTime());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при экспорте плана бурения в Excel", e);
        }
    }
}
