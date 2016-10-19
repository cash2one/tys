/*
 * @(#)DateConverter.java 2014-2-20
 * 
 * Copy Right@ 纽海信息技术有限公司
 */

package com.tys.excel.convert.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tys.excel.convert.ExcelConverter;

/**
 * Excel时间转换器
 * 
 */
@Service("时间")
@Scope("prototype")
public class DateConverter extends ExcelConverter<Date, String> {

    private static final String DATEFORMATE = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date getKey(String cellValue) throws Exception {
        if (StringUtils.isNotBlank(cellValue)) {
            String dateFormate = param == null ? DATEFORMATE : param;
            return DateUtils.parseDate(cellValue, dateFormate);
        }
        return null;
    }

    @Override
    public String getName(Date propertyValue) throws Exception {
        if (propertyValue != null) {
            String dateFormate = param == null ? DATEFORMATE : param;
            return DateFormatUtils.format(propertyValue, dateFormate);
        }
        return null;
    }
}
