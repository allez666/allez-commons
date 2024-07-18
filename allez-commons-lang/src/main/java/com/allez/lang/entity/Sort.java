package com.allez.lang.entity;

import com.allez.lang.util.AssertUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: chenGuanXi
 * @create: 2024-07-19 00:38
 * @Description:
 */
@AllArgsConstructor
@Data
public class Sort implements Serializable {

    private final List<OrderItem> orderChains = new ArrayList<>();

    public static Sort desc(String... property) {

    }

    public static Sort asc(String... property) {

    }

    public static Sort by(OrderItem... orderItem) {

    }

    public static Sort by(String property, String direction) {
        return
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class OrderItem implements Serializable {

        private String property;

        private Direction direction;

        public static
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


        public Direction ofIgnoreCase(String value) {
            for (Direction direction : values()) {
                if (direction.name().equalsIgnoreCase(value)) {
                    return direction;
                }
            }
            throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value));
        }

    }
}
