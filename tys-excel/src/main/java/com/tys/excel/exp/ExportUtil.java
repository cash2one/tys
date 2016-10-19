package com.tys.excel.exp;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

import com.tys.excel.ExcelUtil;
import com.tys.excel.convert.ConverterModel;
import com.tys.excel.convert.ExcelConverter;

/**
 * 生成Excel时候用到工具类
 * 
 */
@Service
public class ExportUtil {

    public static final String EXCEL_EXPORT_WINDOW_SIZE = "excelExportWindowSize";

    public static final String EXCEL_EXPORT_PAGINATION_SIZE = "excelExportPaginationSize";

    @Autowired
    private ApplicationContext appContext;

    private ConversionService conversionService = new DefaultConversionService();

    @SuppressWarnings({ "rawtypes" })
    private ExportConfig readConfigFile(String filePath) throws InvalidFormatException, IOException {
        Workbook wb = WorkbookFactory.create(new File(filePath));

        Sheet sheet = wb.getSheet("导出");

        ExportConfig config = new ExportConfig();

        int propertyRow;

        int firstCellNum = 1;

        for (propertyRow = 1;; propertyRow++) {
            if ("属性".equals(ExcelUtil.getCellText(sheet.getRow(propertyRow).getCell(0)))) {
                break;
            }
        }

        int converterRow = propertyRow + 1, groupRow = converterRow + 1;

        // 读取列头、属性、转换器
        for (int column = firstCellNum;; column++) {
            if (ExcelUtil.getCellValue(sheet.getRow(propertyRow).getCell(column)) == null) {
                break;
            }

            ConverterModel converterModel = new ConverterModel();

            for (int columnHeaderStartRow = 0; columnHeaderStartRow < propertyRow; columnHeaderStartRow++) {
                Cell cell = sheet.getRow(columnHeaderStartRow).getCell(column);
                if (cell != null) {
                    config.getColumnHeaderCells().add(cell);
                }
            }

            config.setColumnHeaderRowsCount(propertyRow);

            // 列标题随便设置，导出时会根据配置来设置列标题
            converterModel.setColumnHeader("x");
            converterModel.setBeanProperty(ExcelUtil.getCellText(sheet.getRow(propertyRow).getCell(column)));

            // 读取转换器
            if (sheet.getRow(converterRow) != null
                    && "转换器".equals(ExcelUtil.getCellText(sheet.getRow(converterRow).getCell(firstCellNum - 1)))) {
                String converterBean = ExcelUtil.getCellText(sheet.getRow(converterRow).getCell(column));
                if (StringUtils.isNotBlank(converterBean)) {
                    ExcelConverter converter = null;
                    if (converterBean.indexOf("(") > 0 && converterBean.endsWith(")")) {
                        converter = appContext.getBean(StringUtils.substringBefore(converterBean, "("),
                                ExcelConverter.class);
                        converter.setParam(StringUtils.substringBetween(converterBean, "(", ")"));
                    } else {
                        converter = appContext.getBean(converterBean, ExcelConverter.class);
                    }

                    if (converter == null) {
                        throw new RuntimeException("找不到转换器:" + converterBean);
                    }

                    converterModel.setConverter(converter);
                }

            }

            // 读取分组
            if (sheet.getRow(groupRow) != null
                    && "分组".equals(ExcelUtil.getCellText(sheet.getRow(groupRow).getCell(firstCellNum - 1)))) {
                if (ExcelUtil.getCellValue(sheet.getRow(groupRow).getCell(column)) != null) {
                    config.getGroups().add(column - 1);
                }
            }

            config.getConverterModels().add(converterModel);

            config.getColumnWidths().add(sheet.getColumnWidth(column));
        }

        // 读取列标题的合并单元格
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress mergedRegion = sheet.getMergedRegion(i);
            if (mergedRegion.getFirstRow() == 0 && mergedRegion.getFirstColumn() == 0) {
                continue;
            }

            mergedRegion.setFirstColumn(mergedRegion.getFirstColumn() - 1);
            mergedRegion.setLastColumn(mergedRegion.getLastColumn() - 1);
            config.getMergeColumnHeader().add(mergedRegion);
        }

        return config;
    }

    @SuppressWarnings({ "rawtypes" })
    public void exportExcel(String exportFileName, List exportDatas, Map<String, String> params,
            ExportCallBack exportCallBack, HttpServletRequest request, HttpServletResponse response) {
        ExportConfig config = null;
        try {
            config = readConfigFile(request.getSession().getServletContext().getRealPath("/resources/excelConfig")
                    + "/" + exportFileName);

            ExportSheet exportSheet = getExportSheet(exportDatas, params, exportCallBack, config);

            response.reset();
            // response.setContentType("application/vnd.ms-excel");
            response.setContentType("application/vnd.openxmlformats");
            response.setCharacterEncoding("utf-8");

            if (exportFileName.toLowerCase().endsWith(".xls")) {
                exportFileName = exportFileName.substring(0, exportFileName.lastIndexOf(".")) + ".xlsx";
            }

            response.addHeader("Content-Disposition", "attachment; filename="
                    + new String(exportFileName.getBytes(), "ISO8859-1"));

            arrays2Excel(response.getOutputStream(), (Integer) request.getAttribute(EXCEL_EXPORT_WINDOW_SIZE),
                    exportSheet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ExportSheet getExportSheet(final List dataList, final Map<String, String> params,
            final ExportCallBack exportCallBack, final ExportConfig config) throws Exception {
        ExportSheet exportSheet = new ExportSheet();

        exportSheet.setStartPoint(config.getColumnHeaderRowsCount() - 1, 0);

        for (ConverterModel converterModel : config.getConverterModels()) {
            exportSheet.addColumn(converterModel.getColumnHeader(), converterModel.getBeanProperty());
        }

        final List<Object[]> convertDataList = new ArrayList<Object[]>();
        MultiKeyMap convertCache = new MultiKeyMap();

        // 转换
        for (Object dataLine : dataList) {
            Object[] objs = new Object[config.getConverterModels().size()];

            for (int i = 0; i < objs.length; i++) {
                ConverterModel converterModel = config.getConverterModels().get(i);
                Object propertyValue = PropertyUtils.getProperty(dataLine, converterModel.getBeanProperty());
                if (converterModel.getConverter() != null && propertyValue != null) {
                    if (convertCache.containsKey(converterModel.getBeanProperty(), propertyValue)) {
                        objs[i] = convertCache.get(converterModel.getBeanProperty(), propertyValue);
                    } else {
                        Class clazz = (Class) ((ParameterizedType) converterModel.getConverter().getClass()
                                .getGenericSuperclass()).getActualTypeArguments()[0];
                        objs[i] = converterModel.getConverter()
                                .getName(conversionService.convert(propertyValue, clazz));

                        convertCache.put(converterModel.getBeanProperty(), propertyValue, objs[i]);
                    }
                } else {
                    objs[i] = propertyValue;
                }
            }

            convertDataList.add(objs);
        }

        exportSheet.setDataArray(convertDataList);

        exportSheet.setSheetName("Sheet1").setExportCallBack(new ExportCallBack() {

            @Override
            public void onDrawTitle(Workbook workBook, Range drawedTitleRange) throws Exception {
                if (exportCallBack != null) {
                    exportCallBack.onDrawTitle(workBook, drawedTitleRange);
                }
            }

            @Override
            public void onDrawColumnHead(Workbook workBook, Range drawedColumnHeadRange) throws Exception {
                Sheet sheet = workBook.getSheetAt(0);
                // 设置列宽
                for (int column = 0; column < config.getColumnWidths().size(); column++) {
                    sheet.setColumnWidth(column, config.getColumnWidths().get(column));
                }

                // 根据导出配置来设置列标题
                for (Cell configCell : config.getColumnHeaderCells()) {
                    if (sheet.getRow(configCell.getRowIndex()) == null) {
                        sheet.createRow(configCell.getRowIndex());
                    }
                    Cell cell = sheet.getRow(configCell.getRowIndex()).createCell(configCell.getColumnIndex() - 1);
                    String cellText = ExcelUtil.getCellText(configCell);
                    if (cellText.startsWith("(") && cellText.endsWith(")")) {
                        cellText = params.get(StringUtils.substringBetween(cellText, "(", ")"));
                        if (cellText == null)
                            cellText = "";
                    }
                    cell.setCellValue(cellText);
                }

                drawColumnHeader(workBook, config);

                for (CellRangeAddress region : config.getMergeColumnHeader()) {
                    sheet.addMergedRegion(region);
                }

                if (exportCallBack != null) {
                    exportCallBack.onDrawColumnHead(workBook, drawedColumnHeadRange);
                }
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onDrawDataLine(Workbook workBook, Range drawedDataLineRange, int indexOfData) throws Exception {
                // 对数据进行分组
                if (indexOfData == dataList.size() - 1 && config.getGroups().size() > 0) {
                    Sheet sheet = workBook.getSheetAt(0);

                    ArrayList<Integer[]> dataRanges = new ArrayList<Integer[]>();
                    dataRanges.add(new Integer[] { config.getColumnHeaderRowsCount(),
                            convertDataList.size() - 1 + config.getColumnHeaderRowsCount() });

                    for (int group : config.getGroups()) {
                        ArrayList<Integer[]> dataRangesCopy = (ArrayList<Integer[]>) dataRanges.clone();
                        dataRanges.clear();
                        for (Integer[] dataRange : dataRangesCopy) {
                            String mergeStartValue = null;
                            int mergeStartRow = -1;

                            for (int row = dataRange[0]; row <= dataRange[1]; row++) {
                                if (!ObjectUtils.equals(
                                        convertDataList.get(row - config.getColumnHeaderRowsCount())[group],
                                        mergeStartValue)) {
                                    if (mergeStartValue != null) {
                                        sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, row - 1, group, group));

                                        dataRanges.add(new Integer[] { mergeStartRow, row - 1 });
                                    }

                                    mergeStartValue = convertDataList.get(row - config.getColumnHeaderRowsCount())[group] == null ? null
                                            : convertDataList.get(row - config.getColumnHeaderRowsCount())[group]
                                                    .toString();
                                    mergeStartRow = row;
                                }
                            }
                            sheet.addMergedRegion(new CellRangeAddress(mergeStartRow, dataRange[1], group, group));
                            dataRanges.add(new Integer[] { mergeStartRow, dataRange[1] });
                        }
                    }
                }

                if (exportCallBack != null) {
                    exportCallBack.onDrawDataLine(workBook, drawedDataLineRange, indexOfData);
                }
            }
        });

        return exportSheet;
    }

    private void drawColumnHeader(Workbook wb, ExportConfig config) {
        CellStyle style = wb.createCellStyle();

        // 设置边框
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());

        // 设置字体粗体
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        // 设置居中对齐
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        for (int row = 0; row < config.getColumnHeaderRowsCount(); row++) {
            for (int column = 0; column < config.getConverterModels().size(); column++) {
                wb.getSheetAt(wb.getActiveSheetIndex()).getRow(row).getCell(column).setCellStyle(style);
            }
        }
    }

    private void arrays2Excel(OutputStream os, Integer exportWindowSize, ExportSheet... sheets) throws Exception {
        SXSSFWorkbook rawWorkbook = new SXSSFWorkbook(exportWindowSize);

        new SXSSFExcelExport(rawWorkbook).arrays2Excel(sheets);

        rawWorkbook.write(os);
        os.close();

        rawWorkbook.dispose();
    }
}
