package ${packageVo};

import ${packageDomain}.${fileDoName};
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @class: ${fileName}VO
 * @Description: ${moduleDesc} 实体类
 * @author: 系统
 * @created: ${date?string('yyyy-MM-dd')}
 */
@ApiModel(value="${fileName}Vo",description="${moduleDesc} 返回前端的实体")
@Data
public class ${fileName}Vo extends ${fileDoName} {

}