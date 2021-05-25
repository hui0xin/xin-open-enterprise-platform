package ${packageImpl};

import ${packageDomain}.${fileDoName};
import ${packageMapper}.${fileName}Mapper;
import ${packageService}.${fileName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * @class: ${fileName}ServiceImpl
 * @Description:  ${moduleDesc} 接口实现
 * @author: 系统
 * @created: ${date?string('yyyy-MM-dd')}
 */
@Slf4j
@Service
public class ${fileName}ServiceImpl implements ${fileName}Service {

    @Autowired
    private ${fileName}Mapper ${fileName?uncap_first}Mapper;

    /**
     * @explain: 添加${fileDoName}对象
     * @param:   model 对象参数
     * @return:  int
     */
    @Override
    public int insert(${fileDoName} model) {
        return ${fileName?uncap_first}Mapper.insert(model);
    }

    /**
     * @explain: 删除${fileDoName}对象
     * @param:   id
     * @return:  int
     */
    @Override
    public int deleteById(Long id) {
        return ${fileName?uncap_first}Mapper.deleteByPrimaryKey(id);
    }

    /**
     * @explain: 修改${fileDoName}对象
     * @param:   model 对象参数
     * @return:  int
     */
    @Override
    public int update(${fileDoName} model) {
        return ${fileName?uncap_first}Mapper.updateByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询${fileDoName}对象
     * @param:   id
     * @return:  ${fileDoName}
     */
    @Override
    public ${fileDoName} selectById(Long id) {
        return ${fileName?uncap_first}Mapper.selectByPrimaryKey(id);
    }

    /**
     * @explain: 查询${fileDoName}对象
     * @param:   model 对象参数
     * @return:  ${fileDoName} 对象
     */
    @Override
    public ${fileDoName} selectByObject(${fileDoName} model) {
        return ${fileName?uncap_first}Mapper.selectByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询列表
     * @param:  model  对象参数
     * @return: list
     */
    @Override
    public List<${fileDoName}> listByObject(${fileDoName} model) {
        return ${fileName?uncap_first}Mapper.selectByPrimaryKeySelectiveList(model);
    }

}
