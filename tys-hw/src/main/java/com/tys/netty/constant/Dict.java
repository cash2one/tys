package com.tys.netty.constant;

public class Dict {

	public enum CommondType {

		A("A"),

		B("B"),
		
		P("P"),
		
		T("T");

		private final String value;

		CommondType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	

	public static enum YesOrNo {
		YES(1, "是"), NO(0, "否");

		private final Integer value;

		private final String memo;

		public Integer getValue() {
			return value;
		}

		public int intValue() {
			return value;
		}

		public String getMemo() {
			return memo;
		}

		public String strValue() {
			return String.valueOf(value);
		}

		private YesOrNo(Integer value, String memo) {
			this.value = value;
			this.memo = memo;
		}
	}

	


	/**
	 * 事件日志类型
	 */
	public enum EventLogType {
		ERROR(1),

		WARN(2),

		INFO(3),

		UNKOWN(99);

		private final Integer value;

		EventLogType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	/**
	 * 事件主题
	 */
	public enum EventSubject {
		UNKOWN("无主题");

		private final String value;

		EventSubject(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

}
