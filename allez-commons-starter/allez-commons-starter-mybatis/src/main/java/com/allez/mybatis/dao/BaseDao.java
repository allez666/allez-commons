package com.allez.mybatis.dao;

import cn.hutool.core.collection.CollUtil;
import com.allez.lang.entity.BaseDo;
import com.allez.lang.entity.PageRequest;
import com.allez.lang.entity.PageResponse;
import com.allez.lang.entity.Sort;
import com.allez.mybatis.convert.MpDataConverter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.binding.MapperMethod;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author: chenyu
 * @create: 2024-08-13 00:38
 * @Description: 拓展分页方法的接口
 */

public abstract class BaseDao<M extends BaseMapper<T>, T extends BaseDo<ID>, ID extends Serializable>
        extends ServiceImpl<M, T> {

    private final int DEFAULT_BATCH_SIZE = 1000;


    public PageResponse<T> page(PageRequest req, Consumer<LambdaQueryWrapper<T>> consumer) {
        return this.page(req, consumer, Function.identity());
    }

    public <R> PageResponse<R> page(int pageNumber, int pageSize, Consumer<LambdaQueryWrapper<T>> conditionConsumer
            , Sort sort, Function<T, R> convertFunc) {
        // page
        Page<T> pageReq = Page.of(pageNumber, pageSize);

        // condition
        LambdaQueryWrapper<T> queryWrapper = new LambdaQueryWrapper<>();

        // sort 转化为
        queryWrapper.orderBy();
    }

    public <R> PageResponse<R> page(PageRequest req, Consumer<LambdaQueryWrapper<T>> consumer, Function<T, R> mapper) {

        // page
        Page<T> pageReq = Page.of(req.getPageNumber(), req.getPageSize());

        // sort
        Sort sort = req.getSort();
        pageReq.addOrder(MpDataConverter.convert(sort));

        // condition
        LambdaQueryWrapper<T> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        consumer.accept(lambdaQueryWrapper);

        IPage<T> page = super.page(pageReq, lambdaQueryWrapper);


        return MpDataConverter.convert(page)
                .mapToPage(mapper);
    }

    public List<Integer> executeBatchUpdateWithRowCount(Collection<T> entities, int batchSize
            , Consumer<LambdaUpdateWrapper<T>> updateWrapperConsumer) {
        return null;
    }

    /**
     * 批量更新 ,条件和设置值都可以不一样
     * 原理：批量update语句，使用batch执行器会一批一批一起发送给jdbc组件（flush）
     *      如果jdbc连接配置了参数rewriteBatchedStatements=true,那就会打包一起发送给mysql，减少io交互
     * 类似sql update table set a = #{value} where b = #{value2},update table b set a = #{?} where b = #{?}
     */
    public boolean executeBatchUpdate(Collection<T> entities, int batchSize
            , Consumer<LambdaUpdateWrapper<T>> updateWrapperConsumer) {

        if (CollUtil.isNotEmpty(entities) || Objects.isNull(updateWrapperConsumer)) {
            return true;
        }

        String sqlStatement = this.getSqlStatement(SqlMethod.UPDATE);
        return super.executeBatch(entities, batchSize, (sqlSession, entity) -> {

            LambdaUpdateWrapper<T> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapperConsumer.accept(updateWrapper);

            // 构建参数 Map
            MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();

            // 这两个常量依然是 "et" 和 "ew" 对应baseMapper中
            // int update(@Param("et") T entity, @Param("ew") Wrapper<T> wrapper)
            param.put(Constants.ENTITY, entity);
            param.put(Constants.WRAPPER, updateWrapper);

            // 执行更新
            sqlSession.update(sqlStatement, param);
        });

    }


}
