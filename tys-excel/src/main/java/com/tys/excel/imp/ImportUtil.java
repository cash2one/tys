package com.tys.excel.imp;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.stereotype.Service;

import com.tys.excel.ExcelUtil;
import com.tys.excel.convert.ConverterModel;
import com.tys.excel.convert.ExcelConverter;
import com.tys.excel.exception.BusinessException;
import com.tys.excel.exp.ExportCallBack;
import com.tys.excel.exp.ExportSheet;
import com.tys.excel.exp.Range;
import com.tys.excel.imp.validate.ExcelImportValidator;
import com.tys.util.MCollectionUtils;

/**
 * 导入Excel时候用到的工具类
 */
@SuppressWarnings("deprecation")
@Service
public class ImportUtil {

    public static final String EXCEL_IMPORT_MAX_ROW = "excelImportMaxRow";

    @Autowired
    private ApplicationContext appContext;

    private ConversionService conversionService = new DefaultConversionService();

    private ImportConfig readConfigFile(String filePath, String fileSheetName, String[] serviceBeanArr)
            throws Exception {
        Workbook wb = WorkbookFactory.create(new File(filePath));

        for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {

            Sheet sheet = wb.getSheetAt(sheetIndex);

            if (sheet.getSheetName().equals("导入") || sheet.getSheetName().equals("导入" + fileSheetName)) {
                return readFromSheet(sheet, sheetIndex, serviceBeanArr[sheetIndex]);
            }
        }

        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ImportConfig readFromSheet(Sheet sheet, Integer sheetIndex, String defaultBean) throws Exception {
        ImportConfig config = new ImportConfig();

        config.setSheetIndex(sheetIndex);
        config.setSheetName(sheet.getSheetName());

        int columnHeaderRow = 0, propertyRow = columnHeaderRow + 1, converterRow = propertyRow + 1, validatorRow = -1;

        int firstCellNum = 1;
        int lastCellNum = 1;

        // 读取列头、属性、转换器
        for (int column = firstCellNum;; column++) {
            if (ExcelUtil.getCellValue(sheet.getRow(columnHeaderRow).getCell(column)) == null) {
                lastCellNum = column - 1;
                break;
            }

            ConverterModel converterModel = new ConverterModel();

            converterModel.setColumnHeader(ExcelUtil.getCellText(sheet.getRow(columnHeaderRow).getCell(column)));
            converterModel.setBeanProperty(ExcelUtil.getCellText(sheet.getRow(propertyRow).getCell(column)));

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

                validatorRow = converterRow + 1;
            } else {
                validatorRow = propertyRow + 1;
            }

            config.getConverterModels().add(converterModel);

            config.getColumnWidths().add(sheet.getColumnWidth(column));
        }

        // 读取校验器
        for (int row = validatorRow;; row++) {
            String validatorBean;
            if (sheet.getRow(row) == null
                    || StringUtils.isBlank(validatorBean = ExcelUtil.getCellText(sheet.getRow(row).getCell(
                            firstCellNum - 1)))) {
                break;
            }

            if ("示例".equals(ExcelUtil.getCellText(sheet.getRow(row).getCell(firstCellNum - 1)))) {
                for (int column = firstCellNum; column <= lastCellNum; column++) {
                    config.getExample().add(ExcelUtil.getCellText(sheet.getRow(row).getCell(column)));
                }
                break;
            }

            ExcelImportValidator validator = null;
            Object validObj = appContext.getBean(validatorBean);

            if (AopUtils.isAopProxy(validObj)) {
                validObj = ((Advised) validObj).getTargetSource().getTarget();
            }

            if (validObj instanceof ExcelImportValidator) {
                validator = (ExcelImportValidator) validObj;
            }

            if (validator == null) {
                throw new RuntimeException("找不到校验器:" + validatorBean);
            }

            validator.setDefaultBean(defaultBean);

            // 读取校验器的参数
            for (int column = firstCellNum; column <= lastCellNum; column++) {
                String validateParam = ExcelUtil.getCellText(sheet.getRow(row).getCell(column));
                if (StringUtils.isNotBlank(validateParam)) {
                    validator.getColumnHeaders().add(
                            ExcelUtil.getCellText(sheet.getRow(columnHeaderRow).getCell(column)));
                    validator.getParams().add(validateParam);
                    validator.getBeanPropertys().add(ExcelUtil.getCellText(sheet.getRow(propertyRow).getCell(column)));

                }
            }

            config.getValidators().add(validator);
        }

        Object validObj = appContext.getBean(defaultBean);

        if (AopUtils.isAopProxy(validObj)) {
            validObj = ((Advised) validObj).getTargetSource().getTarget();
        }

        if (validObj instanceof ExcelImportValidator) {
            config.getValidators().add((ExcelImportValidator) validObj);
        }

        return config;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> int importExcel(HttpServletRequest request, Workbook wb, String configFile, String[] serviceBeanArr,
            int sheetIndex) throws Exception {
        try {
            Sheet sheet = wb.getSheetAt(sheetIndex);

            Integer importRowSize = 3000;//(Integer) request.getAttribute(EXCEL_IMPORT_MAX_ROW);
            if (sheet.getPhysicalNumberOfRows() > importRowSize.intValue()) {
                throw new BusinessException("Excel内容超过最大行数" + importRowSize + "行");
            }

            ImportConfig config = readConfigFile(
                    request.getSession().getServletContext().getRealPath("/resources/excelConfig") + "/" + configFile,
                    wb.getSheetAt(sheetIndex).getSheetName(), serviceBeanArr);

            List<T> allDatas = new ArrayList<T>();

            boolean importSuccess = true;

            int startDataRow = 1, startDataColumn = 0;

            ExcelImportService<T> excelImportService = (ExcelImportService) appContext.getBean(serviceBeanArr[config
                    .getSheetIndex()]);
            MultiKeyMap convertCache = new MultiKeyMap();

            for (int row = startDataRow;; row++) {
                if (ExcelUtil.isEmptyLine(sheet, row, startDataColumn, config.getConverterModels().size())) {
                    break;
                }

                // 创建bean对象
                Class<T> clazz;

                if (AopUtils.isAopProxy(excelImportService)) {
                    clazz = getBeanClass(((Advised) excelImportService).getTargetSource().getTargetClass());
                } else {
                    clazz = getBeanClass(excelImportService.getClass());
                }

                T dataLine = ConstructorUtils.invokeConstructor(clazz);

                StringBuilder convertErrors = new StringBuilder();
                for (int column = startDataColumn; column < startDataColumn + config.getConverterModels().size(); column++) {
                    Object cellValue = ExcelUtil.getCellValue(sheet.getRow(row).getCell(column));
                    ConverterModel converterModel = config.getConverterModels().get(column - startDataColumn);
                    String beanProperty = converterModel.getBeanProperty();

                    // 转换
                    Class beanPropertyType = PropertyUtils.getPropertyType(dataLine, beanProperty);
                    if (cellValue != null) {
                        if (converterModel.getConverter() != null) {
                            converterModel.getConverter().setDataLine(dataLine);

                            Type genericInterface = converterModel.getConverter().getClass().getGenericSuperclass();

                            Object key = null;
                            if (convertCache.containsKey(beanProperty, cellValue)) {
                                key = convertCache.get(beanProperty, cellValue);
                            } else {
                                key = converterModel.getConverter().getKey(
                                        conversionService.convert(cellValue,
                                                (Class<?>) ((ParameterizedType) genericInterface)
                                                        .getActualTypeArguments()[1]));

                                convertCache.put(beanProperty, cellValue, key);
                            }

                            // 转换失败
                            if (cellValue != null && key == null) {
                                convertErrors.append("找不到" + converterModel.getColumnHeader() + ":" + cellValue + "。");
                                continue;
                            }

                            try {
                                PropertyUtils.setProperty(dataLine, beanProperty,
                                        conversionService.convert(key, beanPropertyType));
                            } catch (Exception e) {
                                convertErrors.append(converterModel.getColumnHeader() + "列读取数据失败。");
                                continue;
                            }
                        } else {
                            if ((beanPropertyType.isAssignableFrom(Byte.class)
                                    || beanPropertyType.isAssignableFrom(Short.class)
                                    || beanPropertyType.isAssignableFrom(Integer.class) || beanPropertyType
                                        .isAssignableFrom(Long.class)) && !(cellValue instanceof Long)) {
                                convertErrors.append(converterModel.getColumnHeader() + "应该为整数。");
                                continue;
                            }

                            if ((beanPropertyType.isAssignableFrom(Float.class)
                                    || beanPropertyType.isAssignableFrom(Double.class) || beanPropertyType
                                        .isAssignableFrom(BigDecimal.class))
                                    && !(cellValue instanceof Long)
                                    && !(cellValue instanceof Double)) {
                                convertErrors.append(converterModel.getColumnHeader() + "应该为小数。");
                                continue;
                            }

                            if (beanPropertyType.isAssignableFrom(Date.class) && !(cellValue instanceof Date)) {
                                convertErrors.append(converterModel.getColumnHeader() + "应该为日期。");
                                continue;
                            }

                            try {
                                PropertyUtils.setProperty(dataLine, beanProperty,
                                        conversionService.convert(cellValue, beanPropertyType));
                            } catch (Exception e) {
                                convertErrors.append(converterModel.getColumnHeader() + "列读取数据失败");
                            }
                        }
                    }
                }

                if (convertErrors.length() > 0) {
                    importSuccess = false;

                    createErrorCell(sheet, row, startDataColumn + config.getConverterModels().size(),
                            convertErrors.toString());
                } else {
                    // 验证
                    allDatas.add(dataLine);

                    Map<String, Object> nextLineCellValues = new HashMap<String, Object>();
                    if (!ExcelUtil.isEmptyLine(sheet, row + 1, startDataColumn, config.getConverterModels().size())) {
                        for (int column = startDataColumn; column < startDataColumn
                                + config.getConverterModels().size(); column++) {
                            Object cellValue = ExcelUtil.getCellValue(sheet.getRow(row + 1).getCell(column));
                            ConverterModel converterModel = config.getConverterModels().get(column - startDataColumn);
                            nextLineCellValues.put(converterModel.getBeanProperty(), cellValue);
                        }
                    }

                    StringBuilder validateErrors = new StringBuilder();

                    for (ExcelImportValidator validator : config.getValidators()) {
                        validator.validate(dataLine, allDatas, nextLineCellValues);
                        if (MCollectionUtils.isNotEmpty(validator.getErrorMsgs())) {
                            if (validator.getErrorMsgs().size() == 1) {
                                validateErrors.append(validator.getErrorMsgs().iterator().next() + "。");
                            } else {
                                validateErrors.append(StringUtils.join(validator.getErrorMsgs(), "。"));
                            }

                            validator.clearErrors();

                            if (!validator.isContinueValidate()) {
                                break;
                            }
                        }
                    }

                    if (validateErrors.length() > 0) {
                        importSuccess = false;

                        createErrorCell(sheet, row, startDataColumn + config.getConverterModels().size(),
                                validateErrors.toString());
                    }
                }
            }

            if (importSuccess) {
                excelImportService.importDb(allDatas,request);

                return 0;
            } else {
                return 1;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private <T> Class<T> getBeanClass(Class c) {
        Type[] genericInterfaces = c.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                return (Class<T>) ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
            }
        }

        return null;
    }

    public ExportSheet getTemplateExcel(HttpServletRequest request, String configFile, String serviceBean,
            final int sheetIndex) {
        try {
            final ImportConfig config = readFromSheet(
                    WorkbookFactory.create(
                            new File(request.getSession().getServletContext().getRealPath("/resources/excelConfig")
                                    + "/" + configFile)).getSheetAt(sheetIndex), sheetIndex, serviceBean);

            ExportSheet sheet = new ExportSheet();

            for (ConverterModel converterModel : config.getConverterModels()) {
                sheet.addColumn(converterModel.getColumnHeader(), null);
            }

            sheet.setSheetName(config.getSheetName().equals("导入") ? "Sheet1" : config.getSheetName().substring(2))
                    .setExportCallBack(new ExportCallBack() {

                        @Override
                        public void onDrawTitle(Workbook workBook, Range drawedTitleRange) throws Exception {
                        }

                        @Override
                        public void onDrawColumnHead(Workbook workBook, Range drawedColumnHeadRange) throws Exception {
                            workBook.getSheetAt(sheetIndex).createRow(1);

                            // 设置列宽
                            for (int column = 0; column < config.getColumnWidths().size(); column++) {
                                workBook.getSheetAt(sheetIndex).setColumnWidth(column,
                                        config.getColumnWidths().get(column));
                            }

                            for (int column = 0; column < config.getConverterModels().size(); column++) {
                                ConverterModel converterModel = config.getConverterModels().get(column);
                                if (converterModel.getConverter() == null) {
                                    continue;
                                }

                                String[] dropdownNames = converterModel.getConverter().getDropdownNames();
                                if (dropdownNames != null) {
                                    if (workBook instanceof HSSFWorkbook) {
                                        CellRangeAddressList addressList = new CellRangeAddressList(1, 1, column,
                                                column);
                                        DVConstraint dvConstraint = DVConstraint
                                                .createExplicitListConstraint(dropdownNames);
                                        DataValidation dataValidation = new HSSFDataValidation(addressList,
                                                dvConstraint);
                                        dataValidation.setSuppressDropDownArrow(false);
                                        workBook.getSheetAt(sheetIndex).addValidationData(dataValidation);
                                    } else {
                                        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(
                                                (XSSFSheet) workBook.getSheetAt(sheetIndex));
                                        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                                                .createExplicitListConstraint(dropdownNames);
                                        CellRangeAddressList addressList = new CellRangeAddressList(1, 1, column,
                                                column);
                                        XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(
                                                dvConstraint, addressList);
                                        validation.setShowErrorBox(true);
                                        workBook.getSheetAt(sheetIndex).addValidationData(validation);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onDrawDataLine(Workbook workBook, Range range, int indexOfData) throws Exception {
                            Font font = workBook.createFont();
                            font.setColor(HSSFColor.RED.index);
                            Sheet excelSheet = workBook.getSheetAt(sheetIndex);

                            for (int row = range.leftTopRow; row <= range.rightBottomRow; row++) {
                                for (int column = range.leftTopColumn; column <= range.rightBottomColumn; column++) {
                                    excelSheet.getRow(row).getCell(column).getCellStyle().setFont(font);
                                }
                            }
                        }
                    });

            if (MCollectionUtils.isNotEmpty(config.getExample())) {
                sheet.getDataArray().add(config.getExample().toArray(new Object[config.getExample().size()]));
            }

            return sheet;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createErrorCell(Sheet sheet, int row, int column, String errorMsg) {
        Cell errorCell = sheet.getRow(row).createCell(column);
        CellStyle style = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setColor(IndexedColors.RED.index);
        style.setFont(font);
        errorCell.setCellStyle(style);
        errorCell.setCellValue(errorMsg);
    }
}