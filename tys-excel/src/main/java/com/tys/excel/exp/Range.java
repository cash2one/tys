package com.tys.excel.exp;

/**
 * 生成Excel时候用到的单元格合并信息
 * 
 */
public class Range {

    public int leftTopRow = 0, leftTopColumn = 0, rightBottomRow = 0, rightBottomColumn = 0;

    public Range(int leftTopRow, int leftTopColumn, int rightBottomRow, int rightBottomColumn) {
        super();
        this.leftTopRow = leftTopRow;
        this.leftTopColumn = leftTopColumn;
        this.rightBottomRow = rightBottomRow;
        this.rightBottomColumn = rightBottomColumn;
    }

    @Override
    public String toString() {
        return leftTopRow + ":" + leftTopColumn + "," + rightBottomRow + ":" + rightBottomColumn;
    }
}
