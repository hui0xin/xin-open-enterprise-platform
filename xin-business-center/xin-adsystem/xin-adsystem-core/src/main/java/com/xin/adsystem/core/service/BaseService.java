package com.xin.adsystem.core.service;

import java.util.List;

/**
 * ${DESCRIPTION}
 */
public interface BaseService<T> {
    /**
     * 查询
     *
     * @param entity
     * @return
     */
    T selectOne(T entity);

    /**
     * 通过Id查询
     *
     * @param id
     * @return
     */
    T selectById(Object id);

    /**
     * 根据ID集合来查询
     *
     * @param ids
     * @return
     */
    List<T> selectListByIds(List<Object> ids);

    /**
     * 查询列表
     *
     * @param entity
     * @return
     */
    List<T> selectList(T entity);

    /**
     * 获取所有对象
     *
     * @return
     */
    List<T> selectListAll();

    /**
     * 查询总记录数
     *
     * @param entity
     * @return
     */
    Long selectCount(T entity);

    /**
     * 添加
     *
     * @param entity
     */
    void insert(T entity);


    /**
     * 插入 不插入null字段
     *
     * @param entity
     */
    int insertSelective(T entity);

    /**
     * 删除
     *
     * @param entity
     */
    int delete(T entity);

    /**
     * 根据Id删除
     *
     * @param id
     */
    int deleteById(Object id);

    /**
     * 根据id更新
     *
     * @param entity
     */
    int updateById(T entity);

    /**
     * 不update null
     *
     * @param entity
     */
    int updateSelectiveById(T entity);

}
