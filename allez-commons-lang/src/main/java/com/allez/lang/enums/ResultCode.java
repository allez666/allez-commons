package com.allez.lang.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author chenyu
 * @date 2023/12/21 19:46
 * @description 业务响应状态码枚举
 * <p>
 * 错误码规则：
 * - 200: 成功
 * - 负数（-1, -2, -100~-999）: 需要弹窗提示的业务错误
 * - 正数（1000+）: 普通提示的业务错误
 * <p>
 * 前端判断逻辑：code < 0 则弹窗提示，code > 0 则普通提示
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResultCode implements IValue<Integer> {

    // ========== 成功响应 ==========
    SUCCESS(200, "success"),

    // ========== 通用错误（需要弹窗） ==========
    /**
     * 业务处理失败（普通失败，用于系统异常统一返回）
     */
    FAIL(-1, "操作失败"),

    /**
     * 业务提醒（需要弹窗提示）
     */
    REMIND(-2, "提示"),

    // ========== 需要弹窗的业务错误 -1xx~-9xx ==========
    /**
     * 数据已存在
     */
    DATA_ALREADY_EXIST(-100, "数据已存在"),

    /**
     * 数据已被删除
     */
    DATA_DELETED(-101, "数据已被删除"),

    /**
     * 数据已过期
     */
    DATA_EXPIRED(-102, "数据已过期"),

    /**
     * 重复操作
     */
    DUPLICATE_OPERATION(-103, "请勿重复操作"),

    /**
     * 用户名或密码错误
     */
    USER_LOGIN_ERROR(-200, "用户名或密码错误"),

    /**
     * 用户已被禁用
     */
    USER_DISABLED(-201, "用户已被禁用"),

    /**
     * 用户已存在
     */
    USER_ALREADY_EXIST(-202, "用户已存在"),

    /**
     * 用户登录已过期
     */
    USER_LOGIN_EXPIRED(-203, "登录已过期，请重新登录"),

    /**
     * 密码错误次数过多
     */
    USER_PASSWORD_ERROR_TOO_MANY(-204, "密码错误次数过多，账号已被锁定"),

    /**
     * 用户信息不完整
     */
    USER_INFO_INCOMPLETE(-205, "用户信息不完整"),

    /**
     * 账号异常
     */
    USER_ACCOUNT_ABNORMAL(-206, "账号异常，请联系管理员"),

    /**
     * 无访问权限
     */
    NO_PERMISSION(-300, "无访问权限"),

    /**
     * Token已过期
     */
    TOKEN_EXPIRED(-301, "Token已过期"),

    /**
     * 未授权访问
     */
    UNAUTHORIZED(-302, "未授权访问"),

    /**
     * 角色权限不足
     */
    ROLE_PERMISSION_DENIED(-303, "角色权限不足"),

    /**
     * 访问被拒绝
     */
    ACCESS_DENIED(-304, "访问被拒绝"),

    /**
     * 操作不被允许
     */
    OPERATION_NOT_ALLOWED(-400, "操作不被允许"),

    /**
     * 操作超时
     */
    OPERATION_TIMEOUT(-401, "操作超时"),

    /**
     * 余额不足
     */
    BALANCE_NOT_ENOUGH(-402, "余额不足"),

    /**
     * 库存不足
     */
    STOCK_NOT_ENOUGH(-403, "库存不足"),

    /**
     * 支付失败
     */
    PAYMENT_FAILED(-404, "支付失败"),

    /**
     * 退款失败
     */
    REFUND_FAILED(-405, "退款失败"),

    /**
     * 文件上传失败
     */
    FILE_UPLOAD_ERROR(-500, "文件上传失败"),

    /**
     * 文件下载失败
     */
    FILE_DOWNLOAD_ERROR(-501, "文件下载失败"),

    /**
     * 文件大小超出限制
     */
    FILE_SIZE_EXCEED(-502, "文件大小超出限制"),

    /**
     * 文件类型不支持
     */
    FILE_TYPE_NOT_SUPPORT(-503, "文件类型不支持"),

    /**
     * 文件格式错误
     */
    FILE_FORMAT_ERROR(-504, "文件格式错误"),

    /**
     * 文件已损坏
     */
    FILE_CORRUPTED(-505, "文件已损坏"),

    /**
     * 系统繁忙
     */
    SYSTEM_BUSY(-600, "系统繁忙，请稍后再试"),

    /**
     * 系统维护中
     */
    SYSTEM_MAINTENANCE(-601, "系统维护中"),

    /**
     * 服务不可用
     */
    SERVICE_UNAVAILABLE(-602, "服务不可用"),

    /**
     * 请求过于频繁
     */
    TOO_MANY_REQUESTS(-603, "请求过于频繁，请稍后再试"),

    /**
     * 网络异常
     */
    NETWORK_ERROR(-604, "网络异常"),

    // ========== 普通业务错误 1xxx+ ==========

    // 参数相关错误 1xxx
    /**
     * 参数校验失败
     */
    PARAM_VALID_ERROR(1000, "参数校验失败"),

    /**
     * 参数缺失
     */
    PARAM_MISSING(1001, "必填参数缺失"),

    /**
     * 参数类型错误
     */
    PARAM_TYPE_ERROR(1002, "参数类型错误"),

    /**
     * 参数格式错误
     */
    PARAM_FORMAT_ERROR(1003, "参数格式错误"),

    /**
     * 参数超出范围
     */
    PARAM_OUT_OF_RANGE(1004, "参数超出范围"),

    // 数据相关错误 2xxx
    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(2000, "数据不存在"),

    /**
     * 数据状态异常
     */
    DATA_STATUS_ERROR(2001, "数据状态异常"),

    // 用户相关错误 3xxx
    /**
     * 用户不存在
     */
    USER_NOT_EXIST(3000, "用户不存在"),

    /**
     * 用户未登录
     */
    USER_NOT_LOGIN(3001, "用户未登录"),

    // 权限相关错误 4xxx
    /**
     * Token无效
     */
    TOKEN_INVALID(4000, "Token无效"),

    // 业务操作错误 5xxx
    /**
     * 操作失败
     */
    OPERATION_FAILED(5000, "操作失败"),

    /**
     * 订单状态异常
     */
    ORDER_STATUS_ERROR(5001, "订单状态异常"),

    // 文件相关错误 6xxx
    /**
     * 文件不存在
     */
    FILE_NOT_EXIST(6000, "文件不存在"),

    // 系统服务错误 7xxx（这些通常由SystemException抛出，全局异常处理器会转换为FAIL）
    /**
     * 远程服务调用失败
     */
    REMOTE_SERVICE_ERROR(7000, "远程服务调用失败"),

    /**
     * 数据库操作失败
     */
    DATABASE_ERROR(7001, "数据库操作失败"),

    /**
     * 缓存服务异常
     */
    CACHE_ERROR(7002, "缓存服务异常"),

    /**
     * 消息队列异常
     */
    MQ_ERROR(7003, "消息队列异常"),

    /**
     * 第三方API调用失败
     */
    THIRD_PARTY_API_ERROR(7004, "第三方API调用失败"),

    /**
     * 配置错误
     */
    CONFIG_ERROR(7005, "配置错误"),
    ;

    private final int code;

    private final String msg;

    @Override
    public Integer getValue() {
        return this.code;
    }
}