package com.tys.excel.convert;

/**
 * Excel导入、导出名称转换器模板用实体类
 */
@SuppressWarnings("rawtypes")
public class ConverterModel {

    private String columnHeader;

    private String beanProperty;

    private ExcelConverter converter;

    public String getColumnHeader() {
        return columnHeader;
    }

    public void setColumnHeader(String columnHeader) {
        this.columnHeader = columnHeader;
    }

    public String getBeanProperty() {
        return beanProperty;
    }

    public void setBeanProperty(String beanProperty) {
        this.beanProperty = beanProperty;
    }

    public ExcelConverter getConverter() {
        return converter;
    }

    public void setConverter(ExcelConverter converter) {
        this.converter = converter;
    }
}
