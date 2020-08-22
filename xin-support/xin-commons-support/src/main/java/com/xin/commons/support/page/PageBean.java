package com.xin.commons.support.page;

import java.util.List;

/**
 * 分页查询结果类
 *
 * @param <T>
 * @date 2017-12-28
 */
public final class PageBean<T> {

    //第几页
    private int page = 1;
    //一页显示多少条
    private int size = 10;
    private String order;

    private int totalCount = 0;
    private List<T> content;

    public PageBean() {

    }

    public PageBean(int page, int size, String order) {
        this.page = page;
        this.size = size;
        this.order = order;
    }

    public PageBean(int totalCount, List<T> content) {
        this.totalCount = totalCount;
        this.content = content;
    }

    /**
     * 获得总页数
     *
     * @return
     */
    public long getTotalPage() {
        if (totalCount % size > 0) {
            return totalCount / size + 1;
        } else {
            return totalCount / size;
        }
    }

    /**
     * 获取从哪一条记录开始查询
     *
     * @return
     */
    public int getStart() {
        return (page - 1) * size;
    }

    /**
     * 获取最后一条记录的下标数（不包含）
     *
     * @return
     */
    public int getEnd() {
        return getStart() + size;
    }

    /**
     * 判断是否还有下一页
     *
     * @return
     */
    public boolean hasNextPage() {
        return (page + 1) <= getTotalPage();
    }

    /**
     * 获取下一个页码
     *
     * @return
     */
    public int getNextPage() {
        return page + 1;
    }

    /**
     * 判断是否还有上一页
     *
     * @return
     */
    public boolean hasPrevPage() {
        return (page - 1) > 0;
    }

    /**
     * 获取上一个页码
     *
     * @return
     */
    public int getPrevPage() {
        return page - 1;
    }

    public int getPage() {
        return page;
    }

    public PageBean<T> setPage(int page) {
        this.page = page;
        return this;
    }

    public int getSize() {
        return size;
    }

    public PageBean<T> setSize(int size) {
        this.size = size;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public PageBean<T> setOrder(String order) {
        this.order = order;
        return this;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public PageBean<T> setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        return this;
    }

    public List<T> getContent() {
        return content;
    }

    public PageBean<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return "PageBean{" + "page=" + page + ", size=" + size + ", order='" + order + '\'' +
                ", totalCount=" + totalCount + ", content=" + content + '}';
    }

}
