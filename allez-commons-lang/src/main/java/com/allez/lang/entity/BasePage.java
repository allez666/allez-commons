package com.allez.lang.entity;

/**
 * @author chenyu
 * @date 2024/12/31 15:57
 * @description
 */
public interface BasePage {

    /**
     * 获取当前页数
     * @return int
     */
    int getPageNumber();

    /**
     * 一页多少数据
     * @return int
     */
    int getPageSize();
}
