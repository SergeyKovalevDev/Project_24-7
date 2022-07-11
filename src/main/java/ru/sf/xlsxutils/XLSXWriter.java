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

    private static final String className = XLSXWriter.class.getSimpleName() + ".class.";

    public static Workbook createWorkbook(List<Statistics> statisticsList) {

        // Logger configuration
        String loggerName = className + new Object() {}.getClass().getEnclosingMethod().getName() + "()";
        Logger logger = LoggerFactory.getLogger(loggerName);

        try (Workbook workbook = new XSSFWorkbook()) {


            // workbook create
            String sheetName = "Статистика";
            Sheet sheet = workbook.createSheet(sheetName);
            logger.info("Created workbook with sheet \"{}\"", sheetName);

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
            logger.info("{} rows added into sheet \"{}\"", rowCount - 1, sheetName);
            return workbook;

        } catch (IOException e) {
            logger.error("Error creating a workbook\n{}", e.toString());
            throw new RuntimeException(e);//TODO разобраться как поступить при этой ошибке
        }
    }

    public static void writeWorkbook(Workbook workbook, String filename) {

        // Logger configuration
        String loggerName = className + new Object() {}.getClass().getEnclosingMethod().getName() + "()";
        Logger logger = LoggerFactory.getLogger(loggerName);


        try (FileOutputStream out = new FileOutputStream(filename)) {
            workbook.write(out);
            logger.info("Writing to a file \"{}\" successful", filename);
        } catch (IOException e) {
            logger.error("Error saving the \"{}\" file\n{}", filename, e.toString());
        }
    }


    private static void createHeaderRow(Sheet sheet, int rowNumber, CellStyle cellStyle) {
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

    private static void createContentRow(Sheet sheet, int rowNumber, Statistics statistics) {
        Row row = sheet.createRow(rowNumber);
        row.createCell(0).setCellValue(statistics.getMainProfile().toString());
        row.createCell(1).setCellValue(statistics.getNumberOfUniversities());
        row.createCell(2).setCellValue(createUniversityNames(statistics.getUniversityName()));
        row.createCell(3).setCellValue(statistics.getNumberOfStudents());
        row.createCell(4).setCellValue(statistics.getAvgExamScore());
    }

    private static String createUniversityNames(List<String> universityNames) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String universityName : universityNames) {
            stringBuilder.append(universityName).append(", ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 2);
    }
}
