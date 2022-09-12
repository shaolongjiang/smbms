package com.shao.dao.role;

import com.shao.pojo.Role;

import java.sql.Connection;
import java.util.List;

public interface RoleDao {
    //获取角色信息
    public List<Role> getRoleList(Connection connection) throws Exception;
}
