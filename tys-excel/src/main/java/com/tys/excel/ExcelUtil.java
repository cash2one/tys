package com.tys.excel;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Excel工具类
 */
public class ExcelUtil {

    public static String getCellText(Cell cell) {
        Object obj = getCellValue(cell);

        return obj == null ? "" : obj.toString();
    }

    public static Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                String str = cell.getRichStringCellValue().getString().trim();

                return str.isEmpty() ? null : str;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    double value = cell.getNumericCellValue();

                    if ((value + "").endsWith(".0")) {
                        return Long.parseLong(StringUtils.removeEnd(value + "", ".0"));
                    }
                    
                    if(String.valueOf(value).contains("E")){
                    	return new BigDecimal(String.valueOf(value)).toPlainString();
                    }

                    return value;
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue();
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    public static boolean isEmptyLine(Sheet sheet, int row, int startDataColumn, int columnCount) {
        if (sheet.getRow(row) == null) {
            return true;
        }

        StringBuilder lineStr = new StringBuilder();
        // 判断是不是结束行
        for (int column = startDataColumn; column < startDataColumn + columnCount; column++) {
            Object cellValue = ExcelUtil.getCellValue(sheet.getRow(row).getCell(column));
            if (cellValue != null) {
                lineStr.append(cellValue);
            }
        }

        return lineStr.length() == 0;
    }
}
