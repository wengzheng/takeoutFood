package cn.edu.zucc.takeoutfood.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.edu.zucc.takeoutfood.model.BeanGiveCoupon;
import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.DBUtil;
import cn.edu.zucc.takeoutfood.util.DbException;

public class GiveCouponManager {

	public void modifyGiveCoupon(BeanGiveCoupon GiveCoupon) throws BaseException{
		Connection conn=null;
		try {
				conn= DBUtil.getConnection();				
				String sql="select givecouponID from givecoupon where shopID=? and userID=? and couponID=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				java.sql.ResultSet rs=pst.executeQuery();
				int n=0;
				if(rs.next())
					n=rs.getInt(1);
				sql="UPDATE givecoupon SET numnowing=numnowing+1 WHERE givecouponID="+n;
				java.sql.PreparedStatement pst6=conn.prepareStatement(sql);
				pst.execute();
				pst.close();
				
					
		}catch(SQLException ex) {
			throw new DbException(ex);
		}finally {
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
	
	public void AddGiveCoupon(BeanGiveCoupon GiveCoupon) throws BaseException{
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();				
			String sql="select max(givecouponID) from givecoupon";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			int n=1;
			if(rs.next())
				n=rs.getInt(1)+1;
			sql="insert into givecoupon(givecouponID,shopID,userID,couponID,requarenum,numnowing) values(?,?,?,?,?,1)";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,n);
			pst.setInt(2,GiveCoupon.getShopID());
			pst.setInt(3,SystemUserManager.currentUser.getSystemNUM());
			pst.setInt(4,GiveCoupon.getCouponID());
			pst.setInt(5,GiveCoupon.getRequarenum());
			pst.execute();
			pst.close();
		}catch(SQLException ex) {
			throw new DbException(ex);
		}finally {
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


