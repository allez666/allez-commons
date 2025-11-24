package com.allez.lang.exception;

import com.allez.lang.enums.ResultCode;
import lombok.Getter;

/**
 * 系统异常
 * 用于系统级的不可预期异常，如数据库连接失败、第三方服务调用失败等
 * 会被全局异常处理器捕获并统一包装为 FAIL(-1) 返回给前端
 * <p>
 * 使用建议：
 * 1. 数据库操作失败：new SystemException(ResultCode.DATABASE_ERROR, e)
 * 2. 第三方服务调用失败：new SystemException(ResultCode.THIRD_PARTY_API_ERROR, e)
 * 3. 缓存服务异常：new SystemException(ResultCode.CACHE_ERROR, e)
 *
 * @author chenyu
 * @date 2024/12/31 15:59
 */
@Getter
public class SystemException extends RuntimeException {

    private final Integer code;

    /**
     * 使用枚举码构造异常
     *
     * @param resultCode 结果码枚举
     */
    public SystemException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    /**
     * 使用枚举码和原始异常构造异常
     * 推荐：用于包装底层异常，保留完整堆栈信息
     *
     * @param resultCode 结果码枚举
     * @param cause      原始异常
     */
    public SystemException(ResultCode resultCode, Throwable cause) {
        super(resultCode.getMsg(), cause);
        this.code = resultCode.getCode();
    }

    /**
     * 使用枚举码、自定义消息和原始异常构造异常
     *
     * @param resultCode 结果码枚举
     * @param message    错误消息
     * @param cause      原始异常
     */
    public SystemException(ResultCode resultCode, String message, Throwable cause) {
        super(message, cause);
        this.code = resultCode.getCode();
    }

    /**
     * 使用自定义消息构造异常（默认使用FAIL码）
     *
     * @param message 错误消息
     */
    public SystemException(String message) {
        super(message);
        this.code = ResultCode.FAIL.getCode();
    }

    /**
     * 使用自定义消息和原始异常构造异常（默认使用FAIL码）
     *
     * @param message 错误消息
     * @param cause   原始异常
     */
    public SystemException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCode.FAIL.getCode();
    }

    /**
     * 使用自定义code和消息构造异常
     * 不推荐：建议使用ResultCode枚举
     *
     * @param code    错误码
     * @param message 错误消息
     */
    public SystemException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
