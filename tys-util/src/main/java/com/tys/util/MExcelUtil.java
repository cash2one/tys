package com.tys.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class MExcelUtil {
	/**
	 * 合并单元格处理,获取合并行
	 * 
	 * @param sheet
	 * @return List<CellRangeAddress>
	 */
	public static List<CellRangeAddress> getCombineCell(Sheet sheet) {
		List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		// 获得一个 sheet 中合并单元格的数量
		int sheetmergerCount = sheet.getNumMergedRegions();
		// 遍历合并单元格
		for (int i = 0; i < sheetmergerCount; i++) {
			// 获得合并单元格加入list中
			CellRangeAddress ca = sheet.getMergedRegion(i);
			list.add(ca);
		}
		return list;
	}

	/**
	 * 判断单元格是否为合并单元格，是的话则将单元格的值返回,不是返回本单元格值
	 * 
	 * @param listCombineCell
	 *            存放合并单元格的list
	 * @param cell
	 *            需要判断的单元格
	 * @param sheet
	 *            sheet
	 * @return
	 */
	public static String getCellVal(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet) throws Exception {
		int firstC = 0;
		int lastC = 0;
		int firstR = 0;
		int lastR = 0;
		String cellValue = null;
		for (CellRangeAddress ca : listCombineCell) {
			// 获得合并单元格的起始行, 结束行, 起始列, 结束列
			firstC = ca.getFirstColumn();
			lastC = ca.getLastColumn();
			firstR = ca.getFirstRow();
			lastR = ca.getLastRow();
			if (cell.getRowIndex() >= firstR && cell.getRowIndex() <= lastR) {
				if (cell.getColumnIndex() >= firstC && cell.getColumnIndex() <= lastC) {
					Row fRow = sheet.getRow(firstR);
					Cell fCell = fRow.getCell(firstC);
					if (fCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						cellValue = fCell.getNumericCellValue() + "";

					} else if (fCell.getCellType() == Cell.CELL_TYPE_STRING) {
						cellValue = fCell.getStringCellValue();
					}
					break;
				}
			} else {
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					cellValue = cell.getNumericCellValue() + "";

				} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					cellValue = cell.getStringCellValue();
				}
			}
		}
		return cellValue;
	}

	/**
	 * 获取excel表中某字段值
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellString(Cell cell) {
		if (null == cell) {
			return new String();
		}
		int cellType = cell.getCellType();
		if (Cell.CELL_TYPE_STRING == cellType) {
			return cell.getStringCellValue();
		}
		if (Cell.CELL_TYPE_BOOLEAN == cellType) {
			return Boolean.toString(cell.getBooleanCellValue());
		}
		if (Cell.CELL_TYPE_NUMERIC == cellType) {
			String doubleValue = String.valueOf(cell.getNumericCellValue());
			if (0 <= doubleValue.indexOf("E")) {
				try {
					Double d = Double.valueOf(doubleValue);
					return String.valueOf(d.longValue());
				} catch (Exception e) {
					return doubleValue;
				}
			}
			if (doubleValue.endsWith(".0")) {
				return doubleValue.substring(0, doubleValue.length() - 2);
			}
			return doubleValue;
		}
		if (Cell.CELL_TYPE_FORMULA == cellType) {
			return cell.getCellFormula();
		}
		if (Cell.CELL_TYPE_BLANK == cellType) {
			return new String();
		}
		return new String();
	}

}
