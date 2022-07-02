package ru.sf.xlsxutils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.sf.models.Statistics;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class XLSXWriter {

    private final String[] header;
    private final CellType headerCellType;
    private final CellStyle headerCellStyle;
    private final CellType[] cellTypes;

    public XLSXWriter(String[] header, CellType headerCellType, CellStyle headerCellStyle, CellType[] cellTypes) {
        this.header = header;
        this.headerCellType = headerCellType;
        this.headerCellStyle = headerCellStyle;
        this.cellTypes = cellTypes;
    }

    public void tableCreate(List<Statistics> statisticsList, Path filepath) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Статистика");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void headerCreator(Sheet sheet, String[] header) {

        for (String str :
                header) {
            sheet.createRow(0).createCell(0);
        }
    }

    private void rowCreator(Row row, String[] content, CellType[] cellTypes) {

        for (CellType cellType : cellTypes) {
            row.createCell(0, cellType);
        }


    }

    private void createHeaderRow(Sheet sheet) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < header.length; i++) {
            Cell cell = row.createCell(i, headerCellType);
            cell.setCellValue(header[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }
}
