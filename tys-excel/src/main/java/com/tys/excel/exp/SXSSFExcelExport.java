package com.tys.excel.exp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * 生成Excel时候用到的调用主类
 * 
 */
public class SXSSFExcelExport {

    private Workbook wb;

    private ExportDrawPoint currentPoint = new ExportDrawPoint();

    private int maxSheetId = 1, selectedSheetIndex = 0;

    private List<String> sheetNames = new ArrayList<String>();

    private ConversionService conversionService = new DefaultConversionService();

    public SXSSFExcelExport(Workbook wb) {
        this.wb = wb;
    }

    public void beans2Excel(ExportSheet... sheets) throws Exception {
        for (int sheetIndex = 0; sheetIndex < sheets.length; sheetIndex++) {
            selectedSheetIndex = sheetIndex;

            wb.createSheet();

            ExportSheet sheet = sheets[sheetIndex];

            prepare(sheet, sheet.getDataList().size());

            // 设置数据
            for (int i = 0; i < sheet.getDataList().size(); i++) {
                Object line = sheet.getDataList().get(i);
                int columnIndex = currentPoint.column;
                for (ExportColumn column : sheet.getColumns()) {
                    String beanProperty = StringUtils.isNotBlank(column.getBeanProperty()) ? column.getBeanProperty()
                            : column.getColumnHeadName();

                    setValue(currentPoint.row, columnIndex, BeanUtils.getProperty(line, beanProperty));

                    columnIndex++;
                }
                Range range = new Range(currentPoint.row, currentPoint.column, currentPoint.row, currentPoint.column
                        + sheet.getColumns().size() - 1);

                setRangeStyle(range, true, false, false);

                if (sheet.getExportCallBack() != null) {
                    sheet.getExportCallBack().onDrawDataLine(wb, range, i);
                }

                currentPoint.row++;
            }
        }
    }

    public void arrays2Excel(ExportSheet... sheets) throws Exception {
        for (int sheetIndex = 0; sheetIndex < sheets.length; sheetIndex++) {
            selectedSheetIndex = sheetIndex;

            wb.createSheet();

            ExportSheet sheet = sheets[sheetIndex];

            prepare(sheet, sheet.getDataArray().size());

            // 设置数据
            for (int i = 0; i < sheet.getDataArray().size(); i++) {
                Object[] line = sheet.getDataArray().get(i);
                setValue(currentPoint.row, currentPoint.column, line);

                Range range = new Range(currentPoint.row, currentPoint.column, currentPoint.row, currentPoint.column
                        + sheet.getColumns().size() - 1);

                setRangeStyle(range, true, false, false);

                if (sheet.getExportCallBack() != null) {
                    sheet.getExportCallBack().onDrawDataLine(wb, range, i);
                }

                currentPoint.row++;

            }
        }
    }

    private void prepare(ExportSheet sheet, int dataSize) throws Exception {
        // 增加sheet
        if (!StringUtils.isNotBlank(sheet.getSheetName())) {
            while (sheetNames.contains("sheet" + maxSheetId)) {
                maxSheetId++;
            }
            sheet.setSheetName("sheet" + maxSheetId);
            maxSheetId++;
        }

        sheetNames.add(sheet.getSheetName().toLowerCase());

        currentPoint.row = sheet.getStartPoint().row;
        currentPoint.column = sheet.getStartPoint().column;

        // 设置sheet名
        wb.setSheetName(selectedSheetIndex, sheet.getSheetName());

        // 设置标题
        List<ExportColumn> columns = sheet.getColumns();
        Range range = null;
        if (StringUtils.isNotBlank(sheet.getTitle())) {

            if (columns.size() % 2 == 0) {
                setCellValue(currentPoint.row, currentPoint.column + columns.size() / 2 - 1, sheet.getTitle());
                range = new Range(currentPoint.row, currentPoint.column + columns.size() / 2 - 1, currentPoint.row,
                        currentPoint.column + columns.size() / 2);
                wb.getSheetAt(selectedSheetIndex).addMergedRegion(
                        new CellRangeAddress(range.leftTopRow, range.rightBottomRow, range.leftTopColumn,
                                range.rightBottomColumn));
            } else {
                setCellValue(currentPoint.row, currentPoint.column + (columns.size() + 1) / 2 - 1, sheet.getTitle());
                range = new Range(currentPoint.row, currentPoint.column + (columns.size() + 1) / 2 - 1,
                        currentPoint.row, currentPoint.column + (columns.size() + 1) / 2 - 1);
            }

            setRangeStyle(range, false, true, true);

            if (sheet.getExportCallBack() != null) {
                sheet.getExportCallBack().onDrawTitle(wb, range);
            }

            currentPoint.row++;
        }

        // 设置列标题
        if (hasColumnHead(columns)) {
            for (int i = 0; i < columns.size(); i++) {
                setCellValue(currentPoint.row, currentPoint.column + i, columns.get(i).getColumnHeadName());
            }
            range = new Range(currentPoint.row, currentPoint.column, currentPoint.row, currentPoint.column
                    + columns.size() - 1);

            setRangeStyle(range, true, true, true);

            if (sheet.getExportCallBack() != null) {
                sheet.getExportCallBack().onDrawColumnHead(wb, range);
            }

            currentPoint.row++;
        }
    }

    private void setRangeStyle(Range range, boolean setBorder, boolean setFont, boolean setAlign) {
        CellStyle style = wb.createCellStyle();

        if (setBorder) {
            style.setBorderBottom(CellStyle.BORDER_THIN);
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());
            style.setBorderTop(CellStyle.BORDER_THIN);
            style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        }

        if (setFont) {
            Font font = wb.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            style.setFont(font);
        }

        if (setAlign) {
            style.setAlignment(CellStyle.ALIGN_CENTER);
            style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        }

        Sheet excelSheet = wb.getSheetAt(selectedSheetIndex);
        for (int row = range.leftTopRow; row <= range.rightBottomRow; row++) {
            for (int column = range.leftTopColumn; column <= range.rightBottomColumn; column++) {
                excelSheet.getRow(row).getCell(column).setCellStyle(style);
            }
        }
    }

    /**
     * 是否有列标题
     * 
     * @param columns
     * @return
     */
    private boolean hasColumnHead(List<ExportColumn> columns) {
        for (ExportColumn column : columns) {
            if (StringUtils.isNotBlank(column.getColumnHeadName())) {
                return true;
            }
        }

        return false;
    }

    private void setValue(int row, int startColumn, Object[] values) throws Exception {
        for (int i = 0; i < values.length; i++) {
            setValue(row, startColumn + i, values[i]);
        }
    }

    /**
     * 给单元格设置值
     * 
     * @param row
     * @param column
     * @param value
     * @param clazz
     * @param cellValueOptions
     * @return
     * @throws Exception
     */
    private void setValue(int row, int column, Object value) throws Exception {
        if (value == null) {
            setCellValue(row, column, "");
            return;
        }

        if (value instanceof Number) {
            setCellValue(row, column, conversionService.convert(value, Double.class));
        } else if (value instanceof Boolean) {
            Boolean b = (Boolean) value;
            setCellValue(row, column, b ? "是" : "否");
        } else if (value instanceof Date) {
            setCellValue(row, column, (Date) value);
        } else {
            setCellValue(row, column, value.toString());
        }
    }

    protected void setCellValue(int row, int column, String text) {
        createCell(row, column).setCellValue(text);
    }

    protected void setCellValue(int row, int column, Double number) {
        createCell(row, column).setCellValue(number);
    }

    protected void setCellValue(int row, int column, Date date) {
        createCell(row, column).setCellValue(date);
    }

    private Cell createCell(int row, int column) {
        Row excelRow = wb.getSheetAt(selectedSheetIndex).getRow(row);
        if (excelRow == null) {
            excelRow = wb.getSheetAt(selectedSheetIndex).createRow(row);
        }

        Cell excelCell = excelRow.getCell(column);
        if (excelCell == null) {
            excelCell = excelRow.createCell(column);
        }
        return excelCell;
    }
}
