package com.tys.excel.imp.validate.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tys.excel.imp.validate.ExcelImportValidator;

/**
 * Excel导入校验器
 * 
 */
@Service("非空校验器")
@Scope("prototype")
public class NotNullValidator extends ExcelImportValidator<Object> {

    @Override
    public void validate(Object dataLine, List<Object> readedLines, Map<String, Object> nextLineCellValues)
            throws Exception {
        for (int i = 0; i < beanPropertys.size(); i++) {
            if (PropertyUtils.getProperty(dataLine, beanPropertys.get(i)) == null) {
                addError(columnHeaders.get(i) + "不能为空");
            }
        }
    }

    @Override
    public boolean isContinueValidate() {
        return false;
    }
}
