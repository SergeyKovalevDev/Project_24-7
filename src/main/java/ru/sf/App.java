package ru.sf;

public class App {
    public static void main(String[] args) {
        XLSXParser xlsxParser = XLSXParser.getInstance();
        xlsxParser.getAllStudentsFromXLSX().forEach(System.out::println);
        xlsxParser.getAllUniversitiesFromXLSX().forEach(System.out::println);
    }
}
