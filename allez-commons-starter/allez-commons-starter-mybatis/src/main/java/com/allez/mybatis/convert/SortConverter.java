package com.allez.mybatis.convert;

import com.allez.lang.entity.Sort;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排序参数转换器
 * <p>
 * 负责将业务层的 {@link Sort} 对象转换为 MyBatis-Plus 的 {@link OrderItem} 列表。
 * 此组件通常被自定义 Starter 自动加载，供 DAO 层或 Service 层复用。
 * </p>
 *
 * @author chenyu
 * @since 1.0.0
 */
public final class SortConverter {

    private SortConverter() {
        // 私有构造，防止实例化
    }

    /**
     * 将 Sort 对象转换为 MyBatis-Plus 的 OrderItem 列表
     *
     * @param sort 业务排序对象
     * @return MP 排序项列表，若为空则返回空列表
     */
    public static List<OrderItem> convert(Sort sort) {
        if (sort == null || CollectionUtils.isEmpty(sort.getOrderChains())) {
            return Collections.emptyList();
        }

        return sort.getOrderChains()
                .stream()
                .map(SortConverter::adaptOrderChain)
                .collect(Collectors.toList());
    }


    private static OrderItem adaptOrderChain(Sort.OrderItem orderItem) {
        if (orderItem.getDirection().isAscending()) {
            return OrderItem.asc(orderItem.getProperty());
        } else {
            return OrderItem.desc(orderItem.getProperty());
        }
    }
}