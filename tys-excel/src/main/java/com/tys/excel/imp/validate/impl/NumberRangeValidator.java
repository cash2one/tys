package com.tys.excel.imp.validate.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

import com.tys.excel.imp.validate.ExcelImportValidator;

/**
 * Excel导入校验器
 * 
 */
@Service("数字范围校验器")
@Scope("prototype")
public class NumberRangeValidator extends ExcelImportValidator<Object> {

    @Override
    public void validate(Object dataLine, List<Object> readedLines, Map<String, Object> nextLineCellValues)
            throws Exception {
        for (int i = 0; i < beanPropertys.size(); i++) {
            Object value = PropertyUtils.getProperty(dataLine, beanPropertys.get(i));

            if (value != null
                    && !new SpelExpressionParser().parseExpression(params.get(i).replace("?", value.toString()))
                            .getValue(Boolean.class)) {
                addError(columnHeaders.get(i)
                        + "应该"
                        + params.get(i).replace("?", "").replace(">", "大于").replace("=", "等于").replace("<", "小于")
                                .replace(" and ", "并且").replace(" or ", "或者"));
            }
        }
    }

    @Override
    public boolean isContinueValidate() {
        return false;
    }
}
