package com.tys.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.expression.spel.standard.SpelExpressionParser;

public class MCollectionUtils {

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Collection coll) {
		return (coll == null || coll.isEmpty());
	}

	@SuppressWarnings("rawtypes")
	public static boolean isNotEmpty(Collection coll) {
		return !isEmpty(coll);
	}

	/**
	 * 将list按照指定长度分割为多个list
	 *
	 * @param <T>
	 * @param list
	 * @param subListSize
	 * @return
	 */
	public static <T> List<List<T>> splitList(List<T> list, int subListSize) {
		List<List<T>> r = new ArrayList<List<T>>();
		if (MCollectionUtils.isEmpty(list) || subListSize < 0) {
			r.add(list);
			return r;
		}
		int maxSize = subListSize;
		int subNum = list.size() / maxSize + 1;
		int endIndex = 0;
		for (int i = 0; i < subNum; i++) {
			if (i * maxSize < list.size()) {
				List<T> subList = new ArrayList<T>(maxSize);
				endIndex = (i + 1) * maxSize;
				endIndex = endIndex < list.size() ? endIndex : (list.size());
				subList.addAll(list.subList(i * maxSize, endIndex));

				r.add(subList);
			}
		}

		return r;
	}

	public static <T> List<T> mergeList(List<T>... lists) {
		List<T> result = new ArrayList<T>();

		for (List<T> list : lists) {
			if (MCollectionUtils.isNotEmpty(list)) {
				result.addAll(list);
			}
		}

		return result;
	}

	public static <T> List<T> subList(List<T> list, int offset, int newListSize) {
		if (MCollectionUtils.isEmpty(list) || offset < 0 || newListSize <= 0 || offset >= list.size()) {
			return new ArrayList<T>();
		}

		return list.subList(offset, Math.min(offset + newListSize, list.size()));
	}

	public static <T> ArrayList<T> newArrayList(T... elements) {
		ArrayList<T> list = new ArrayList<T>();

		for (T elem : elements) {
			if (elem != null) {
				list.add(elem);
			}
		}

		return list;
	}

	public static <T> HashSet<T> newHashSet(T... elements) {
		HashSet<T> list = new HashSet<T>();

		for (T elem : elements) {
			if (elem != null) {
				list.add(elem);
			}
		}

		return list;
	}

	public static double avgList(List<?> list) {
		if (MCollectionUtils.isEmpty(list)) {
			return 0.0;
		}

		Object sum = null;
		SpelExpressionParser parser = new SpelExpressionParser();
		Class<?> clazz = list.get(0).getClass();
		for (Object line : list) {
			if (line != null) {
				if (sum == null) {
					sum = line;
				} else {
					sum = parser.parseExpression(sum + "+" + line).getValue(clazz);
				}
			}
		}

		if (sum != null) {
			return parser.parseExpression(sum + "*1.0/" + list.size()).getValue(Double.class);
		} else {
			return 0.0;
		}
	}

	public static long countList(List<?> list) {
		if (isEmpty(list)) {
			return 0L;
		}

		long count = 0L;
		for (Object line : list) {
			if (line != null) {
				count++;
			}
		}

		return count;
	}

}
