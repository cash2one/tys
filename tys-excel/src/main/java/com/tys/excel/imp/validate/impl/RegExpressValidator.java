package com.tys.excel.imp.validate.impl;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tys.excel.imp.validate.ExcelImportValidator;

/**
 * Excel导入校验器
 * 
 */
@Service("正则校验器")
@Scope("prototype")
public class RegExpressValidator extends ExcelImportValidator<Object> {

    @Override
    public void validate(Object dataLine, List<Object> readedLines, Map<String, Object> nextLineCellValues)
            throws Exception {
        for (int i = 0; i < beanPropertys.size(); i++) {
            Object value = PropertyUtils.getProperty(dataLine, beanPropertys.get(i));

            if (value != null) {
                String reg = params.get(i).split("[$]")[0].trim() + "$";
                String errorMsg = params.get(i).split("[$]")[1].trim();
                if (!Pattern.matches(reg, value.toString())) {
                    addError(columnHeaders.get(i) + "列" + errorMsg);
                }
            }
        }
    }
}
