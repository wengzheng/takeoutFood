package cn.edu.zucc.takeoutfood.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.zucc.takeoutfood.model.BeanAdministrator;
import cn.edu.zucc.takeoutfood.model.BeanRiderSend;
import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.BusinessException;
import cn.edu.zucc.takeoutfood.util.DBUtil;
import cn.edu.zucc.takeoutfood.util.DbException;

public class RiderSendManager {

	public List<BeanRiderSend> loadAllOrdersends(boolean b) throws BaseException{
		Connection conn=null;
		List<BeanRiderSend> result=new ArrayList<BeanRiderSend>();
		try {
			conn=DBUtil.getConnection();
			String sql="select sendID,orderID,riderID,sendtime from ridesends where sendID="+SystemUserManager.currentUser.getSystemNUM();
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				BeanRiderSend ba=new BeanRiderSend();
				ba.setSendID(rs.getInt(1));
				ba.setOrderID(rs.getInt(2));
				ba.setRiderID(rs.getInt(3));
				ba.setSendtime(rs.getTimestamp(4));
				result.add(ba);
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

	public void addOrder(int orderid) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select max(sendID) sendtime from ridesends ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			int n=1;
			if(rs.next()) {
				n=rs.getInt(1)+1;
			}
			rs.close();
			st.close();
			
			Date time=new Date();
			sql="select requiretime from orders where orderid="+orderid;
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			if(rs.next()) {
				time=rs.getTimestamp(1);
			}
			
			sql="insert into ridesends(sendID,orderID,riderID,sendtime,income) values(?,?,?,?,0)";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, n);
			pst.setInt(2, orderid);
			pst.setInt(3, SystemUserManager.currentUser.getSystemNUM());
			pst.setTimestamp(4, new java.sql.Timestamp(time.getTime()));
			pst.execute();
			pst.close();
			
			sql="update orders set orderstate=? where orderID="+orderid;
			pst=conn.prepareStatement(sql);
			pst.setString(1, new String("Received Order"));
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

	public void getmoney(int orderid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			int id=0;
			String sql="select * from ridesends where sendID="+orderid;
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				id=rs.getInt(1);
			}
			else
				throw new BusinessException("ÎÞ¸Ãµ×µ¥");
			
			sql="select count(*) from ridesends where riderID="+SystemUserManager.currentUser.getSystemNUM();
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			int n=0;
			if(rs.next()) {
				n=rs.getInt(1);
			}
			
			double p=0;
			if(n>0&&n<=100)
				p=2;
			else if(n>100&&n<=300)
				p=3;
			else if(n>300&&n<=450)
				p=5;
			else if(n>450&&n<=550)
				p=6;
			else if(n>550&&n<=650)
				p=7;
			else 
				p=8;
			
			sql="UPDATE ridesends SET income=? where sendID="+orderid;
			pst=conn.prepareStatement(sql);
			pst.setDouble(1,p);
			pst.execute();
			pst.close();
			
			sql="update orders set orderstate=? where orderID="+id;
			pst=conn.prepareStatement(sql);
			pst.setString(1, new String("Received Order"));
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

}
