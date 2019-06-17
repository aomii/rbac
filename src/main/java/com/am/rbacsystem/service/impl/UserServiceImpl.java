package com.am.rbacsystem.service.impl;

import com.am.rbacsystem.dao.UserMapper;
import com.am.rbacsystem.pojo.User;
import com.am.rbacsystem.service.UserService;
import com.am.rbacsystem.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUsername(User user) {
        User dbUser=userMapper.findUserByUsername(user.getUserName());
        if (dbUser!=null && dbUser.getPassword().equals(user.getPassword())){
            return dbUser;
        }
        return null;
    }

    @Override
    public List<User> findUserByPage(Integer currentPage, Integer pageSize, UserVo vo) {
        return userMapper.findUserByPage(currentPage,pageSize,vo);
    }

    @Override
    public User checkUser(String userName) {
        return userMapper.findUserByUsername(userName);
    }

    @Override
    public Integer addUser(User user) {
        return userMapper.insertSelective(user);
    }

    @Override
    public int deleteUsers(Integer[] usersId) {
        return userMapper.deleteUsers(usersId);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
