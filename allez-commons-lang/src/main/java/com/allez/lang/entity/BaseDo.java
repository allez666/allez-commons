package com.allez.lang.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class BaseDo<ID extends Serializable> implements Serializable {

    private ID id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    public static void main(String[] args) {
    }


}
