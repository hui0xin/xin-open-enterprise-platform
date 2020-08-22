package ${packageService};

import ${packageDomain}.${fileDoName};
import java.util.List;

/**
 * @interface: ${fileName}Service
 * @Description:  ${moduleDesc} 接口
 * @author: huixin
 * @created: ${date?string('yyyy-MM-dd')}
 */
public interface ${fileName}Service {

    /**
     * @explain: 添加${fileDoName}对象
     * @param:   ${fileDoName} 对象参数
     * @return:  int
     */
    int insertSelective(${fileDoName} model);

    /**
     * @explain: 删除${fileDoName}对象
     * @param:   id  对象参数
     * @return:  int
     */
    int deleteByPrimaryKey(Long id);

    /**
     * @explain: 修改${fileDoName}对象
     * @param:   ${fileDoName}  对象参数
     * @return:  int
     */
    int updateByPrimaryKeySelective(${fileDoName} model);

    /**
     * @explain: 查询${fileDoName}对象
     * @param:   id  对象参数
     * @return:  ${fileDoName}
     */
    ${fileDoName} selectByPrimaryKey(Long id);

}
