package com.allez.lang.exception;

import com.allez.lang.enums.ResultCode;
import lombok.Getter;

/**
 * 业务异常
 * 用于业务逻辑中的可预期异常，会被统一异常处理器捕获并返回给前端
 * <p>
 * 使用建议：
 * 1. 需要弹窗提示：使用负数错误码的枚举，或使用 new BusinessException("自定义消息")
 * 2. 普通提示：使用正数错误码的枚举
 * 3. 自定义消息：使用 new BusinessException(ResultCode.XXX, "自定义消息")
 *
 * @author chenyu
 * @date 2023/12/21 19:24
 */
@Getter
public class BusinessException extends RuntimeException {

    private final Integer code;

    /**
     * 使用枚举码构造异常
     * 推荐：直接使用枚举定义的code和message
     *
     * @param resultCode 结果码枚举
     */
    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    /**
     * 使用枚举码和自定义消息构造异常
     * 推荐：需要使用枚举的code，但消息需要动态生成时
     *
     * @param resultCode 结果码枚举
     * @param message    自定义错误消息
     */
    public BusinessException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }

    /**
     * 使用自定义消息构造异常（默认使用REMIND码-2，会弹窗提示）
     * 推荐：业务层快速抛出需要弹窗提示的异常
     *
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = ResultCode.REMIND.getCode();
    }

    /**
     * 使用自定义code和消息构造异常
     * 不推荐：建议使用ResultCode枚举
     *
     * @param code    错误码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 快速创建需要弹窗提示的异常（静态工厂方法）
     * 等同于 new BusinessException(message)
     *
     * @param message 错误消息
     * @return BusinessException
     */
    public static BusinessException remind(String message) {
        return new BusinessException(message);
    }

    /**
     * 使用枚举码和格式化消息构造异常（静态工厂方法）
     *
     * @param resultCode 结果码枚举
     * @param msgFormat  消息格式（支持String.format）
     * @param args       格式化参数
     * @return BusinessException
     */
    public static BusinessException of(ResultCode resultCode, String msgFormat, Object... args) {
        String message = String.format(msgFormat, args);
        return new BusinessException(resultCode, message);
    }

}
