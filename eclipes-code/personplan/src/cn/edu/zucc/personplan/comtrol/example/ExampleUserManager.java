package cn.edu.zucc.personplan.comtrol.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dom4j.util.UserDataAttribute;

import com.mysql.fabric.xmlrpc.base.Data;

import cn.edu.zucc.personplan.itf.IUserManager;
import cn.edu.zucc.personplan.model.BeanUser;
import cn.edu.zucc.personplan.util.*;


public class ExampleUserManager implements IUserManager {

	@Override//ע��
	public BeanUser reg(String userid, String pwd,String pwd2) throws BaseException {
		Connection conn=null;
		if(userid==null||pwd==null||pwd2==null)
			throw new BusinessException("�û������벻Ϊ��");
		if("".equals(userid)||"".equals(pwd)||"".equals(pwd2))
			throw new BusinessException("�û������벻Ϊ��");
		if(!pwd.equals(pwd2))
			throw new BusinessException("�������벻ͬ");
		try {
			conn=DBUtil.getConnection();
			String sql="select user_id from tbl_user where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				rs.close();
				pst.close();
				throw new BusinessException("�û��Ѵ���");
			}
			rs.close();
			pst.close();
			sql="insert into tbl_user(user_id,user_pwd,register_time) values(?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, pwd);
			pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.execute();
			BeanUser user=new BeanUser();
			user.setRegister_time(new Date());
			user.setUser_id(userid);
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
		return null;
	}

	
	
	@Override//��¼
	public BeanUser login(String userid, String pwd) throws BaseException {
		BeanUser user=null;
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_id,register_time from tbl_user where user_id=? and user_pwd=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, pwd);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				user=new BeanUser();
				user.setUser_id(rs.getString(1));
				//ʵ���һ�������
				//user.settime(rs.getTimestamp(2));
				java.text.SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time=sdf.format(rs.getTimestamp(2));
				Date date=sdf.parse(time);
				user.setRegister_time(date);
				
				
			}
			else {
				throw new BusinessException("�û������ڻ��������");
			}
			rs.close();
			pst.close();
		}catch(SQLException ex) {
			throw new DbException(ex);
		}catch (ParseException ex2) {
			// TODO Auto-generated catch block
			ex2.printStackTrace();
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
		return user;
	}


	@Override//�����޸�
	public void changePwd(BeanUser user, String oldPwd, String newPwd,
			String newPwd2) throws BaseException {
		if(oldPwd==null||newPwd==null)
			throw new BusinessException("����Ϊ��");
		if("".equals(oldPwd)||"".equals(newPwd))
			throw new BusinessException("����Ϊ��");
		if(!oldPwd.equals(newPwd))
			throw new BusinessException("�¾����벻ͬ");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="UPDATE tbl_user SET user_pwd=? WHERE user_pwd=? AND user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, newPwd);
			pst.setString(2, oldPwd);
			pst.setString(3, user.getUser_id());
			pst.execute();
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