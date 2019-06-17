package com.am.rbacsystem.service;

import com.am.rbacsystem.pojo.Auth;
import com.am.rbacsystem.pojo.User;

import java.util.List;

public interface AuthService {
    List<Auth> findAuthByParentId(User user, Integer parentId);

    List<Auth> findAllAuth(Integer parentId);

    List<Auth> findAll();


    Auth findAuthByName(String authName);

    void addAuth(Auth auth);

    int updateAuth(Auth auth);

    void deleteAuth(Auth auth);

    Auth findAuthByNamePerms(Auth auth);

    List<Auth> findChidrenByAuthId(Integer authId);

    List<String> findPermsByUserName(String userName);
}
