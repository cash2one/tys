package com.tys.excel.exp;

/**
 * 生成Excel时候用到的列对象
 */
public class ExportColumn {

    private String columnHeadName;

    private String beanProperty;

    public ExportColumn() {
        this(null, null);
    }

    public ExportColumn(String columnHeadName) {
        this(columnHeadName, null);
    }

    public ExportColumn(String columnHeadName, String beanProperty) {
        super();

        this.columnHeadName = columnHeadName;
        this.beanProperty = beanProperty;
    }

    public String getBeanProperty() {
        return beanProperty;
    }

    public void setBeanProperty(String beanProperty) {
        this.beanProperty = beanProperty;
    }

    public String getColumnHeadName() {
        return columnHeadName;
    }

    public void setColumnHeadName(String columnHeadName) {
        this.columnHeadName = columnHeadName;
    }

}
