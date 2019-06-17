package com.am.rbacsystem.dao;

import com.am.rbacsystem.pojo.User;
import com.am.rbacsystem.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User findUserByUsername(String userName);

    List<User> findUserByPage(@Param("currentPage") Integer currentPage, @Param("pageSize") Integer pageSize, @Param("vo") UserVo vo);

    int deleteUsers(Integer[] usersId);
}