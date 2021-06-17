package com.xin.oauth2.controller;

import com.github.pagehelper.PageInfo;
import com.xin.commons.support.response.ResponseResult;
import com.xin.oauth2.bean.DO.OauthClientDetails;
import com.xin.oauth2.core.service.OauthClientDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags="oauth客户端信息")
@RestController
@RequestMapping("/oauth")
public class OauthClientDetailsController {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    @ApiOperation(value = "查询oauth客户端信息列表", notes = "查询oauth客户端信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "第几页", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示多少条", dataType = "Integer", paramType = "query")
    })
    @GetMapping("/list")
    public ResponseResult<PageInfo<OauthClientDetails>> list(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        PageInfo<OauthClientDetails> page = oauthClientDetailsService.getList(pageIndex,pageSize);
        return ResponseResult.success(page);
    }

    @ApiOperation(value = "根据clientId查询oauth客户端信息", notes = "根据clientId查询oauth客户端信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", value = "客户端id", dataType = "String", paramType = "query")
    })
    @GetMapping("/getByClientId")
    public ResponseResult<OauthClientDetails> getByClientId(@RequestParam(value = "clientId") String clientId) {
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.getByClientId(clientId);
        return ResponseResult.success(oauthClientDetails);
    }

    @ApiOperation(value = "添加oauth客户端信息", notes = "添加oauth客户端信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "model", value = "OauthClientDetailsDo类型实体", required = true, dataType = "OauthClientDetailsDo", paramType = "query")
    })
    @PostMapping("/save")
    public ResponseResult save(@RequestBody OauthClientDetails model) {
        String clientId = model.getClientId();
        if(StringUtils.isBlank(clientId)){
            return ResponseResult.failure("");
        }
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.getByClientId(clientId);
        if(!ObjectUtils.isEmpty(oauthClientDetails)){
            return ResponseResult.failure("");
        }
        oauthClientDetailsService.insertSelective(model);
        return ResponseResult.success("保存成功");
    }

    @ApiOperation(value = "修改oauth客户端信息",notes = "修改oauth客户端信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "model", value = "OauthClientDetailsDo类型实体", required = true, dataType = "OauthClientDetailsDo", paramType = "query")
    })
    @PostMapping("/update")
    public ResponseResult update(@RequestBody OauthClientDetails model) {
        String clientId = model.getClientId();
        if(StringUtils.isBlank(clientId)){
            return ResponseResult.failure("");
        }
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.getByClientId(clientId);
        if(ObjectUtils.isEmpty(oauthClientDetails)){
            return ResponseResult.failure("");
        }
        oauthClientDetailsService.updateByPrimaryKeySelective(model);
        return ResponseResult.success("修改成功");
    }

    @ApiOperation(value = "物理删除oauth客户端信息",notes = "物理删除oauth客户端信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", value = "客户端id", dataType = "String", paramType = "query")
    })
    @PostMapping("/delete")
    public ResponseResult delete(@RequestParam(value = "clientId") String clientId) {
        if(StringUtils.isBlank(clientId)){
            return ResponseResult.failure("");
        }
        oauthClientDetailsService.deleteByClientId(clientId);
        return ResponseResult.success("删除成功");
    }

}
