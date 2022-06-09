package ru.sf;

import ru.sf.parser.XLSXParser;

public class App {
    public static void main(String[] args) {
        XLSXParser xlsxParser = XLSXParser.getInstance();
        xlsxParser.getAllStudentsFromXLSX("src/main/resources/universityInfo.xlsx").forEach(System.out::println);
        xlsxParser.getAllUniversitiesFromXLSX("src/main/resources/universityInfo.xlsx").forEach(System.out::println);
    }
}
