package com.shao.dao.role;

import com.shao.dao.BaseDao;
import com.shao.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao {
    //获取角色信息
    public List<Role> getRoleList(Connection connection) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Role> roleList = new ArrayList<Role>();
        if (connection!=null){
            String sql="select * from smbms_role";
            Object[] params ={};
            System.out.println("sql--->"+sql);
            resultSet = BaseDao.exectue(connection,preparedStatement,resultSet,sql.toString(),params);
            while (resultSet.next()){
                Role _role = new Role();
                _role.setId(resultSet.getInt("id"));
                _role.setRoleCode(resultSet.getString("roleCode"));
                _role.setRoleName(resultSet.getString("roleName"));
                roleList.add(_role);
//                System.out.println("输出");
//                String rn = _role.getRoleName();
//                System.out.println(rn);
            }
            BaseDao.closeResource(null,preparedStatement,resultSet);
        }
        return roleList;
    }
}
