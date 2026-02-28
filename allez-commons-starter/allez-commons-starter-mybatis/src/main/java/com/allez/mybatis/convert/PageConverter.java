package com.allez.mybatis.convert;

import com.allez.lang.entity.PageResponse;
import com.allez.lang.entity.Sort;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class PageConverter {
    private PageConverter() {
        // 私有构造，防止实例化
    }

    public static <T> PageResponse<T> convert(IPage<T> page) {
        return PageResponse.of(
                (int) page.getCurrent(), (int) page.getSize()
                , page.getTotal(), page.getRecords()
        );
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
                .map(PageConverter::adaptOrderChain)
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
