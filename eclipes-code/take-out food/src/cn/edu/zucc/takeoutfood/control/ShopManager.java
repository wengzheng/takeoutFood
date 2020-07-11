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
import cn.edu.zucc.takeoutfood.model.BeanShop;



public class ShopManager {
	public List<BeanShop> loadAllShops(boolean withnotdel) throws BaseException{
		Connection conn=null;
		List<BeanShop> result=new ArrayList<BeanShop>();
		try {
			conn=DBUtil.getConnection();
			String sql="select shopaccount,shopname,shoppwd from shop";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				BeanShop bs=new BeanShop();
				bs.setShopid(rs.getString(1));
				bs.setShopname(rs.getString(2));
				bs.setShoppwd(rs.getString(3));
				result.add(bs);
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


	public void resetShopPwd(String shopid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select shopID from shop where shopaccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,shopid);
			java.sql.ResultSet rs=pst.executeQuery();
			int n;
			if(rs.next()) 
				n=rs.getInt(1);
			else	
				throw new BusinessException("登陆账号不存在");
			rs.close();
			pst.close();
			sql="UPDATE shop SET shoppwd=? where shopID=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, "333333");
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

	public void deleteShop(String shopid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT shopID FROM shop WHERE shopaccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,shopid);
			java.sql.ResultSet rs=pst.executeQuery();
			int n;
			if(rs.next()) { 
				n=rs.getInt(1);
			}else	
				throw new BusinessException("登陆账号不存在");
			rs.close();
			pst.close();
			sql="delete from shop where shopID=? ";
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

	public BeanShop loadShop(String shopid)throws BaseException{
		BeanShop bu=new BeanShop();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT shopID,shopaccount,shopname,shoppwd FROM shop WHERE shopaccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,shopid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				bu.setShopNUM(rs.getInt(1));
				bu.setShopid(rs.getString(2));
				bu.setShopname(rs.getString(3));
				bu.setShoppwd(rs.getString(4));
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


	public void createShop(BeanShop user) throws BaseException{
		Connection conn=null;
		if ("".equals(user.getShopid())) {
			throw new BusinessException("账号为空");}
		if("".equals(user.getShopname())) {
			throw new BusinessException("用户名为空");}
		if("".equals(user.getShoppwd())) {	
			throw new BusinessException("密码为空");}
		try {
			conn=DBUtil.getConnection();
			String sql="select shopaccount from shop where shopaccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, user.getShopid());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				rs.close();
				pst.close();
				throw new BusinessException("用户已存在");
			}
			sql="select max(shopID) from shop";
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			int n = 0;
			if(rs.next())
				n=rs.getInt(1);
			rs.close();
			pst.close();
			sql="INSERT shop(shopID,adminID,shopaccount,shopname,shoppwd,shopstar) VALUES (?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,(n+1));
			pst.setInt(2, SystemUserManager.currentUser.getSystemNUM());
			pst.setString(3, user.getShopid());
			pst.setString(4, user.getShopname());
			pst.setString(5, user.getShoppwd());
			pst.setInt(6, user.getShopstar());
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
