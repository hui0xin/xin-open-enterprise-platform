package com.xin.security.oauth2.core.service;

import com.github.pagehelper.PageInfo;
import com.xin.security.oauth2.bean.DO.DemoDo;
import com.xin.security.oauth2.bean.bo.DemoBo;
import java.util.List;

/**
 * @interface: DemoService
 * @Description:  这里可以通过 xin-mybatis-generator 直接生成
 * @author: 系统
 */
public interface DemoService {

    /**
     * @explain: 添加DemoDo对象
     * @param:   demoDo 对象参数
     * @return:  int
     */
    int insertSelective(DemoDo demoDo);

    /**
     * @explain: 删除DemoDo对象
     * @param:   id
     * @return:  int
     */
    int deleteByPrimaryKey(Long id);

    /**
     * @explain: 修改DemoDo对象
     * @param:   demoDo  对象参数
     * @return:  int
     */
    int updateByPrimaryKeySelective(DemoDo demoDo);

    /**
     * @explain: 查询DemoDo对象
     * @param:   id
     * @return:  DemoDo
     */
    DemoDo selectByPrimaryKey(Long id);

    /**
     * @explain: 根据条件查询
     * @param:   demoBo 查询条件
     */
    List<DemoDo> selectByPrimaryList(DemoBo demoBo);

    /**
     * @explain: 查询 分页 demo
     * @param:   demoBo 查询条件
     * @param:   pageIndex  第几页
     * @param:   pageSize  每页显示多少条
     */
     PageInfo<DemoDo> selectByPrimaryPage(DemoBo demoBo, Integer pageIndex, Integer pageSize);
}