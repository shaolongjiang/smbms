package com.shao.dao.user;

import com.mysql.jdbc.StringUtils;
import com.shao.dao.BaseDao;
import com.shao.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class  UserDaoImpl implements UserDao{

    //得到用户
    public User getLoginUser(Connection connection, String userCode,String password) throws  SQLException{
        PreparedStatement pstm=null;
        ResultSet resultSet=null;
        User user=null;
        if(connection!=null){
            String sql="select * from smbms_user where userCode=? and userPassword=?";
            Object[] params={userCode,password};
            resultSet= BaseDao.exectue(connection,pstm,resultSet,sql,params);
                if(resultSet.next()){
                user=new User();
                    user.setId(resultSet.getInt("id"));
                    user.setUserCode(resultSet.getString("userCode"));
                    user.setUserName(resultSet.getString("userName"));
                    user.setUserPassword(resultSet.getString("userPassword"));
                    user.setGender(resultSet.getInt("gender"));
                    user.setBirthday(resultSet.getDate("birthday"));
                    user.setPhone(resultSet.getString("phone"));
                    user.setAddress(resultSet.getString("address"));
                    user.setUserRole(resultSet.getInt("userRole"));
                    user.setCreatedBy(resultSet.getInt("createdBy"));
                    user.setCreationDate(resultSet.getTimestamp("creationDate"));
                    user.setModifyBy(resultSet.getInt("modifyBy"));
                    user.setModifyDate(resultSet.getTimestamp("modifyDate"));
                }
                BaseDao.closeResource(null,pstm,resultSet);
        }
        return user;
    }

    //修改密码
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        PreparedStatement pstm = null;
        int exectue=0;
        //System.out.println(id);
        //System.out.println(password);
        if(connection!=null) {
            String sql = "update smbms_user set userPassword=? where id=?";
            Object params[]={password,id};
            exectue=BaseDao.exectue(connection,pstm,sql,params);
            BaseDao.closeResource(connection,pstm,null);
        }
        return exectue;
    }


    public int getUserCount(Connection connection, String username, int userRole) throws SQLException {
        PreparedStatement pstm=null;
        ResultSet rs = null;
        int count = 0;
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");
            ArrayList<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(username)){
                sql.append(" and u.username like ?");
                list.add("%"+username+"%");
            }
            if(userRole>0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            Object[] params=list.toArray();
            System.out.println("UserDaoImpl->getUserCount:"+sql.toString());

           rs = BaseDao.exectue(connection,pstm,rs,sql.toString(),params);
           if(rs.next()){
               count = rs.getInt("count");
           }
           BaseDao.closeResource(null,pstm,rs);
        }
        return count;
    }

    //获取用户列表  通过条件查询userList
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> userList = new ArrayList<User>();
        if (connection!=null){
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();//存放我们的参数
            if (!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ?");
                list.add("%"+userName+"%");
            }
            if (userRole>0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);//index:1
            }
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql--->"+sql.toString());
            resultSet = BaseDao.exectue(connection,preparedStatement,resultSet,sql.toString(),params);
            while (resultSet.next()){
                User _user = new User();
                _user.setId(resultSet.getInt("id"));
                _user.setUserCode(resultSet.getString("userCode"));
                _user.setUserName(resultSet.getString("userName"));
                _user.setGender(resultSet.getInt("gender"));
                _user.setBirthday(resultSet.getDate("birthday"));
                _user.setPhone(resultSet.getString("phone"));
                _user.setUserRole(resultSet.getInt("userRole"));
                _user.setUserRoleName(resultSet.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDao.closeResource(null,preparedStatement,resultSet);
        }
        return userList;
    }

    //增加用户信息
    public int add(Connection connection, User user) throws SQLException{
        PreparedStatement preparedStatement = null;
        int updateRows = 0;
        if (connection!=null){
            String sql = "insert into smbms_user (userCode,userName,userPassword," +
                    "userRole,gender,birthday,phone,address,creationDate,createdBy) " +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {user.getUserCode(),user.getUserName(),user.getUserPassword(),
                    user.getUserRole(),user.getGender(),user.getBirthday(),
                    user.getPhone(),user.getAddress(),user.getCreationDate(),user.getCreatedBy()};
            updateRows = BaseDao.exectue(connection,preparedStatement,sql,params);
            BaseDao.closeResource(null,preparedStatement,null);
        }
        return updateRows;
    }
    //根据userId删除user
    public int deleteUserById(Connection connection,Integer delId) throws SQLException{
        PreparedStatement preparedStatement = null;
        int updateRows = 0;

        if (connection != null) {
            String sql = "delete from smbms_user where id = ?";
            Object[] params = {delId};
            updateRows = BaseDao.exectue(connection,preparedStatement,sql,params);
            BaseDao.closeResource(null,preparedStatement,null);
        }
        return updateRows;
    }
    //修改用户信息
    public int modify(Connection connection, User user) throws SQLException {
        PreparedStatement preparedStatement = null;
        int updateRows = 0;

        if (connection != null){
            String sql = "update smbms_user set userName=?," +
                    "gender=?,birthday=?,phone=?,address=?,userRole=?,modifyBy=?,modifyDate=? where id = ?";
            Object[] params = {user.getUserName(),user.getGender(),user.getBirthday(),user.getPhone(),
                    user.getAddress(),user.getUserRole(),user.getModifyBy(),user.getModifyDate(),user.getId()};

            updateRows = BaseDao.exectue(connection,preparedStatement,sql,params);
            BaseDao.closeResource(null,preparedStatement,null);
        }
        return updateRows;
    }
    //根据id获取user信息
    public User getUserById(Connection connection,String id) throws SQLException{
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        if (connection != null){
            String sql = "select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.id= ? and u.userRole = r.id";
            Object[] params = {id};
            resultSet = BaseDao.exectue(connection,preparedStatement,resultSet,sql,params);
            if (resultSet.next()){
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getTimestamp("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getTimestamp("modifyDate"));
                user.setUserRoleName(resultSet.getString("userRoleName"));
            }
            BaseDao.closeResource(null,preparedStatement,resultSet);
        }
        return user;
    }

    @Override
    public User getUserBycode(Connection connection, String userCode)  throws SQLException{
        PreparedStatement pstm = null;
        ResultSet resultSet = null;
        User user = null;
        if (connection != null){
            String sql="select * from smbms_user where userCode=?";
            Object[] params={userCode};
            resultSet= BaseDao.exectue(connection,pstm,resultSet,sql,params);
            if(resultSet.next()){
                user=new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getTimestamp("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(null,pstm,resultSet);
        }
        return user;
    }
}
