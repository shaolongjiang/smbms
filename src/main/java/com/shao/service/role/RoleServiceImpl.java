package com.shao.service.role;


import com.shao.dao.BaseDao;
import com.shao.dao.role.RoleDao;
import com.shao.dao.role.RoleDaoImpl;
import com.shao.pojo.Role;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService{

    //引入Dao
    private RoleDao roleDao;

    public RoleServiceImpl(){
        roleDao = new RoleDaoImpl();
    }


    public List<Role> getRoleList() {

        Connection connection = null;
        List<Role> roleList = null;

        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
        return roleList;
    }

//    @Test
//    public void test(){
//        RoleServiceImpl roleService = new RoleServiceImpl();
//        List<Role> roleList = roleService.getRoleList();
//        for (Role role:roleList){
//            System.out.println(role.getRoleName());
//        }
//    }

}

