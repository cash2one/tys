package com.tys.excel.imp.validate.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tys.excel.imp.validate.ExcelImportValidator;
import com.tys.util.MCollectionUtils;

/**
 * Excel导入校验器
 */
@Service("多列不重复校验器")
@Scope("prototype")
public class MultipleNotRepeatValidator extends ExcelImportValidator<Object> {


    @Autowired
    private ApplicationContext appContext;

    @Override
    public void validate(Object dataLine, List<Object> readedLines, Map<String, Object> nextLineCellValues)
            throws Exception {
        boolean isRepeatInExcel = false;
        for (Object readLine : readedLines) {
            if (dataLine.equals(readLine)) {
                continue;
            }

            int repeatCount = 0;
            for (int i = 0; i < beanPropertys.size(); i++) {
                String beanProperty = beanPropertys.get(i);
                Object propertyValue = PropertyUtils.getProperty(dataLine, beanProperty);

                {
                    if (propertyValue != null && PropertyUtils.getProperty(readLine, beanProperty) != null
                            && propertyValue.equals(PropertyUtils.getProperty(readLine, beanProperty))) {
                        repeatCount++;
                    }
                }
            }

            if (repeatCount == beanPropertys.size()) {
                isRepeatInExcel = true;
                break;
            }
        }
        if (isRepeatInExcel) {
            addError(StringUtils.join(columnHeaders, "、") + "重复或者在系统中已存在");
        } else if (MCollectionUtils.isNotEmpty(params)) {
            String bean, methodName;
            if (params.get(0).contains(".")) {
                bean = params.get(0).split("[.]")[0];
                methodName = params.get(0).split("[.]")[1];
            } else {
                bean = defaultBean;
                methodName = params.get(0);
            }

            try {
                if ((Boolean) MethodUtils.invokeMethod(appContext.getBean(bean), methodName, dataLine)) {
                    addError(StringUtils.join(columnHeaders, "、") + "重复或者在系统中已存在");
                }
            } catch (Exception e) {
            	e.printStackTrace();
                // 如果底层方法未实现,这里会有异常,为不影响业务,暂不处理
            }
        }
    }
}
