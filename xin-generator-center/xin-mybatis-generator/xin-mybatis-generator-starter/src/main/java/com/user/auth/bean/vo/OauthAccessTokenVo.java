package com.user.auth.bean.vo;

import com.user.auth.bean.DO.OauthAccessTokenDo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @class: OauthAccessTokenVO
 * @Description: oauth访问令牌 实体类
 * @author: huixin
 * @created: 2021-03-06
 */
@ApiModel(value="OauthAccessTokenVo",description="oauth访问令牌 返回前端的实体")
@Data
public class OauthAccessTokenVo extends OauthAccessTokenDo {

}