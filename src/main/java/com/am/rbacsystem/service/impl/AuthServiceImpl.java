package com.am.rbacsystem.service.impl;

import com.am.rbacsystem.constant.SysConstant;
import com.am.rbacsystem.dao.AuthMapper;
import com.am.rbacsystem.pojo.Auth;
import com.am.rbacsystem.pojo.User;
import com.am.rbacsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AuthServiceImpl  implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> findAuthByParentId(User user, Integer parentId) {
        return authMapper.findAuthByParentId(user.getUserId(),parentId);
    }

    @Override
    public List<Auth> findAllAuth(Integer parentId) {
        return authMapper.findAllAuth(parentId);
    }

    @Override
    public List<Auth> findAll() {
        return authMapper.findAll();
    }

    @Override
    public Auth findAuthByName(String authName) {
        return authMapper.findAuthByName(authName);
    }

    @Override
    public void addAuth(Auth auth) {
        try {
            //1.修改父节点的状态为closed
            authMapper.updateState(auth.getParentId(), SysConstant.NODE_CLOSED_STATES);
            //2.保存节点,默认为open
            auth.setState(SysConstant.NODE_OPEN_STATES);
            authMapper.insertSelective(auth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateAuth(Auth auth) {
        return authMapper.updateByPrimaryKeySelective(auth);
    }

    @Override
    public void deleteAuth(Auth auth) {
        try {
            //1.判断他的夫节点有多少个子节点
            List<Auth> list=authMapper.findAuthByParentId2(auth.getParentId());
            if (list.size()==1){//只有自己一个
                //1.修改父节点的状态为open
                authMapper.updateState(auth.getParentId(), SysConstant.NODE_OPEN_STATES);
            }
            //2.删除自己
            authMapper.deleteByPrimaryKey(auth.getAuthId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Auth findAuthByNamePerms(Auth auth) {
        return authMapper.findAuthByNamePerms(auth);
    }

    @Override
    public List<Auth> findChidrenByAuthId(Integer authId) {
        return authMapper.findAuthByParentId2(authId);
    }

    @Override
    public List<String> findPermsByUserName(String userName) {
        return authMapper.findPermsByUserName(userName);
    }


}
