package com.am.rbacsystem.dao;

import com.am.rbacsystem.pojo.Auth;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AuthMapper {
    int deleteByPrimaryKey(Integer authId);

    int insert(Auth record);

    int insertSelective(Auth record);

    Auth selectByPrimaryKey(Integer authId);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

    List<Auth> findAuthByParentId(@Param("userId") Integer userId, @Param("parentId") Integer parentId);

    List<Auth> findAllAuth(Integer parentId);

    List<Auth> findAll();

    @Select("select * from t_auth where auth_name=#{authName}")
    Auth findAuthByName(String authName);

    @Update("update t_auth set state=#{state} where auth_id=#{parentId}")
    void updateState(@Param("parentId") Integer parentId, @Param("state") String state);

    @Select("select * from t_auth where parent_id=#{parentId}")
    List<Auth> findAuthByParentId2(Integer parentId);

    Auth findAuthByNamePerms(Auth auth);

    List<String> findPermsByUserName(String userName);
}