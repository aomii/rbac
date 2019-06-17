package com.am.rbacsystem.service;

import com.am.rbacsystem.pojo.User;
import com.am.rbacsystem.vo.UserVo;

import java.util.List;

public interface UserService {
    User findUserByUsername(User user);

    List<User> findUserByPage(Integer currentPage, Integer pageSize, UserVo vo);

    User checkUser(String userName);

    Integer addUser(User user);

    int deleteUsers(Integer[] usersId);

    int updateUser(User user);
}
