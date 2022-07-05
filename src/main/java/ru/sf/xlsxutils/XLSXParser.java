package ru.sf.xlsxutils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.sf.App;
import ru.sf.enums.StudyProfile;
import ru.sf.models.Student;
import ru.sf.models.University;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XLSXParser {

    private static volatile XLSXParser INSTANCE;
//    private static final Logger logger = LoggerFactory.getLogger(XLSXParser.class);
    private static final String[] STUDENT_HEADER_VALIDATOR = {"id университета", "ФИО", "Курс", "Средний балл"};
    private static final CellType[] STUDENT_ROW_VALIDATOR = {CellType.STRING, CellType.STRING, CellType.NUMERIC, CellType.NUMERIC};
    private static final String[] UNIVERSITY_HEADER_VALIDATOR = {"id университета", "Полное название", "Аббревиатура", "Год основания", "Профиль обучения"};
    private static final CellType[] UNIVERSITY_ROW_VALIDATOR = {CellType.STRING, CellType.STRING, CellType.STRING, CellType.NUMERIC, CellType.STRING};

    private XLSXParser() {
    }

    public static XLSXParser getInstance() {
        if (INSTANCE == null) {
            synchronized (XLSXParser.class) {
                if (INSTANCE == null) {
                    INSTANCE = new XLSXParser();
                }
            }
        }
        return INSTANCE;
    }

    public List<Student> getAllStudentsFromXLSX(Path filePath) {

        // Logger configuration
        String loggerName = this.getClass().getSimpleName() + ".class." + new Object(){}.getClass().getEnclosingMethod().getName() + "()";
        Logger logger = LoggerFactory.getLogger(loggerName);

        logger.info("Parsing a file \"{}\"", filePath.getFileName());
        List<Student> studentList = new ArrayList<>();
        try (InputStream stream = Files.newInputStream(filePath, StandardOpenOption.READ);
             XSSFWorkbook workbook = new XSSFWorkbook(stream)) {
            int sheetNumber = Integer.parseInt(App.properties.getProperty("STUDENT_SHEET_NUMBER"));
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            logger.info("Reading sheet number {} \"{}\"", sheetNumber, sheet.getSheetName());
            Iterator<Row> rowIterator = sheet.rowIterator();
            Row header = rowIterator.next();
            if (isHeaderValid(header, STUDENT_HEADER_VALIDATOR)) {
                logger.info("The header of the sheet is valid");
                int counter = 0;
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (isRowValid(row, STUDENT_ROW_VALIDATOR)) {
                        Iterator<Cell> cells = row.iterator();
                        Student student = new Student.Builder()
                                .withUniversityId(cells.next().getStringCellValue())
                                .withFullName(cells.next().getStringCellValue())
                                .withCurrentCourseNumber((int) cells.next().getNumericCellValue())
                                .withAvgExamScore((float) cells.next().getNumericCellValue())
                                .build();
                        if (student != null) {
                            studentList.add(student);
                            counter++;
                        } else {
                            logger.warn("In the {} row there are errors in the cells. Not added to the list", row.getRowNum());
                        }
                    } else {
                        logger.warn("The {} row has the wrong cell type. Not added to the list", row.getRowNum());
                    }
                }
                logger.info("{} students added to the list", counter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public List<University> getAllUniversitiesFromXLSX(Path filePath) {

        // Logger configuration
        String loggerName = this.getClass().getSimpleName() + ".class." + new Object(){}.getClass().getEnclosingMethod().getName() + "()";
        Logger logger = LoggerFactory.getLogger(loggerName);

        logger.info("Parsing a file \"{}\"", filePath.getFileName());
        List<University> universityList = new ArrayList<>();
        try (InputStream stream = Files.newInputStream(filePath, StandardOpenOption.READ);
             XSSFWorkbook workbook = new XSSFWorkbook(stream)) {
            int sheetNumber = Integer.parseInt(App.properties.getProperty("UNIVERSITY_SHEET_NUMBER"));
            Sheet sheet = workbook.getSheetAt(sheetNumber);
            logger.info("Reading sheet number {} \"{}\"", sheetNumber, sheet.getSheetName());
            Iterator<Row> rowIterator = sheet.rowIterator();
            Row header = rowIterator.next();
            if (isHeaderValid(header, UNIVERSITY_HEADER_VALIDATOR)) {
                logger.info("The header of the sheet is valid");
                int counter = 0;
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (isRowValid(row, UNIVERSITY_ROW_VALIDATOR)) {
                        Iterator<Cell> cells = row.iterator();
                        University university = new University.Builder()
                                .withId(cells.next().getStringCellValue())
                                .withFullName(cells.next().getStringCellValue())
                                .withShortName(cells.next().getStringCellValue())
                                .withYearOfFoundation((int) cells.next().getNumericCellValue())
                                .withMainProfile(StudyProfile.valueOf(cells.next().getStringCellValue()))
                                .build();
                        if (university != null) {
                            universityList.add(university);
                            counter++;
                        } else {
                            logger.warn("In the {} row there are errors in the cells. Not added to the list", row.getRowNum());
                        }
                    } else {
                        logger.warn("The {} row has the wrong cell type. Not added to the list", row.getRowNum());
                    }
                }
                logger.info("{} universities added to the list", counter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return universityList;
    }

    private boolean isRowValid(Row row, CellType[] validator) {
        Iterator<Cell> cells = row.iterator();
        for (CellType cellType : validator) {
            if (cells.next().getCellType() != cellType) {
                return false;
            }
        }
        return true;
    }

    private boolean isHeaderValid(Row header, String[] validator) {
        Iterator<Cell> cells = header.iterator();
        for (String cellName : validator) {
            Cell cell = cells.next();
            if (cell.getCellType() != CellType.STRING || !cell.getStringCellValue().equals(cellName)) {
                return false;
            }
        }
        return true;
    }
}