package com.tys.excel.imp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 导入Excel时候需要继承的接口
 */
public interface ExcelImportService<T> {

    void importDb(List<T> datas,HttpServletRequest request);
}
