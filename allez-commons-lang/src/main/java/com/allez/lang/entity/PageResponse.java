package com.allez.lang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author: chenGuanXi
 * @create: 2024-07-22 00:49
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> implements Serializable {

    private int pageNumber;

    private int pageSize;

    private long totalCount;

    private long totalPage;

    private Collection<T> result;

    private Serializable extra;


    public PageResponse(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public PageResponse(int pageNumber, int pageSize, long totalCount, Collection<T> result, Serializable extra) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.result = result;
        this.extra = extra;
        this.totalPage = calculateTotalPage();
    }

    public PageResponse(int pageNumber, int pageSize, long totalCount, Collection<T> result) {
        this(pageNumber, pageSize, totalCount, result, null);
    }

    public static <T> PageResponse<T> of(int pageNumber, int pageSize, long totalCount, Collection<T> data) {
        return new PageResponse<>(pageNumber, pageSize, totalCount, data);
    }

    public static <T> PageResponse<T> of(PageRequest pageRequest, long totalCount, Collection<T> result) {
        return of(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount, result);
    }

    public static <T> PageResponse<T> of(int pageNumber, int pageSize, LongSupplier totalCountSupplier, Supplier<Collection<T>> resultSupplier) {
        return of(pageNumber, pageSize, totalCountSupplier.getAsLong(), resultSupplier.get());
    }

    public static <T> PageResponse<T> of(PageRequest pageRequest, LongSupplier totalCountSupplier, Supplier<Collection<T>> resultSupplier) {
        return of(pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCountSupplier.getAsLong(), resultSupplier.get());
    }

    public static <T> PageResponse<T> from(PageResponse<T> pageResponse) {
        return of(pageResponse.getPageNumber(), pageResponse.getPageSize(), pageResponse.getTotalCount(), pageResponse.getResult());
    }

    public static <T> PageResponse<T> empty(PageRequest pageRequest) {
        return new PageResponse<>(pageRequest.getPageNumber(), pageRequest.getPageSize());
    }

    public static <T> PageResponse<T> empty(int pageSize) {
        return of(1, pageSize, 0, Collections.emptyList());
    }


    public <R> List<R> mapToList(Function<T, R> function) {
        return this.getResult().stream().map(function).collect(Collectors.toList());
    }

    public <R> Set<R> mapToSet(Function<T, R> function) {
        return this.getResult().stream().map(function).collect(Collectors.toSet());
    }

    public <R> PageResponse<R> mapToPage(Function<T, R> function) {
        return of(this.getPageNumber(), this.getPageSize(),
                this.getTotalCount(),
                this.getResult().stream().map(function).collect(Collectors.toList())
        );
    }


    private long calculateTotalPage() {
        if (this.getPageSize() <= 0) {
            return 0;
        }
        return this.totalCount / this.getPageSize() + 1;
    }

    public PageResponse<T> setExtra(Serializable extra) {
        this.extra = extra;
        return this;
    }
}