package ru.sf.xlsxutils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.sf.models.Statistics;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class XLSXWriter {

    public void workbookCreateAndWrite(List<Statistics> statisticsList, String filename) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Статистика");

            Font font = workbook.createFont();
            font.setFontName("Arial");
            font.setBold(true);
            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(font);
            createHeaderRow(sheet, 0, headerCellStyle);

            int rowCount = 1;
            for (Statistics statistics : statisticsList) {
                createContentRow(sheet, rowCount++, statistics);
            }

            try (FileOutputStream out = new FileOutputStream(filename)) {
                workbook.write(out);
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
        row.createCell(2).setCellValue("names");
        row.createCell(3).setCellValue(statistics.getNumberOfStudents());
        row.createCell(4).setCellValue(statistics.getAvgExamScore());
    }
}
