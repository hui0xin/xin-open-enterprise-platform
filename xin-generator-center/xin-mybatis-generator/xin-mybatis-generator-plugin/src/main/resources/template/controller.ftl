package ${packageController};

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ${packageService}.${fileName}Service;
import ${packageDomain}.${fileDoName};

/**
 * @class: ${fileName}Controller
 * @Description:  ${moduleDesc} Controller
 * @author: 系统
 * @created: ${date?string('yyyy-MM-dd')}
 */
@RestController
@RequestMapping("/${moduleName}")
@Api(value = "${moduleDesc} API", tags = {"${moduleDesc} api操作接口"})
public class ${fileName}Controller {

    @Autowired
    public ${fileName}Service ${fileName?uncap_first}Service;

}