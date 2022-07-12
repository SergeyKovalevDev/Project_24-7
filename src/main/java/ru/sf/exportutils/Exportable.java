package ru.sf.exportutils;

import ru.sf.exceptions.AppException;

import java.nio.file.Path;

public interface Exportable {

    void exportToFile(ExportStructure structure, Path filePath) throws AppException;
}
