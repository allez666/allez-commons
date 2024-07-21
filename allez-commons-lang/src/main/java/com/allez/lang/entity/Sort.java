package com.allez.lang.entity;

import com.allez.lang.util.AssertUtils;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: chenyu
 * @create: 2024-07-19 00:38
 * @Description:
 */
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Sort implements Serializable {


    private final List<OrderItem> orderChains = new ArrayList<>();

//    public static Sort desc(String... properties) {
//        List<OrderItem> orderItems = OrderItem.desc(properties);
//        return by(orderItems);
//    }


//    public static Sort desc(List<String> properties) {
//        List<OrderItem> orderItems = OrderItem.desc(properties);
//        return by(orderItems);
//    }
//
//    public static Sort asc(String... properties) {
//        List<OrderItem> orderItems = OrderItem.asc(properties);
//        return by(orderItems);
//    }

    public static Sort by(OrderItem... orderItems) {
        return by(Arrays.asList(orderItems));
    }

    public static Sort by(List<OrderItem> orderItems) {
        Sort sort = new Sort();
        for (OrderItem orderItem : orderItems) {
            sort.and(orderItem);
        }
        return sort;
    }

//    public static Sort by(String property, String direction) {
//        Sort sort = new Sort();
//        return sort.and(property, direction);
//    }
//
//    public Sort and(String property, String direction) {
//        this.orderChains.add(new OrderItem(property, Direction.valueOf(direction)));
//        return this;
//    }
//
//    public Sort and(String property, Direction direction) {
//        this.orderChains.add(OrderItem.of(property, direction));
//        return this;
//    }


    public Sort and(OrderItem orderItem) {
        this.orderChains.add(orderItem);
        return this;
    }

    public Sort and(OrderItem... orderItems) {
        this.orderChains.addAll(Arrays.asList(orderItems));
        return this;
    }


//    public Sort andDesc(String property) {
//        this.orderChains.add(OrderItem.desc(property));
//        return this;
//    }
//
//
//    public Sort andAsc(String property) {
//        this.orderChains.add(OrderItem.asc(property));
//        return this;
//    }

    public static void main(String[] args) {
        // 这样格式好看一点，统一格式
        Sort and = Sort.by(OrderItem.desc("createTime"))
                .and(OrderItem.desc("createTime"))
                .and(OrderItem.desc("createTime"))
                .and(OrderItem.desc("createTime"))
                .and(OrderItem.asc("ccc"));
        System.out.println(and);
    }


    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @Data
    public static class OrderItem implements Serializable {

        private String property;

        private Direction direction;

        public static OrderItem desc(String property) {
            return new OrderItem(property, Direction.DESC);
        }

        public static List<OrderItem> desc(String... properties) {
            return Arrays.stream(properties).map(OrderItem::desc).collect(Collectors.toList());
        }

        public static List<OrderItem> desc(List<String> properties) {
            return properties.stream().map(OrderItem::desc).collect(Collectors.toList());
        }


        public static OrderItem asc(String property) {
            return new OrderItem(property, Direction.ASC);
        }

        public static List<OrderItem> asc(String... properties) {
            return Arrays.stream(properties).map(OrderItem::asc).collect(Collectors.toList());
        }

        public static List<OrderItem> asc(List<String> properties) {
            return properties.stream().map(OrderItem::asc).collect(Collectors.toList());
        }


    }

    @Getter
    @AllArgsConstructor
    public enum Direction implements Serializable {

        ASC,
        DESC,

        ;

        public boolean isAscending() {
            return this.equals(ASC);
        }

        public boolean isDescending() {
            return this.equals(DESC);
        }

        public Direction of(String value) {
            Direction direction = valueOf(value.toUpperCase());
            AssertUtils.notNull(direction, String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value));
            return direction;
        }

    }
}
