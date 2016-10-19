package com.tys.excel.convert;

/**
 * Excel导入、导出名称转换器
 * 
 */
public abstract class ExcelConverter<ID, NM> {

    protected String param;

    protected Object dataLine;

    public abstract ID getKey(NM cellValue) throws Exception;

    public abstract NM getName(ID propertyValue) throws Exception;

    public String[] getDropdownNames() throws Exception {
        return null;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public void setDataLine(Object dataLine) {
        this.dataLine = dataLine;
    }
}
