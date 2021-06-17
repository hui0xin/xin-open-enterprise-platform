package com.xin.commons.multi.mybatis.automatic.utils;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 核心基于ThreadLocal的切换数据源工具类
 * @since 1.0.0
 */
public final class DynamicDataSourceContextHolder {

    private static final ThreadLocal<LinkedBlockingDeque<String>> LOOKUP_KEY_HOLDER = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return new LinkedBlockingDeque();
        }
    };

    private DynamicDataSourceContextHolder() { }

    /**
     * 获得当前线程数据源
     *
     * @return 数据源名称
     */
    public static String getDataSourceLookupKey() {
        LinkedBlockingDeque<String> deque = LOOKUP_KEY_HOLDER.get();
        return deque.isEmpty() ? null : deque.pollFirst();
    }

    /**
     * 设置当前线程数据源
     */
    public static void setDataSourceLookupKey(String dataSourceLookupKey) {
        LOOKUP_KEY_HOLDER.get().addFirst(dataSourceLookupKey);
    }

    /**
     * 清空当前线程数据源
     * <p>
     * 如果当前线程是连续切换数据源
     * 只会移除掉当前线程的数据源名称
     * </p>
     */
    public static void clearDataSourceLookupKey() {
        LinkedBlockingDeque<String> deque = LOOKUP_KEY_HOLDER.get();
        if (deque.isEmpty()) {
            LOOKUP_KEY_HOLDER.remove();
        }
    }
}
