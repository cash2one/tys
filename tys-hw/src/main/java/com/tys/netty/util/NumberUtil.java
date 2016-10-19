package com.tys.netty.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class NumberUtil {
	
    public static final int DEFAULT_SCALE = 2;

	public static Double DOUBLE_ZERO = Double.valueOf("0");

	/**
	 * 如果d1为null，返回d2
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static Double nvl(Double d1, Double d2) {
		if (d1 == null)
			return d2;
		else
			return d1;
	}

	public static Double nvl(Double d1) {
		if (d1 == null)
			return 0.00D;
		else
			return d1;
	}

	public static Long nvl(Long d1, Long d2) {
		if (d1 == null)
			return d2;
		else
			return d1;
	}

	public static Long nvl(Long d1) {
		if (d1 == null)
			return 0L;
		else
			return d1;
	}

	public static Integer nvl(Integer d1, Integer d2) {
		if (d1 == null)
			return d2;
		else
			return d1;
	}

	public static Integer nvl(Integer d1) {
		if (d1 == null)
			return 0;
		else
			return d1;
	}

	/**
	 * 两个数字是否相等
	 */
	public static boolean equals(Short n1, Short n2) {
		return equalsImpl(n1, n2);
	}

	/**
	 * 两个数字是否相等
	 */
	public static boolean equals(Integer n1, Integer n2) {
		return equalsImpl(n1, n2);
	}

	/**
	 * 两个数字是否相等
	 */
	public static boolean equals(Long n1, Long n2) {
		return equalsImpl(n1, n2);
	}

	/**
	 * 两个数字是否相等
	 */
	public static boolean equals(Double n1, Double n2) {
		return equalsImpl(n1, n2);
	}

	private static boolean equalsImpl(Number n1, Number n2) {
		if (n1 == null && n2 == null) {
			return true;
		}

		if (n1 == null || n2 == null) {
			return false;
		}

		return n1.equals(n2);
	}

	public static boolean equalsAnyone(Integer n1, Integer... n2) {
		return equalsAnyoneImpl(n1, n2);
	}

	public static boolean equalsAnyone(Long n1, Long... n2) {
		return equalsAnyoneImpl(n1, n2);
	}

	public static boolean equalsAnyone(Double n1, Double... n2) {
		return equalsAnyoneImpl(n1, n2);
	}

	private static boolean equalsAnyoneImpl(Number n1, Number... n2) {
		if (n1 == null && (n2 == null || n2.length == 0)) {
			return true;
		}

		for (Number n : n2) {
			if (equalsImpl(n1, n)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 保留小数精度
	 * 
	 * @param db
	 * @param num
	 * @return
	 */
	public static Double parseScale(Double db, int num) {
		if (null == db)
			return Double.valueOf(0);
		return new BigDecimal(db).setScale(num, RoundingMode.HALF_UP)
				.doubleValue();
	}
	
	
	/**
     * 四舍五入-保留两位小数
     * 
     * @param d
     * @return
     */
    public static Double round(Double d) {
        return round(d, DEFAULT_SCALE, false);
    }

    /**
     * 四舍五入-保留两位小数
     * 
     * @param d
     * @param scale
     *            保留的位数
     * @return
     */
    public static Double round(Double d, int scale) {
        return round(d, scale, false);
    }

    /**
     * 四舍五入保留指定的小数
     * 
     * @param d
     * @param scale
     *            保留的位数
     * @param isGroupingUsed
     *            是否显示千分位
     * @return 返回处理后的结果，出现异常则返回d
     */
    public static Double round(Double d, int scale, boolean isGroupingUsed) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(scale);
        nf.setGroupingUsed(isGroupingUsed);
        try {
            return Double.valueOf(nf.format(d));
        } catch (RuntimeException e) {
            return Double.valueOf(d);
        }
    }
}
