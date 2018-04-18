package org.choviwu.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.choviwu.example.common.model.BasUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BasUserMapper extends Mapper<BasUser> {

    BasUser getUserByphone(@Param("phone") String  phone);

    BasUser getUserByUserName(@Param("username")String username);

    BasUser getNameAndPassword(Map map);//@Param("mobile")String mobile,@Param("password")String password);

}