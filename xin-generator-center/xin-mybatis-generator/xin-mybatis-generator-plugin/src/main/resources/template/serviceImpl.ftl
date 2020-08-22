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
 * @author: huixin
 * @created: ${date?string('yyyy-MM-dd')}
 */
@Slf4j
@Service
public class ${fileName}ServiceImpl implements ${fileName}Service {

    @Autowired
    private ${fileName}Mapper ${fileName?uncap_first}Mapper;

    /**
     * @explain: 添加${fileDoName}对象
     * @param:   ${fileDoName} 对象参数
     * @return:  int
     */
    @Override
    public int insertSelective(${fileDoName} model) {
        return ${fileName?uncap_first}Mapper.insertSelective(model);
    }

    /**
     * @explain: 删除${fileDoName}对象
     * @param:   id  对象参数
     * @return:  int
     */
    @Override
    public int deleteByPrimaryKey(Long id) {
        return ${fileName?uncap_first}Mapper.deleteByPrimaryKey(id);
    }

    /**
     * @explain: 修改${fileDoName}对象
     * @param:   ${fileDoName}  对象参数
     * @return:  int
     */
    @Override
    public int updateByPrimaryKeySelective(${fileDoName} model) {
        return ${fileName?uncap_first}Mapper.updateByPrimaryKeySelective(model);
    }

    /**
     * @explain: 查询${fileDoName}对象
     * @param:   id  对象参数
     * @return:  ${fileDoName}
     */
    @Override
    public ${fileDoName} selectByPrimaryKey(Long id) {
        return ${fileName?uncap_first}Mapper.selectByPrimaryKey(id);
    }

}
