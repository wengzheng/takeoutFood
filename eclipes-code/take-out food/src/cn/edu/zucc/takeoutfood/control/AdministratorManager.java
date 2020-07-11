package cn.edu.zucc.takeoutfood.control;

import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.omg.CORBA.PERSIST_STORE;

import cn.edu.zucc.takeoutfood.util.BusinessException;
import cn.edu.zucc.takeoutfood.util.DBUtil;
import cn.edu.zucc.takeoutfood.util.DbException;
import cn.edu.zucc.takeoutfood.model.BeanAdministrator;
import cn.edu.zucc.takeoutfood.model.BeanUser;
import cn.edu.zucc.takeoutfood.util.BaseException;

public class AdministratorManager {

	public BeanAdministrator loadAdmins(String userid) throws BaseException{
		BeanAdministrator ba=new BeanAdministrator();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select adminID,adminaccount,adminname,adminpwd from admin where adminaccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				ba.setaNUM(rs.getInt(1));;
				ba.setAdminid(rs.getString(2));
				ba.setAdminname(rs.getString(3));
				ba.setAPwd(rs.getString(4));
			}	
			else
				throw new BusinessException("µÇÂ½ÕËºÅ²»´æÔÚ");
			rs.close();
			pst.close();
			return ba;
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
	
	public List<BeanAdministrator> loadAllUsers(boolean withnotdel) throws BaseException{
		Connection conn=null;
		List<BeanAdministrator> result=new ArrayList<BeanAdministrator>();
		try {
			conn=DBUtil.getConnection();
			String sql="select adminaccount,adminname,adminpwd from admin";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				BeanAdministrator ba=new BeanAdministrator();
				ba.setAdminid(rs.getString(1));
				ba.setAdminname(rs.getString(2));
				ba.setAPwd(rs.getString(3));
				result.add(ba);
			}
			rs.close();
			st.close();
			BeanAdministrator ba=new BeanAdministrator();
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

	public void ResetAdminPwd(String userid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select adminID from admin where adminaccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			int n;
			if(rs.next()) 
				n=rs.getInt(1);
			else	
				throw new BusinessException("µÇÂ½ÕËºÅ²»´æÔÚ");
			rs.close();
			pst.close();
			sql="UPDATE admin SET adminpwd=? where adminID=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, "111111");
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


	public void deleteAdmin(String userid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select adminID from admin where adminaccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			int n;
			if(rs.next()) { 
				n=rs.getInt(1);
				if(n==1)
					throw new BusinessException("¸ÃÕËºÅ²»ÄÜÉ¾³ý");
			}
				
			else	
				throw new BusinessException("µÇÂ½ÕËºÅ²»´æÔÚ");
			rs.close();
			pst.close();
			sql="delete from admin where adminID=? ";
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


	
	public void createAdmin(BeanAdministrator ad) throws BaseException{
		Connection conn=null;
		if(ad.getAdminid()==null||ad.getAdminname()==null||ad.getAPwd()==null)
			throw new BusinessException("ÕËºÅ£¬Ãû×Ö£¬ÃÜÂëÎª¿Õ");
		if("".equals(ad.getAdminid())||"".equals(ad.getAdminname())||"".equals(ad.getAPwd()))
			throw new BusinessException("ÕËºÅ£¬Ãû×Ö£¬ÃÜÂëÎª¿Õ");
		try {
			conn=DBUtil.getConnection();
			String sql="select adminaccount from admin where adminaccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, ad.getAdminid());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				rs.close();
				pst.close();
				throw new BusinessException("ÓÃ»§ÒÑ´æÔÚ");
			}
			sql="select max(adminID) from admin";
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			int n = 0;
			if(rs.next())
				n=rs.getInt(1);
			rs.close();
			pst.close();
			sql="insert into admin(adminID,adminaccount,adminname,adminpwd) values(?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,(n+1));
			pst.setString(2, ad.getAdminid());
			pst.setString(3, ad.getAdminname());
			pst.setString(4, ad.getAPwd());
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