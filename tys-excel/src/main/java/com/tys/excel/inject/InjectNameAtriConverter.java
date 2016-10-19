package com.tys.excel.inject;

public interface InjectNameAtriConverter <T> {

    public String getName(T object, String[] params) throws Exception;

}
