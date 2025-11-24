# 异常处理设计说明

## 设计原则

通过**错误码范围**区分展示方式，无需额外的 `needPopup` 字段：

- **200**: 成功
- **负数（-1, -2, -100~-999）**: 需要弹窗提示的业务错误
- **正数（1000+）**: 普通提示的业务错误

前端只需判断：`code < 0` 则弹窗提示，`code > 0` 则普通提示。

---

## 一、ResultCode 枚举

### 错误码分类

```java
// 成功
SUCCESS(200, "success")

// 需要弹窗的业务错误（负数）
FAIL(-1, "操作失败")                              // 通用失败，系统异常统一返回
REMIND(-2, "提示")                                // 通用提醒
USER_LOGIN_ERROR(-200, "用户名或密码错误")          // 用户相关
STOCK_NOT_ENOUGH(-403, "库存不足")                // 业务操作

// 普通业务错误（正数）
PARAM_VALID_ERROR(1000, "参数校验失败")           // 参数相关
DATA_NOT_EXIST(2000, "数据不存在")                // 数据相关
USER_NOT_EXIST(3000, "用户不存在")                // 用户相关
```

---

## 二、异常类使用

### 1. BusinessException（业务异常）

#### 场景1：直接使用枚举
```java
// 需要弹窗提示
throw new BusinessException(ResultCode.USER_LOGIN_ERROR);  // code=-200

// 普通提示
throw new BusinessException(ResultCode.USER_NOT_EXIST);    // code=3000
```

#### 场景2：使用枚举 + 自定义消息
```java
// 需要弹窗提示
throw new BusinessException(ResultCode.STOCK_NOT_ENOUGH, "商品库存不足，当前仅剩5件");

// 普通提示
throw new BusinessException(ResultCode.DATA_NOT_EXIST, "ID为123的数据不存在");
```

#### 场景3：快速抛出弹窗提示异常
```java
// 默认使用 REMIND(-2) 码
throw new BusinessException("您的余额不足，请先充值");
// 或使用静态方法
throw BusinessException.remind("操作成功，请等待审核");
```

#### 场景4：格式化消息
```java
throw BusinessException.of(
    ResultCode.STOCK_NOT_ENOUGH,
    "商品[%s]库存不足，需要%d件，当前仅有%d件",
    productName, required, available
);
```

---

### 2. SystemException（系统异常）

系统异常会被全局异常处理器统一包装为 `FAIL(-1)`，不暴露详细错误给前端。

#### 场景1：数据库操作失败
```java
try {
    // 数据库操作
    userMapper.insert(user);
} catch (Exception e) {
    throw new SystemException(ResultCode.DATABASE_ERROR, e);
}
```

#### 场景2：第三方服务调用失败
```java
try {
    // 调用第三方API
    paymentService.pay(order);
} catch (Exception e) {
    throw new SystemException(ResultCode.THIRD_PARTY_API_ERROR, "调用支付接口失败", e);
}
```

#### 场景3：缓存服务异常
```java
try {
    // Redis操作
    redisTemplate.opsForValue().set(key, value);
} catch (Exception e) {
    throw new SystemException(ResultCode.CACHE_ERROR, e);
}
```

---

## 三、全局异常处理器

### Spring MVC 全局异常处理器示例

```java
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     * 直接返回异常中的code和message给前端
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(BusinessException e) {
        log.warn("业务异常: code={}, message={}", e.getCode(), e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 处理系统异常
     * 统一包装为 FAIL(-1)，不暴露详细错误给前端
     */
    @ExceptionHandler(SystemException.class)
    public Result<?> handleSystemException(SystemException e) {
        log.error("系统异常: code={}, message={}", e.getCode(), e.getMessage(), e);
        // 返回统一的错误提示，不暴露系统内部错误
        return Result.error(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMsg());
    }

    /**
     * 处理参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.warn("参数校验失败: {}", message);
        return Result.error(ResultCode.PARAM_VALID_ERROR.getCode(), message);
    }

    /**
     * 处理未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        log.error("未知异常", e);
        return Result.error(ResultCode.FAIL.getCode(), "系统繁忙，请稍后再试");
    }
}
```

### 响应对象示例

```java
@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}
```

---

## 四、前端处理

### JavaScript axios 拦截器示例

```javascript
// axios 响应拦截器
axios.interceptors.response.use(
    response => {
        const { code, message } = response.data;

        // 成功
        if (code === 200) {
            return response.data;
        }

        // 错误处理：根据错误码范围判断展示方式
        if (code < 0) {
            // 负数：弹窗提示（Toast/Alert）
            Toast.error(message);
        } else if (code > 0) {
            // 正数：普通提示（Message）
            Message.error(message);
        }

        return Promise.reject(response.data);
    },
    error => {
        // 网络错误
        Toast.error('网络异常，请稍后重试');
        return Promise.reject(error);
    }
);
```

### Vue 3 示例

```javascript
import { ElMessage, ElMessageBox } from 'element-plus';

axios.interceptors.response.use(
    response => {
        const { code, message } = response.data;

        if (code === 200) {
            return response.data;
        }

        // 根据错误码判断展示方式
        if (code < 0) {
            // 弹窗提示
            ElMessageBox.alert(message, '提示', { type: 'warning' });
        } else if (code > 0) {
            // 普通消息提示
            ElMessage.error(message);
        }

        return Promise.reject(response.data);
    }
);
```

---

## 五、实际业务场景示例

### 场景1：用户登录

```java
@Service
public class UserService {

    public LoginVO login(String username, String password) {
        // 1. 参数校验（抛出普通错误）
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(ResultCode.PARAM_MISSING, "用户名不能为空");
        }

        // 2. 查询用户（可能抛出系统异常）
        User user = findUser(username);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_EXIST);  // 普通提示
        }

        // 3. 验证密码（抛出弹窗提示）
        if (!user.getPassword().equals(password)) {
            throw new BusinessException(ResultCode.USER_LOGIN_ERROR);  // 弹窗提示
        }

        // 4. 检查用户状态（抛出弹窗提示）
        if (user.isDisabled()) {
            throw new BusinessException(ResultCode.USER_DISABLED);  // 弹窗提示
        }

        // 5. 生成token
        String token = generateToken(user);
        return new LoginVO(token, user);
    }

    private User findUser(String username) {
        try {
            return userMapper.selectByUsername(username);
        } catch (Exception e) {
            // 数据库异常，抛出系统异常
            throw new SystemException(ResultCode.DATABASE_ERROR, e);
        }
    }
}
```

### 场景2：创建订单

```java
@Service
@Transactional
public class OrderService {

    public OrderVO createOrder(Long productId, int quantity) {
        // 1. 查询商品（普通错误）
        Product product = productService.getById(productId);
        if (product == null) {
            throw new BusinessException(ResultCode.DATA_NOT_EXIST, "商品不存在");
        }

        // 2. 检查库存（弹窗提示）
        if (product.getStock() < quantity) {
            throw BusinessException.of(
                ResultCode.STOCK_NOT_ENOUGH,
                "商品[%s]库存不足，当前库存%d件",
                product.getName(), product.getStock()
            );
        }

        // 3. 检查用户余额（弹窗提示）
        User user = getCurrentUser();
        double totalAmount = product.getPrice() * quantity;
        if (user.getBalance() < totalAmount) {
            throw new BusinessException(ResultCode.BALANCE_NOT_ENOUGH);
        }

        // 4. 扣减库存（可能抛出系统异常）
        try {
            productService.deductStock(productId, quantity);
        } catch (Exception e) {
            throw new SystemException(ResultCode.DATABASE_ERROR, "扣减库存失败", e);
        }

        // 5. 创建订单
        Order order = new Order();
        // ... 设置订单信息
        orderMapper.insert(order);

        return OrderVO.from(order);
    }
}
```

### 场景3：调用第三方支付

```java
@Service
public class PaymentService {

    public void pay(Order order) {
        try {
            // 调用第三方支付接口
            PaymentResult result = thirdPartyPaymentApi.pay(order);

            if (!result.isSuccess()) {
                // 支付失败，需要弹窗提示用户
                throw new BusinessException(ResultCode.PAYMENT_FAILED, result.getMessage());
            }
        } catch (BusinessException e) {
            // 业务异常直接抛出
            throw e;
        } catch (Exception e) {
            // 系统异常，包装后抛出
            throw new SystemException(ResultCode.THIRD_PARTY_API_ERROR, "调用支付接口失败", e);
        }
    }
}
```

---

## 六、总结

### 优点

1. **简单直观**：通过错误码范围即可判断展示方式，无需额外字段
2. **灵活可扩展**：支持使用枚举或自定义消息
3. **职责清晰**：业务异常和系统异常分离，便于统一处理
4. **前端友好**：前端只需一行判断 `code < 0` 即可决定展示方式

### 使用建议

1. **优先使用枚举**：预定义的错误码便于统一管理
2. **需要弹窗的场景**：
   - 用户登录失败
   - 余额/库存不足
   - 权限不足
   - 操作成功的提示
3. **普通提示的场景**：
   - 参数校验失败
   - 数据不存在
   - 状态异常
4. **系统异常**：始终记录详细日志，但不暴露给前端