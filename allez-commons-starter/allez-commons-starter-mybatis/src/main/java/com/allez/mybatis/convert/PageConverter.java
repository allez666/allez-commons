package com.allez.mybatis.convert;

import com.allez.lang.entity.PageResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;

public final class PageConverter {
    private PageConverter() {
        // 私有构造，防止实例化
    }

    public static <T> PageResponse<T> convert(IPage<T> page) {
        return PageResponse.of(
                (int) page.getCurrent(), (int) page.getSize()
                , page.getTotal(), page.getRecords()
        );
    }

}
