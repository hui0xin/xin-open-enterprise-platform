package ${packageService};

import ${packageDomain}.${fileDoName};
import java.util.List;

/**
 * @interface: ${fileName}Service
 * @Description:  ${moduleDesc} 接口
 * @author: 系统
 * @created: ${date?string('yyyy-MM-dd')}
 */
public interface ${fileName}Service {

    /**
     * @explain: 添加${fileDoName}对象
     * @param:   model 对象参数
     * @return:  int
     */
    int insert(${fileDoName} model);

    /**
     * @explain: 删除${fileDoName}对象
     * @param:   id
     * @return:  int
     */
    int deleteById(Long id);

    /**
     * @explain: 修改${fileDoName}对象
     * @param:   model 对象参数
     * @return:  int
     */
    int update(${fileDoName} model);

    /**
     * @explain: 查询${fileDoName}对象
     * @param:   id
     * @return:  ${fileDoName}
     */
    ${fileDoName} selectById(Long id);

    /**
     * @explain: 查询${fileDoName}对象
     * @param:   model  对象参数
     * @return:  ${fileDoName} 对象
     */
    ${fileDoName} selectByObject(${fileDoName} model);

    /**
     * @explain: 查询列表
     * @param:  model  对象参数
     * @return: list
     */
    List<${fileDoName}> listByObject(${fileDoName} model);

}
