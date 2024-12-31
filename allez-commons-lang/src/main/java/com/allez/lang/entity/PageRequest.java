package com.allez.lang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: chenyu
 * @create: 2024-07-19 00:46
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageRequest implements Serializable, BasePage, BaseSort {

    @NotNull
    @Min(1)
    private int pageNumber;

    @NotNull
    @Min(1)
    private int pageSize;

    private Sort sort;


    public PageRequest(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }


    public PageRequest(long offset, int limit) {
        this.pageNumber = (int) (offset / limit + 1);
        this.pageSize = limit;
    }


    public long toOffset() {
        return (long) (this.getPageNumber() - 1) * this.getPageSize();
    }

    public int toLimit() {
        return this.getPageSize();
    }


    public static PageRequest of(int pageNumber, int pageSize) {
        return new PageRequest(pageNumber, pageSize);
    }

    public static PageRequest of(long offset, int limit) {
        return new PageRequest(offset, limit);
    }

    public PageRequest setSort(Sort sort) {
        this.sort = sort;
        return this;
    }

    public static void main(String[] args) {
        PageRequest pageRequest = PageRequest.of(1, 2)
                .setSort(
                        Sort.by(Sort.OrderItem.desc("ccc"))
                                .and(Sort.OrderItem.asc("aaa"))
                );
    }

}
