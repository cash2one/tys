package com.tys.excel.exception;

public class BusinessException extends RuntimeException {


    /**
	 * 
	 */
	private static final long serialVersionUID = -358656756851225635L;

	// 业务异常码
    private String code;

    // 参数
    private String[] params;

    public BusinessException(String code) {
        this.code = code;
    }

    public BusinessException(String code, String... params) {
        this.code = code;
        this.params = params;
    }

    public String getCode() {
        return code;
    }

    public String[] getParams() {
        return params;
    }
}
