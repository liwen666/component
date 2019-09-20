package com.temp.common.message;



import java.io.Serializable;

/**
 * 通用结果类
 *
 * @param <T>
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -2047713519405708632L;
    private static final int successCode = 0;
    private static final int errorCode = -1;

    private boolean success;// 返回是否成功
    private int code;// 提示代码
    private String message;// 返回信息
    private T data;// 返回其他对象信息
    private Object[] i18nParam;
    

    public Object[] getI18nParam() {
		return i18nParam;
	}

	public void setI18nParam(Object[] i18nParam) {
		this.i18nParam = i18nParam;
	}

	public boolean getSuccess() {
        return success;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        if (message != null) {
            return message;
        }
        if (success) {
            return "successful";
        }
        return "error";

    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Result() {

    }

    public Result(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return Result.success(null);
    }

    public static <T> Result<T> success(String message) {
        return Result.success(successCode, message);
    }


    public static <T> Result<T> success(T data) {
        return Result.success(null, data);
    }

    public static <T> Result<T> success(int code, String message) {
        return Result.success(code, message, null);
    }

    public static <T> Result<T> success(String message, T data) {
        return Result.success(successCode, message, data);
    }

    public static <T> Result<T> success(int code, String message, T data) {
        return new Result<>(true, code, message, data);
    }

    public static <T> Result<T> error() {
        return Result.error(errorCode);
    }

    public static <T> Result<T> error(int code) {
        return Result.error(code, null);
    }

    public static <T> Result<T> error(String message) {
        return Result.error(errorCode, message);
    }

    public static <T> Result<T> error(int code, String message) {
        return Result.error(code, message, null);
    }

    public static <T> Result<T> error(int code, T data) {
        return Result.error(code, null, data);
    }

    public static <T> Result<T> error(int code, String message, T data) {
        return new Result<>(false, code, message, data);
    }

    public static <T> Result<T> convert(Result result) {
        if (null == result) {
            throw new IllegalArgumentException("转换结果为空");
        }
        return new Result<>(result.getSuccess(), result.getCode(), result.getMessage(), null);
    }


    public static <T> Result<T> error(CodeEnums codeEnums) {
        return Result.error(codeEnums.getCode(), codeEnums.getCnDesc());
    }

}
