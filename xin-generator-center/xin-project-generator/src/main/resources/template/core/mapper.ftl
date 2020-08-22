package com.${packageName}.core.mapper;

import com.${packageName}.bean.DO.DemoDo;
import java.util.List;
import org.apache.ibatis.annotations.*;

/**
 * @interface: DemoMapper
 * @Description:  描述
 *                具体的实现就不做了 这里可以通过 xin-mybatis-generator 直接生成
 *               （可以生成注解 ，也可以生成xml）
 * @author: 系统
 */
@Mapper
public interface DemoMapper {

    /**
     * @explain: 添加DemoDo对象
     * @param:   DemoDo 对象参数
     * @return:  int
     */
    int insertSelective(DemoDo model);

    /**
     * @explain: 删除DemoDo对象
     * @param:   id  对象参数
     * @return:  int
     */
    int deleteByPrimaryKey(Long id);

    /**
     * @explain: 修改DemoDo对象
     * @param:   DemoDo  对象参数
     * @return:  int
     */
    int updateByPrimaryKeySelective(DemoDo model);

    /**
     * @explain: 查询DemoDo对象
     * @param:   id  对象参数
     * @return:  DemoDo
     */
    DemoDo selectByPrimaryKey(Long id);

}
