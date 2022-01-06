package com.xin.security.oauth2.core.mapper;

import com.xin.security.oauth2.bean.DO.RoleDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface RoleMapper {

    @Select( "SELECT A.id,A.name FROM role A LEFT JOIN user_role B ON A.id=B.role_id WHERE B.user_id=${userId}" )
    List<RoleDo> getRolesByUserId(@Param("userId") Long userId);

}
