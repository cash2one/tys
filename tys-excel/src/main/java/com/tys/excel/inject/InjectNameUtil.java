package com.tys.excel.inject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang3.StringUtils;

import com.tys.excel.core.ApplicationContextHolder;
import com.tys.util.MCollectionUtils;

public class InjectNameUtil {

    private static final String INJECT_BATCH_INJECT_INFO = ",injectBatchInjectInfo";

    public static List<Map<String, Object>> inject(List<?> list) {
        return inject(list, null, null);
    }

    public static List<Map<String, Object>> injectInclude(List<?> list, String... includes) {
        return inject(list, includes, null);
    }

    public static List<Map<String, Object>> injectExclude(List<?> list, String... excludes) {
        return inject(list, null, excludes);
    }

    public static Map<String, Object> inject(Object obj) {
        List<Map<String, Object>> list = inject(MCollectionUtils.newArrayList(obj));

        return MCollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

    public static Map<String, Object> injectInclude(Object obj, String... includes) {
        List<Map<String, Object>> list = injectInclude(MCollectionUtils.newArrayList(obj), includes);

        return MCollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

    public static Map<String, Object> injectExclude(Object obj, String... excludes) {
        List<Map<String, Object>> list = injectExclude(MCollectionUtils.newArrayList(obj), excludes);

        return MCollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

    @SuppressWarnings({"unchecked"})
    private static List<Map<String, Object>> inject(List<?> list, String[] includes, String[] excludes) {
        try {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

            if (MCollectionUtils.isEmpty(list)) {
                return result;
            }

            Map<String, BatchInjectInfo> batchInjectInfos = new HashMap<String, BatchInjectInfo>();
            MultiKeyMap cache = new MultiKeyMap();

            for (Object obj : list) {
                Map<String, Object> injectedMap = doInject(obj, cache, MCollectionUtils.newArrayList(includes),
                		MCollectionUtils.newArrayList(excludes));

                if (obj != null) {
                    Field[] fields = obj.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if (injectedMap.containsKey(field.getName() + INJECT_BATCH_INJECT_INFO)) {
                            field.setAccessible(true);

                            if (batchInjectInfos.containsKey(field.getName())) {
                                if (!batchInjectInfos.get(field.getName()).ids.contains(field.get(obj))) {
                                    batchInjectInfos.get(field.getName()).ids.add(field.get(obj));
                                }
                            } else {
                                batchInjectInfos.put(field.getName(),
                                        (BatchInjectInfo) injectedMap.get(field.getName() + INJECT_BATCH_INJECT_INFO));
                            }

                            injectedMap.remove(field.getName() + INJECT_BATCH_INJECT_INFO);
                        }
                    }

                    Method[] methods = obj.getClass().getDeclaredMethods();
                    for (Method method : methods) {
                        String methodNameNoGet = StringUtils.uncapitalize(StringUtils.removeStart(method.getName(),
                                "get"));

                        if (injectedMap.containsKey(methodNameNoGet + INJECT_BATCH_INJECT_INFO)) {

                            if (batchInjectInfos.containsKey(methodNameNoGet)) {
                                if (!batchInjectInfos.get(methodNameNoGet).ids.contains(method.invoke(obj))) {
                                    batchInjectInfos.get(methodNameNoGet).ids.add(method.invoke(obj));
                                }
                            } else {
                                batchInjectInfos.put(methodNameNoGet,
                                        (BatchInjectInfo) injectedMap.get(methodNameNoGet + INJECT_BATCH_INJECT_INFO));
                            }

                            injectedMap.remove(methodNameNoGet + INJECT_BATCH_INJECT_INFO);
                        }
                    }
                }

                result.add(injectedMap);
            }

            if (batchInjectInfos.size() > 0) {
                for (Map.Entry<String, BatchInjectInfo> entry : batchInjectInfos.entrySet()) {
                    entry.getValue().values = entry.getValue().converter.getNames(entry.getValue().ids,
                            entry.getValue().params);
                }

                for (Map<String, Object> obj : result) {
                    for (Map.Entry<String, Object> entry : obj.entrySet()) {
                        if (batchInjectInfos.containsKey(entry.getKey())) {
                            try {
                                obj.put(batchInjectInfos.get(entry.getKey()).nameField,
                                        batchInjectInfos.get(entry.getKey()).values.get(entry.getValue()));
                            } catch (Exception exp) {
                                obj.put(batchInjectInfos.get(entry.getKey()).nameField, "");
                            }
                        }
                    }
                }
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static Map<String, Object> doInject(Object obj, MultiKeyMap cache, List<String> includes,
                                                List<String> excludes) {
        if (obj == null) {
            return null;
        }

        try {
            Map<String, Object> map = PropertyUtils.describe(obj);
            map.remove("class");

            Field[] fields = obj.getClass().getDeclaredFields();

            for (Field field : fields) {
                InjectName injectName = field.getAnnotation(InjectName.class);
                if (injectName != null) {
                    field.setAccessible(true);

                    Object fieldValue = field.get(obj);

                    if ((MCollectionUtils.isEmpty(includes) && MCollectionUtils.isEmpty(excludes))
                            || (MCollectionUtils.isNotEmpty(includes) && includes.contains(field.getName()))
                            || (MCollectionUtils.isNotEmpty(excludes) && !excludes.contains(field.getName()))) {
                        String name = null;
                        String nameField;

                        if (StringUtils.isNotBlank(injectName.name())) {
                            nameField = injectName.name();
                        } else {
                            nameField = StringUtils.removeEndIgnoreCase(field.getName(), "Id") + "Name";
                        }

                        if (fieldValue != null) {
                            Object converter = ApplicationContextHolder.getBean(injectName.converter());
                            if (converter instanceof InjectNameBatchConverter) {
                                BatchInjectInfo batchInjectInfo = new BatchInjectInfo();

                                batchInjectInfo.ids.add(fieldValue);
                                batchInjectInfo.converter = (InjectNameBatchConverter) converter;
                                batchInjectInfo.params = injectName.param();
                                batchInjectInfo.nameField = nameField;

                                map.put(field.getName() + INJECT_BATCH_INJECT_INFO, batchInjectInfo);
                            } else if (converter instanceof InjectNameAtriConverter) {
                                if (cache.containsKey(field.getName(), fieldValue)) {
                                    name = (String) cache.get(field.getName(), fieldValue);
                                } else {
                                    name = ((InjectNameAtriConverter) converter).getName(obj, injectName.param());
                                    cache.put(field.getName(), fieldValue, name != null ? name : "");
                                }
                            } else {
                                if (cache.containsKey(field.getName(), fieldValue)) {
                                    name = (String) cache.get(field.getName(), fieldValue);
                                } else {
                                    name = ((InjectNameConverter) converter).getName(fieldValue, injectName.param());
                                    cache.put(field.getName(), fieldValue, name != null ? name : "");
                                }
                            }
                        }

                        map.put(nameField, name != null ? name : "");
                    }
                }
            }

            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                InjectName injectName = method.getAnnotation(InjectName.class);
                if (injectName != null) {
                    Object methodValue = method.invoke(obj);
                    String methodNameNoGet = StringUtils.uncapitalize(StringUtils.removeStart(method.getName(), "get"));

                    if ((MCollectionUtils.isEmpty(includes) && MCollectionUtils.isEmpty(excludes))
                            || (MCollectionUtils.isNotEmpty(includes) && includes.contains(methodNameNoGet))
                            || (MCollectionUtils.isNotEmpty(excludes) && !excludes.contains(methodNameNoGet))) {
                        String name = null;
                        String nameField;

                        if (StringUtils.isNotBlank(injectName.name())) {
                            nameField = injectName.name();
                        } else {
                            nameField = StringUtils.removeEndIgnoreCase(methodNameNoGet, "Id") + "Name";
                        }

                        if (methodValue != null) {
                            Object converter = ApplicationContextHolder.getBean(injectName.converter());
                            if (converter instanceof InjectNameBatchConverter) {
                                BatchInjectInfo batchInjectInfo = new BatchInjectInfo();

                                batchInjectInfo.ids.add(methodValue);
                                batchInjectInfo.converter = (InjectNameBatchConverter) converter;
                                batchInjectInfo.params = injectName.param();
                                batchInjectInfo.nameField = nameField;

                                map.put(methodNameNoGet + INJECT_BATCH_INJECT_INFO, batchInjectInfo);
                            } else {
                                if (cache.containsKey(methodNameNoGet, methodValue)) {
                                    name = (String) cache.get(methodNameNoGet, methodValue);
                                } else {
                                    name = ((InjectNameConverter) converter).getName(methodValue, injectName.param());

                                    cache.put(methodNameNoGet, methodValue, name != null ? name : "");
                                }
                            }
                        }

                        map.put(nameField, name != null ? name : "");
                    }
                }
            }

            return map;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

@SuppressWarnings("rawtypes")
class BatchInjectInfo {

    String nameField;

    InjectNameBatchConverter converter;

    String[] params;

    List ids = new ArrayList();

    Map values = new HashMap();
}