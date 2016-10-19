package com.tys.excel.exp;

import java.util.ArrayList;
import java.util.List;

/**
 * 生成Excel时候用到的Sheet页信息
 * 
 */
public class ExportSheet {

    /**
     * 数据表的标题
     */
    private String title;

    /**
     * sheet名
     */
    private String sheetName;

    /**
     * 数据表List
     */
    private List<?> dataList = new ArrayList<Object>();

    /**
     * 数据表数组
     */
    private List<Object[]> dataArray = new ArrayList<Object[]>();

    /**
     * 导出起始行和列
     */
    private ExportDrawPoint startPoint = new ExportDrawPoint();

    /**
     * 回调函数，自定义导出时的行为
     */
    private ExportCallBack exportCallBack = null;

    /**
     * 所有列
     */
    private List<ExportColumn> columns = new ArrayList<ExportColumn>();

    public String getSheetName() {
        return sheetName;
    }

    public ExportSheet setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ExportSheet setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<?> getDataList() {
        return dataList;
    }

    public ExportSheet setDataList(List<?> dataList) {
        this.dataList = dataList;
        return this;
    }

    public List<Object[]> getDataArray() {
        return dataArray;
    }

    public ExportSheet setDataArray(List<Object[]> dataArray) {
        this.dataArray = dataArray;
        return this;
    }

    public List<ExportColumn> getColumns() {
        return columns;
    }

    public ExportSheet addColumns(ExportColumn... columns) {
        for (ExportColumn column : columns) {
            this.columns.add(column);
        }
        return this;
    }

    public ExportSheet addColumn(String columnHeadName, String beanProperty) {
        this.columns.add(new ExportColumn(columnHeadName, beanProperty));
        return this;
    }

    public ExportSheet addColumn(String columnHeadName) {
        this.columns.add(new ExportColumn(columnHeadName));
        return this;
    }

    public ExportDrawPoint getStartPoint() {
        return startPoint;
    }

    public ExportSheet setStartPoint(int row, int column) {
        startPoint.row = row;
        startPoint.column = column;
        return this;
    }

    public ExportCallBack getExportCallBack() {
        return exportCallBack;
    }

    public ExportSheet setExportCallBack(ExportCallBack exportCallBack) {
        this.exportCallBack = exportCallBack;
        return this;
    }
}
