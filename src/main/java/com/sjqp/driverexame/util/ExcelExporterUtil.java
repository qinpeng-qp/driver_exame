package com.sjqp.driverexame.util;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class ExcelExporterUtil {
    public static String EXPORT_FIELD_TYPE_BOOLEAN = "Boolean";
    public static String EXPORT_FIELD_TYPE_INTEGER = "Integer";
    public static String EXPORT_FIELD_TYPE_DOUBLE = "Double";
    public static String EXPORT_FIELD_TYPE_STRING = "String";

    public static Object[][] read(File file, int sheetNum) {
        Workbook wb = null;
        InputStream in = null;
        try {
            in = new PushbackInputStream(new FileInputStream(file), 8);
            if (POIFSFileSystem.hasPOIFSHeader(in)) {
                wb = new HSSFWorkbook(in);
            } else {
                wb = new XSSFWorkbook(in);
            }
            Sheet sheet = wb.getSheetAt(sheetNum);
            return getTable(sheet);
        } catch (Exception e) {
            throw new RuntimeException("read excel exception - ", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException("close excel exception - ", e);
                }
            }
        }
    }

    public static String getStringCellValue(Cell cell) {
        String cellValue = null;
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case 0:
                cellValue = String.valueOf((int) cell.getNumericCellValue());
                break;
            case 1:
                cellValue = cell.getStringCellValue();
                break;
            case 2:
                cellValue = String.valueOf(cell.getDateCellValue());
                break;
            case 3:
                cellValue = "";
                break;
            case 4:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case 5:
                cellValue = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                break;
        }
        return cellValue == null ? null : cellValue.trim();
    }

    private static Object[][] getTable(Sheet sheet) {

        Object[][] list = new Object[sheet.getLastRowNum() + 1][];
        for (int r = sheet.getFirstRowNum(); r <= sheet.getLastRowNum(); r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                // logger.trace("row:{}, null", r);
                list[r] = null;
            } else {
                // logger.trace("row:{}, firstCellNum:{}, lastCellNum:{}",
                // row.getRowNum(), row.getFirstCellNum(),
                // row.getLastCellNum());
                if (row.getLastCellNum() <= 0) {
                    continue;
                }
                list[r] = new Object[row.getLastCellNum()];
                for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
                    Cell cell = row.getCell(c);
                    cell.setCellType(1);
                    // logger.trace("row:{}, cell:{}, value:{}",
                    // row.getRowNum(), c, cell);
                    list[r][c] = getValueFromCell(cell);
                }
            }
        }
        return list;
    }

    private static final Object getValueFromCell(Cell cell) {
        if (cell == null) {
            return StringUtils.EMPTY;
        }
        Object value = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    value = cell.getDateCellValue();
                } else {
                    value = cell.getNumericCellValue();
                }
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_FORMULA:
                double numericValue = cell.getNumericCellValue();
                if (HSSFDateUtil.isValidExcelDate(numericValue)) { // 如果是日期类型
                    value = cell.getDateCellValue();
                } else {
                    value = String.valueOf(numericValue);
                }
                break;
            case Cell.CELL_TYPE_BLANK:
                value = StringUtils.EMPTY;
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR: // Error，返回错误码
                value = String.valueOf(cell.getErrorCellValue());
                break;
            default:
                value = StringUtils.EMPTY;
                break;
        }
        return value;
    }

    public static <T> void exportToExcel(HttpServletRequest request,
                                         HttpServletResponse response, List<T> data, String[] fields,
                                         String[] fieldValue, String[] fieldsType) {
        exportToExcel(request, response, data, fields, fieldValue, fieldsType, null);
    }

    public static <T> void exportToExcel(HttpServletRequest request,
                                         HttpServletResponse response, List<T> data, String[] fields,
                                         String[] fieldValue, String[] fieldsType, String fileName) {

        if (fileName == null || "".equals(fileName)) {
            fileName = System.currentTimeMillis() + ".xls";
        }

        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename="
                + fileName);

        try {
            exportToExcel(response.getOutputStream(), data, fields, fieldValue, fieldsType);
        } catch (IOException e) {
            throw new RuntimeException("export data error", e);
        }
    }

    public static <T> void exportToExcel(OutputStream os, List<T> data,
                                         String[] fields, String[] fieldValue, String[] fieldsType) {
        if (fields == null || fieldValue == null) {
            throw new RuntimeException("fields or header can't be null.");
        }

        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("ExportDataList");

        // Create a row and put some cells in it. Rows are 0 based.
        Row headerTitle = sheet.createRow(0);
        int index = 0;
        for (int i = 0; i < fields.length; i++) {
            Cell cell = headerTitle.createCell(index++ % fields.length);
            cell.setCellValue(fieldValue[i]);
        }

        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            T o = data.get(i);

            for (int j = 0; j < fields.length; j++) {
                Cell cell = row.createCell(index++ % fields.length);

                try {
                    if (fieldsType[j].equals(ExcelExporterUtil.EXPORT_FIELD_TYPE_INTEGER)) {
                        cell.setCellValue(Integer.parseInt(BeanUtils.getProperty(o, fields[j])));
                    } else if (fieldsType[j].equals(ExcelExporterUtil.EXPORT_FIELD_TYPE_BOOLEAN)) {
                        cell.setCellValue(Boolean.parseBoolean(BeanUtils.getProperty(o, fields[j])));
                    } else if (fieldsType[j].equals(ExcelExporterUtil.EXPORT_FIELD_TYPE_DOUBLE)) {
                        cell.setCellValue(Double.parseDouble(BeanUtils.getProperty(o, fields[j])));
                    } else {
                        cell.setCellValue(BeanUtils.getProperty(o, fields[j]));
                    }
                } catch (Exception e) {
                    throw new RuntimeException("export data error", e);
                }
            }
        }

        try {
            wb.write(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            throw new RuntimeException("export data error", e);
        }
    }
}
