package com.allez.mybatis.test;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestEntityMapper extends BaseMapper<TestEntity> {
}
