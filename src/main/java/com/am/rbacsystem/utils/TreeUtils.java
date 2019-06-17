package com.am.rbacsystem.utils;

import com.am.rbacsystem.common.TreeNode;
import com.am.rbacsystem.pojo.Auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeUtils {
    public static List<TreeNode> bulidCheckedTree(List<Auth> list,String authIds,Integer parentId){
        List<TreeNode> nodeList = new ArrayList();
        for(Auth auth : list){
            if(auth.getParentId().equals(parentId)){
                TreeNode node = new TreeNode();
                node.setId(auth.getAuthId());
                node.setIconCls(auth.getIconCls());
                node.setState(auth.getState());
                node.setText(auth.getAuthName());
                if(auth.getAuthPath() != null && !"".equals(auth.getAuthPath())){
                    Map attr = new HashMap<>();
                    attr.put("authPath",auth.getAuthPath());
                    node.setAttributes(attr);
                }
                if (authIds!=null){
                    String[] arr = authIds.split(",");
                    for(String s: arr){
                        if(auth.getAuthId().equals(Integer.parseInt(s))){
                            node.setChecked(true);
                        }
                    }
                }
                node.setChildren(bulidCheckedTree(list,authIds,auth.getAuthId()));
                nodeList.add(node);
            }


        }
        return nodeList;
    }

    public static List<Auth> bulidGridTree(List<Auth> list, int parentId) {
       List<Auth> nodeList=new ArrayList<>();
         for (Auth auth : list) {
             if (auth.getParentId()==parentId){
                 Auth node=new Auth();
                 node=auth;
                 node.setChildren(bulidGridTree(list,node.getAuthId()));
                 nodeList.add(node);
             }
        }
        return  nodeList;
    }
}
