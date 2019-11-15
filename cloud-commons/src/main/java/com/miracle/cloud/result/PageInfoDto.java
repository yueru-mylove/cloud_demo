package com.miracle.cloud.result;


import lombok.Data;

import java.io.Serializable;

/**
 * Created by liangliang on 2017/02/25.
 *
 * @author liangliang
 * @since 2017/02/25
 */
@Data
public class PageInfoDto implements Serializable {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private String orderColumn;
    /**
     * asc ,desc
     */
    private String dir;


    @Override
    public String toString() {
        return "PageInfoDto{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", orderColumn='" + orderColumn + '\'' +
                ", dir='" + dir + '\'' +
                '}';
    }
}
