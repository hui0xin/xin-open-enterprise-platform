package com.xin.exchange.etp.core.service.impl;

import com.xin.exchange.etp.bean.DO.DemoDo;
import com.xin.exchange.etp.bean.bo.DemoBo;
import com.xin.exchange.etp.core.mapper.DemoMapper;
import com.xin.exchange.etp.core.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @class: DemoServiceImpl
 * @Description: 这里可以通过 xin-mybatis-generator 直接生成
 * @author: 系统
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoMapper demoMapper;

    /**
     * @explain: 添加DemoDo对象
     * @param:   demoDo 对象参数
     * @return:  int
     */
    @Override
    public int insertSelective(DemoDo demoDo) {
        return demoMapper.insertSelective(demoDo);
    }

    /**
     * @explain: 删除DemoDo对象
     * @param:   id
     * @return:  int
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        return demoMapper.deleteByPrimaryKey(id);
    }

    /**
     * @explain: 修改DemoDo对象
     * @param:   demoDo  对象参数
     * @return:  int
     */
    @Override
    public int updateByPrimaryKeySelective(DemoDo demoDo) {
        return demoMapper.updateByPrimaryKeySelective(demoDo);
    }

    /**
     * @explain: 查询DemoDo对象
     * @param:   id
     * @return:  DemoDo
     */
    @Override
    public DemoDo selectByPrimaryKey(Long id) {
        return demoMapper.selectByPrimaryKey(id);
    }

    /**
     * @explain: 根据条件查询
     * @param:   demoBo 查询条件
     */
    @Override
    public List<DemoDo> selectByPrimaryList(DemoBo demoBo){
        //具体的逻辑结合需求去写
        List<DemoDo> list = null;

        return list;
    }

    /**
     * @explain: 查询 分页 demo
     * @param:   demoBo 查询条件
     * @param:   pageIndex  第几页
     * @param:   pageSize  每页显示多少条
     */
    @Override
    public PageInfo<DemoDo> selectByPrimaryPage(DemoBo demoBo, Integer pageIndex, Integer pageSize) {
        Page<DemoDo> page = PageHelper.startPage(pageIndex, pageSize);
        //具体的sql请自行去写，不用关心 pageIndex和pageSize mybatis会帮你在查询之前拼接起来
        List<DemoDo> modelList = null;
        PageInfo<DemoDo> pageInfo = new PageInfo(page.getResult());
        return pageInfo;
    }
}
