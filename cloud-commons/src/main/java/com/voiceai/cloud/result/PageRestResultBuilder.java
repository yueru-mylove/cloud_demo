package com.voiceai.cloud.result;


import lombok.Data;

/**
 * Created by liangliang on 2017/02/25.
 *
 * @author liangliang
 * @since 2017/02/25
 */
@Data
public class PageRestResultBuilder extends RestResultBuilder {

    private int pageNum;

    private int pageSize;

    private long pages;

    private int size;

    private long total;

    public static PageRestResultBuilder builder() {
        return new PageRestResultBuilder();
    }

    public <T> RestResultBuilder success(PageInfo<T> pageInfo) {
        success(pageInfo.getList());
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.pages = pageInfo.getPages();
        this.size = pageInfo.getSize();
        this.total = pageInfo.getTotal();
        return this;
    }

    @Override
    public RestResult build() {
        PageRestResult pageRestResult = new PageRestResult(this.code, this.msg, this.data);
        pageRestResult.setPageNum(this.pageNum);
        pageRestResult.setPageSize(this.pageSize);
        pageRestResult.setPages(this.pages);
        pageRestResult.setTotal(this.total);
        return pageRestResult;
    }
}
