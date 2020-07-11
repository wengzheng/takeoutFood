package cn.edu.zucc.takeoutfood.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.BusinessException;
import cn.edu.zucc.takeoutfood.util.DBUtil;
import cn.edu.zucc.takeoutfood.util.DbException;
import cn.edu.zucc.takeoutfood.model.BeanAdministrator;
import cn.edu.zucc.takeoutfood.model.BeanSystemUser;
import cn.edu.zucc.takeoutfood.model.BeanUser;


public class UserManager {

	public List<BeanUser> loadAllUsers(boolean withnotdel) throws BaseException{
		Connection conn=null;
		List<BeanUser> result=new ArrayList<BeanUser>();
		try {
			conn=DBUtil.getConnection();
			String sql="select useraccount,username,userpwd from users";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				BeanUser bu=new BeanUser();
				bu.setUserid(rs.getString(1));
				bu.setUsername(rs.getString(2));
				bu.setUpwd(rs.getString(3));
				result.add(bu);
			}
			rs.close();
			st.close();
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}


	public void resetUserPwd(String userid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select userID from users where useraccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			int n;
			if(rs.next()) 
				n=rs.getInt(1);
			else	
				throw new BusinessException("登陆账号不存在");
			rs.close();
			pst.close();
			sql="UPDATE users SET userpwd=? where userID=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, "000000");
			pst.setInt(2, n);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}

	public void deleteUser(String userid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT userID FROM users WHERE useraccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			int n;
			if(rs.next()) { 
				n=rs.getInt(1);
			}else	
				throw new BusinessException("登陆账号不存在");
			rs.close();
			pst.close();
			sql="delete from users where userID=? ";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,n);
			pst.execute();
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}

	public BeanUser loadUser(String userid)throws BaseException{
		BeanUser bu=new BeanUser();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT userID,useraccount,username,userpwd FROM users WHERE useraccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				bu.setpNUM(rs.getInt(1));
				bu.setUserid(rs.getString(2));
				bu.setUsername(rs.getString(3));
				bu.setUpwd(rs.getString(4));
			}	
			else
				throw new BusinessException("登陆账号不存在");
			rs.close();
			pst.close();
			return bu;
		} catch (SQLException e) {
			throw new DbException(e);	
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}


	public void createUser(BeanUser user) throws BaseException{
		Connection conn=null;
		if ("".equals(user.getUserid())) {
			throw new BusinessException("账号为空");}
		if("".equals(user.getUsername())) {
			throw new BusinessException("用户名为空");}
		if("".equals(user.getUsex())) {
			throw new BusinessException("性别为空");}
		if("".equals(user.getUpwd())) {	
			throw new BusinessException("密码为空");}
		if("".equals(user.getPhone())) {
			throw new BusinessException("电话为空");}
		if("".equals(user.getEmail())) {	
			throw new BusinessException("邮箱为空");}
		if("".equals(user.getAddress())) {
			throw new BusinessException("地址为空");}
		try {
			conn=DBUtil.getConnection();
			String sql="select useraccount from users where useraccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, user.getUserid());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				rs.close();
				pst.close();
				throw new BusinessException("用户已存在");
			}
			sql="select max(userID) from users";
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			int n = 0;
			if(rs.next())
				n=rs.getInt(1);
			rs.close();
			pst.close();
			sql="INSERT users(userID,adminID,useraccount,username,usex,userpwd,phone,email,address,registertime) VALUES (?,?,?,?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,(n+1));
			pst.setInt(2, SystemUserManager.currentUser.getSystemNUM());
			pst.setString(3, user.getUserid());
			pst.setString(4, user.getUsername());
			pst.setString(5, user.getUsex());
			pst.setString(6, user.getUpwd());
			pst.setString(7, user.getPhone());
			pst.setString(8, user.getEmail());
			pst.setString(9, user.getAddress());
			pst.setTimestamp(10, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.execute();
			pst.close();
		}catch(SQLException ex) {
			throw new DbException(ex);
		}
		finally {
			if(conn!=null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	}
}
