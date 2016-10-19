package com.tys.excel.exp;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * 生成Excel时、各节点的回调函数
 * 
 */
public interface ExportCallBack {

    /**
     * 画完标题后的事件
     * 
     * @param workBook
     *            ，ExcelWorkBook对象
     * @param drawedTitleRange
     *            ，画过的区域
     * @param currentDrawingPoint
     *            ，当前Draw位置
     */
    public void onDrawTitle(Workbook workBook, Range drawedTitleRange) throws Exception;

    /**
     * 画完列头后的事件
     * 
     * @param workBook
     *            ，ExcelWorkBook对象
     * @param drawedColumnHeadRange
     *            ，画过的区域
     * @param currentDrawingPoint
     *            ，当前Draw位置
     */
    public void onDrawColumnHead(Workbook workBook, Range drawedColumnHeadRange) throws Exception;

    /**
     * 画完每个数据行后的事件
     * 
     * @param workBook
     *            ，ExcelWorkBook对象
     * @param drawedDataLineRange
     *            ，画过的一行区域
     * @param indexOfData
     *            ，当前数据在List中的索引
     * @param currentDrawingPoint
     *            ，当前Draw位置
     */
    public void onDrawDataLine(Workbook workBook, Range drawedDataLineRange, int indexOfData) throws Exception;
}
