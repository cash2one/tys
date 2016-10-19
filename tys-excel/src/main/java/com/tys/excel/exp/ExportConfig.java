package com.tys.excel.exp;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import com.tys.excel.convert.ConverterModel;

/**
 * 生成Excel时候用到的模板配置信息
 * 
 */
public class ExportConfig {

    private List<ConverterModel> converterModels = new ArrayList<ConverterModel>();

    private List<Integer> columnWidths = new ArrayList<Integer>();

    private List<CellRangeAddress> mergeColumnHeader = new ArrayList<CellRangeAddress>();

    private List<Cell> columnHeaderCells = new ArrayList<Cell>();

    private List<Integer> groups = new ArrayList<Integer>();

    private int columnHeaderRowsCount = 1;

    public List<Integer> getGroups() {
        return groups;
    }

    public void setGroups(List<Integer> groups) {
        this.groups = groups;
    }

    public int getColumnHeaderRowsCount() {
        return columnHeaderRowsCount;
    }

    public void setColumnHeaderRowsCount(int columnHeaderRowsCount) {
        this.columnHeaderRowsCount = columnHeaderRowsCount;
    }

    public List<CellRangeAddress> getMergeColumnHeader() {
        return mergeColumnHeader;
    }

    public void setMergeColumnHeader(List<CellRangeAddress> mergeColumnHeader) {
        this.mergeColumnHeader = mergeColumnHeader;
    }

    public List<Cell> getColumnHeaderCells() {
        return columnHeaderCells;
    }

    public void setColumnHeaderCells(List<Cell> columnHeaderCells) {
        this.columnHeaderCells = columnHeaderCells;
    }

    public List<ConverterModel> getConverterModels() {
        return converterModels;
    }

    public void setConverterModels(List<ConverterModel> converterModels) {
        this.converterModels = converterModels;
    }

    public List<Integer> getColumnWidths() {
        return columnWidths;
    }

    public void setColumnWidths(List<Integer> columnWidths) {
        this.columnWidths = columnWidths;
    }
}
