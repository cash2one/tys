package com.tys.excel.inject;

import java.util.List;
import java.util.Map;

public interface InjectNameBatchConverter<T> {

    public Map<T, String> getNames(List<T> ids, String[] params) throws Exception;
}