package com.allez.mybatis.test;

import com.allez.lang.entity.BaseDo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("test_entity")
@Data
public class TestEntity extends BaseDo<Long> {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer age;

}
