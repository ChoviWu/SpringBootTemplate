package org.choviwu.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.choviwu.example.common.model.BasRole;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BasRoleMapper extends Mapper<BasRole> {

    List<BasRole> getRoleByUserId(Integer userId);

    BasRole getOneByRoleName(@Param("roleName") String roleName);

}