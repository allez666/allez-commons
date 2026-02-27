package com.allez.mybatis.dao;

import com.allez.lang.entity.BaseDo;
import com.allez.lang.entity.PageRequest;
import com.allez.lang.entity.PageResponse;
import com.allez.mybatis.convert.PageConverter;
import com.allez.mybatis.convert.SortConverter;
import com.allez.mybatis.test.TestEntity;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

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

    public <R> PageResponse<R> page(PageRequest req, Consumer<LambdaQueryWrapper<T>> consumer, Function<T, R> mapper) {
        Page<T> pageReq = Page.of(req.getPageNumber(), req.getPageSize());
        pageReq.addOrder(SortConverter.convert(req.getSort()));

        LambdaQueryWrapper<T> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        consumer.accept(lambdaQueryWrapper);

        super.executeBatch(new ArrayList<>(),((sqlSession, o) -> {

        }));

        IPage<T> page = super.page(pageReq,lambdaQueryWrapper);
        return PageConverter.convert(page)
                .mapToPage(mapper);
    }


}
