package com.allez.mybatis.dao;

import com.allez.lang.entity.BaseDo;
import com.allez.lang.entity.PageRequest;
import com.allez.lang.entity.PageResponse;
import com.allez.lang.entity.Sort;
import com.allez.mybatis.convert.PageConverter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author: chenyu
 * @create: 2024-08-13 00:38
 * @Description: 拓展分页方法的接口
 */

public abstract class BaseDao<M extends BaseMapper<T>, T extends BaseDo<ID>, ID extends Serializable>
        extends ServiceImpl<M, T> {


    public PageResponse<T> page(PageRequest req, Consumer<LambdaQueryWrapper<T>> consumer) {
        return this.page(req, consumer, Function.identity());
    }

    public <R> PageResponse<R> page(PageRequest req, Consumer<LambdaQueryWrapper<T>> consumer, Function<T, R> mapper) {

        // page
        Page<T> pageReq = Page.of(req.getPageNumber(), req.getPageSize());

        // sort
        Sort sort = req.getSort();
        pageReq.addOrder(PageConverter.convert(sort));

        // condition
        LambdaQueryWrapper<T> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        consumer.accept(lambdaQueryWrapper);

        IPage<T> page = super.page(pageReq, lambdaQueryWrapper);

        super.executeBatch()
        return PageConverter.convert(page)
                .mapToPage(mapper);
    }



}
