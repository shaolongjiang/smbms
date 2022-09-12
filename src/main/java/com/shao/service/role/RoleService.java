package com.shao.service.role;

import java.sql.SQLException;
import java.util.List;
import com.shao.pojo.Role;
public interface RoleService {
    //获取角色列表
    public List<Role> getRoleList() throws SQLException;
}
