package ru.sf.xlsxutils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.sf.exceptions.AppException;
import ru.sf.models.Statistics;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class XLSXWriter {

    public static final Logger logger = Logger.getLogger(XLSXWriter.class.getName());

    public static void createWorkbook(List<Statistics> statisticsList, String destFilename) throws AppException {

        try (Workbook workbook = new XSSFWorkbook()) {

            String sheetName = "Статистика";
            Sheet sheet = workbook.createSheet(sheetName);
            logger.info("Created workbook with sheet \"" + sheetName + "\"");

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
            logger.info((rowCount - 1) + " rows added into sheet \"" + sheetName + "\"");

            writeWorkbook(workbook, destFilename);

        } catch (IOException e) {
            throw new AppException("Error creating a workbook");
        }
    }

    private static void writeWorkbook(Workbook workbook, String filename) throws AppException {

        try (FileOutputStream out = new FileOutputStream(filename)) {
            workbook.write(out);
            logger.info("Writing to a file \"" + filename + "\" successful");
        } catch (IOException e) {
            throw new AppException("Error saving the \"" + filename + "\" file");
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
