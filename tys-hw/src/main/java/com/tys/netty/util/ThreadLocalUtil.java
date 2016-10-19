package com.tys.netty.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程对象操作工具类 </b>
 */
public class ThreadLocalUtil {

    private static ThreadLocal<Map<String, Object>> threadMap = new ThreadLocal<Map<String, Object>>() {

        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        };
    };

    public static void put(String key, Object value) {
        threadMap.get().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) threadMap.get().get(key);
    }

    public static boolean containsKey(String key) {
        return threadMap.get().containsKey(key);
    }

    public static void remove() {
        threadMap.remove();
    }

    public static final class ThreadLocalKey {

        public static final String CURRENT_TIME = "CURRENT_TIME";
    }
}
