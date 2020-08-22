package com.xin.commons.support.utils;

import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * description: PageSubUtils
 * date: 2019-12-09 11:12
 * author: hx
 * version: 1.0
 */
public class PageSubUtils<T> {

    /**
     * 利用subList方法进行分页
     *
     * @param list      分页数据
     * @param pageSize  每页显示多少条
     * @param pageIndex 当前第几页
     */
    public List<T> pageBySubList(List<T> list, int pageSize, int pageIndex) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (pageIndex <= 0) {
            pageIndex = 1;
        }
        //每页的起始索引
        int pageNo = (pageIndex - 1) * pageSize;
        if (pageNo >= list.size()) {
            return null;
        }
        //记录总数
        Integer sum = list.size();
        //结束索引
        Integer endIndex = pageNo + pageSize;
        if (endIndex > sum) {
            list = list.subList(pageNo, sum);
        } else {
            list = list.subList(pageNo, endIndex);
        }
        return list;
    }


    /**
     * isLastPage 计算是不是最后一页
     *
     * @param pageSize  每页显示多少条
     * @param pageIndex 当前第几页
     * @param total     总条数
     */
    public static boolean isLastPage(int pageSize, int pageIndex, int total) {
        Boolean result = false;
        if (pageIndex <= 0) {
            pageIndex = 1;
        }
        //总页数
        int totalPage = total / pageSize;
        if (total % pageSize != 0) {
            totalPage = totalPage + 1;
        }
        if (pageIndex >= totalPage) {
            result = true;
        }
        return result;
    }
}