package com.allez.mybatis.test;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class TestServiceImpl extends ServiceImpl<TestEntityMapper,TestEntity> implements ITestService {
}
