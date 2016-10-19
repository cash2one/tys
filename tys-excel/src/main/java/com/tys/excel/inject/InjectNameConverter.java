package com.tys.excel.inject;

public interface InjectNameConverter<T> {

    public String getName(T id, String[] params) throws Exception;
}