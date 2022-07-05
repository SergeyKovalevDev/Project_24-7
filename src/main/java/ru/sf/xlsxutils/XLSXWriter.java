package ru.sf.xlsxutils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sf.models.Statistics;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XLSXWriter {

    public void workbookCreateAndWrite(List<Statistics> statisticsList, String filename) {
        try (Workbook workbook = new XSSFWorkbook()) {

            // Logger configuration
            String loggerName = this.getClass().getSimpleName() + ".class." + new Object(){}.getClass().getEnclosingMethod().getName() + "()";
            Logger logger = LoggerFactory.getLogger(loggerName);


            Sheet sheet = workbook.createSheet("Статистика");

            Font headerFont = workbook.createFont();
            headerFont.setFontName("Arial");
            headerFont.setBold(true);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            createHeaderRow(sheet, 0, headerCellStyle);

            int rowCount = 1;
            for (Statistics statistics : statisticsList) {
                createContentRow(sheet, rowCount++, statistics);
            }

            try (FileOutputStream out = new FileOutputStream(filename)) {
                workbook.write(out);
                logger.info("Writing to a file \"{}\" successful", filename);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createHeaderRow(Sheet sheet, int rowNumber, CellStyle cellStyle) {
        Row row = sheet.createRow(rowNumber);
        row.createCell(0).setCellValue("Профиль обучения");
        row.getCell(0).setCellStyle(cellStyle);
        row.createCell(1).setCellValue("Количество университетов");
        row.getCell(1).setCellStyle(cellStyle);
        row.createCell(2).setCellValue("Названия университетов");
        row.getCell(2).setCellStyle(cellStyle);
        row.createCell(3).setCellValue("Количество студентов");
        row.getCell(3).setCellStyle(cellStyle);
        row.createCell(4).setCellValue("Средний балл");
        row.getCell(4).setCellStyle(cellStyle);
    }

    private void createContentRow(Sheet sheet, int rowNumber, Statistics statistics) {
        Row row = sheet.createRow(rowNumber);
        row.createCell(0).setCellValue(statistics.getMainProfile().toString());
        row.createCell(1).setCellValue(statistics.getNumberOfUniversities());
        row.createCell(2).setCellValue(createUniversityNames(statistics.getUniversityName()));
        row.createCell(3).setCellValue(statistics.getNumberOfStudents());
        row.createCell(4).setCellValue(statistics.getAvgExamScore());
    }

    private String createUniversityNames(List<String> universityNames) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String universityName : universityNames) {
            stringBuilder.append(universityName).append(", ");
        }
        return stringBuilder.substring(0, stringBuilder.length()-2);
    }
}
