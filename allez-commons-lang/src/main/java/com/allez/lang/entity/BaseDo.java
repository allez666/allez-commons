package com.allez.lang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: chenyu
 * @create: 2024-07-18 23:51
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDo<ID extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    public abstract ID getId();
    public abstract void setId(ID id);

}
