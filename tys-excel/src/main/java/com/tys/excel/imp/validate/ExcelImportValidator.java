package com.tys.excel.imp.validate;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 导入Excel时候模板的输入值校验基类
 * 
 */
public abstract class ExcelImportValidator<T> {

    protected List<String> columnHeaders = new ArrayList<String>();

    protected List<String> beanPropertys = new ArrayList<String>();

    protected List<String> params = new ArrayList<String>();

    private Set<String> errorMsgs = new LinkedHashSet<String>();

    protected String defaultBean;

    public abstract void validate(T dataLine, List<T> readedLines, Map<String, Object> nextLineCellValues)
            throws Exception;

    public void addError(String error) {
        errorMsgs.add(error);
    }

    public void clearErrors() {
        errorMsgs.clear();
    }

    public boolean isContinueValidate() {
        return true;
    }

    public void setDefaultBean(String defaultBean) {
        this.defaultBean = defaultBean;
    }

    public List<String> getColumnHeaders() {
        return columnHeaders;
    }

    public List<String> getBeanPropertys() {
        return beanPropertys;
    }

    public List<String> getParams() {
        return params;
    }

    public void setColumnHeaders(List<String> columnHeaders) {
        this.columnHeaders = columnHeaders;
    }

    public void setBeanPropertys(List<String> beanPropertys) {
        this.beanPropertys = beanPropertys;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public Set<String> getErrorMsgs() {
        return errorMsgs;
    }
}
