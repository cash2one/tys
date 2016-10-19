package com.tys.excel.imp;

import java.util.ArrayList;
import java.util.List;

import com.tys.excel.convert.ConverterModel;
import com.tys.excel.imp.validate.ExcelImportValidator;

/**
 * 导入Excel时候模板的配置信息
 * 
 */
@SuppressWarnings("rawtypes")
public class ImportConfig {

    private List<ConverterModel> converterModels = new ArrayList<ConverterModel>();

    private List<ExcelImportValidator> validators = new ArrayList<ExcelImportValidator>();

    private List<Integer> columnWidths = new ArrayList<Integer>();

    private String sheetName;

    private Integer sheetIndex;

    private List<Object> example = new ArrayList<Object>();

    public List<Object> getExample() {
        return example;
    }

    public void setExample(List<Object> example) {
        this.example = example;
    }

    public Integer getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(Integer sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<ConverterModel> getConverterModels() {
        return converterModels;
    }

    public void setConverterModels(List<ConverterModel> converterModels) {
        this.converterModels = converterModels;
    }

    public List<ExcelImportValidator> getValidators() {
        return validators;
    }

    public void setValidators(List<ExcelImportValidator> validators) {
        this.validators = validators;
    }

    public List<Integer> getColumnWidths() {
        return columnWidths;
    }

    public void setColumnWidths(List<Integer> columnWidths) {
        this.columnWidths = columnWidths;
    }
}
