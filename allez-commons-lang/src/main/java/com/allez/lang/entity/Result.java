package com.allez.lang.entity;

import com.allez.lang.enums.ResultCode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: chenyu
 * @create: 2024-07-22 00:54
 * @Description:
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Result<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    private String traceId;

    public Result(Integer code, String msg, T data, String traceId) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.traceId = traceId;
    }

    public Result(Integer code, String msg, T data) {
        this(code, msg, data, "");
    }

    public Result(ResultCode resultCode, T data) {
        this(resultCode.getCode(), resultCode.getMsg(), data);
    }


    public static <T> Result<T> of(ResultCode resultCode, T data) {
        return new Result<>(resultCode, data);
    }

    public static <T> Result<T> of(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> of(Integer code, String msg) {
        return of(code, msg, null);
    }

    public static <T> Result<T> success(T data) {
        return of(ResultCode.SUCCESS, data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> fail(ResultCode resultCode, T data) {
        return of(resultCode, data);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return of(code, msg, null);
    }

    public static <T> Result<T> fail(String msg) {
        return of(ResultCode.FAIL.getCode(), msg, null);
    }

    public static <T> Result<T> remind(String msg) {
        return of(ResultCode.REMIND.getCode(), msg, null);
    }


}
