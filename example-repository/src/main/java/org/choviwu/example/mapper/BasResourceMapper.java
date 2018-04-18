package org.choviwu.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.choviwu.example.common.model.BasResource;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BasResourceMapper extends Mapper<BasResource> {

    List<BasResource> getResourceListByRoleId(@Param("roleId") Integer roleId);

}