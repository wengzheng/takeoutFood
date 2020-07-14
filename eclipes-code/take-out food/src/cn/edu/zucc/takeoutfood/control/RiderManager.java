package cn.edu.zucc.takeoutfood.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.takeoutfood.model.BeanRider;
import cn.edu.zucc.takeoutfood.model.BeanShop;
import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.BusinessException;
import cn.edu.zucc.takeoutfood.util.DBUtil;
import cn.edu.zucc.takeoutfood.util.DbException;

public class RiderManager {
	public List<BeanRider> loadAllRiders(boolean withnotdel) throws BaseException{
		Connection conn=null;
		List<BeanRider> result=new ArrayList<BeanRider>();
		try {
			conn=DBUtil.getConnection();
			String sql="select rideraccount,ridername,riderpwd from rider";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				BeanRider bs=new BeanRider();
				bs.setRiderid(rs.getString(1));
				bs.setRidername(rs.getString(2));
				bs.setRiderpwd(rs.getString(3));
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


	public void resetRiderPwd(String riderid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select riderID from rider where rideraccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,riderid);
			java.sql.ResultSet rs=pst.executeQuery();
			int n;
			if(rs.next()) 
				n=rs.getInt(1);
			else	
				throw new BusinessException("登陆账号不存在");
			rs.close();
			pst.close();
			sql="UPDATE rider SET riderpwd=? where riderID=?";
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

	public void deleteRider(String riderid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT riderID FROM rider WHERE rideraccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,riderid);
			java.sql.ResultSet rs=pst.executeQuery();
			int n;
			if(rs.next()) { 
				n=rs.getInt(1);
			}else	
				throw new BusinessException("登陆账号不存在");
			rs.close();
			pst.close();
			sql="delete from rider where riderID=? ";
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

	public BeanRider loadRider(String riderid)throws BaseException{
		BeanRider bu=new BeanRider();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT riderID,rideraccount,ridername,riderpwd FROM rider WHERE rideraccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,riderid);
			java.sql.ResultSet rs=pst.executeQuery();
			int n=0;
			if(rs.next()) {
				n=rs.getInt(1);
				bu.setRiderID(n);
				bu.setRiderid(rs.getString(2));
				bu.setRidername(rs.getString(3));
				bu.setRiderpwd(rs.getString(4));
			}	
			else
				throw new BusinessException("登陆账号不存在");
			rs.close();
			pst.close();
			sql="update rider set rIdtpye=? WHERE TIMESTAMPDIFF(MONTH,rstarttime,NOW())>0 and riderID="+n;
			pst=conn.prepareStatement(sql);
			pst.setString(1,new String("old"));
			pst.execute();
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


	public void createRider(BeanRider rider) throws BaseException{
		Connection conn=null;
		if ("".equals(rider.getRiderid())) {
			throw new BusinessException("账号为空");}
		if("".equals(rider.getRidername())) {
			throw new BusinessException("用户名为空");}
		if("".equals(rider.getRiderpwd())) {	
			throw new BusinessException("密码为空");}
		try {
			conn=DBUtil.getConnection();
			String sql="select rideraccount from rider where rideraccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, rider.getRiderid());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				rs.close();
				pst.close();
				throw new BusinessException("用户已存在");
			}
			sql="select max(riderID) from rider";
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			int n = 0;
			if(rs.next())
				n=rs.getInt(1);
			rs.close();
			pst.close();
			sql="INSERT rider(riderID,adminID,rideraccount,ridername,riderpwd,rstarttime,rIdtpye) VALUES (?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,(n+1));
			pst.setInt(2, SystemUserManager.currentUser.getSystemNUM());
			pst.setString(3, rider.getRiderid());
			pst.setString(4, rider.getRidername());
			pst.setString(5, rider.getRiderpwd());
			pst.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setString(7, rider.getrIdtpye());
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