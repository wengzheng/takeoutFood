package cn.edu.zucc.takeoutfood.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import cn.edu.zucc.takeoutfood.util.DBUtil;
import cn.edu.zucc.takeoutfood.util.DbException;
import cn.edu.zucc.takeoutfood.util.BusinessException;
import cn.edu.zucc.takeoutfood.model.BeanCoupon;
import cn.edu.zucc.takeoutfood.model.BeanCouponHold;
import cn.edu.zucc.takeoutfood.util.BaseException;

public class CouponManager {

	public List<BeanCoupon> loadAllComoditys(boolean b) throws BaseException{
		Connection conn=null;
		List<BeanCoupon> result =new ArrayList<BeanCoupon>();
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT couponID,shopID,cdiscount,cstarttime,cendtime,requarenum FROM coupon where shopID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,SystemUserManager.currentUser.getSystemNUM());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				BeanCoupon p=new BeanCoupon();
				p.setCouponNUM(rs.getInt(1));
				p.setShopNUM(rs.getInt(2));
				p.setC_discount(rs.getDouble(3));
				p.setC_starttime(rs.getTimestamp(4));
				p.setC_endtime(rs.getTimestamp(5));
				p.setRequarrenum(rs.getInt(6));
				result.add(p);
			}
			
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
		
		return result;
	}

	public void deleteComodity(int couponid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			//条件
			String sql="SELECT * FROM coupon where couponID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, couponid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
			}else {
				throw new BusinessException("无该步骤 ");}			
			rs.close();
			pst.close();
			//删除
			sql="delete from coupon where couponID=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,couponid);
			pst.execute();
			rs.close();
			pst.close();
		}catch(SQLException ex) {
			ex.printStackTrace();
			throw new DbException(ex);
		}catch(BaseException ex) {  
			try { 
			//操作不成功则回退 
			conn.rollback(); 
			}catch(SQLException e) {
				e.printStackTrace();
			}
			ex.printStackTrace();
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
		
	

	public void createRider(BeanCoupon coupon, String endtime) throws BaseException{
		Connection conn=null;
		if(coupon.getC_discount()<0.0001)
			throw new BusinessException("优惠金额为空");
		if("".equals(coupon.getC_endtime()))
			throw new BusinessException("结束时间为空");
		if(coupon.getRequarrenum()==0) 
			throw new BusinessException("集单要求数为空");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			coupon.setC_endtime(sdf.parse(endtime));
			conn=DBUtil.getConnection();
			String sql1="SELECT MAX(couponID) FROM coupon";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql1);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				coupon.setCouponNUM(rs.getInt(1)+1);
			}
			else {
				coupon.setCouponNUM(1);
			}
			rs.close();
			pst.close();
			
			String sql3="INSERT INTO coupon (couponID,shopID,cdiscount,cstarttime,cendtime,requarenum) VALUES(?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql3);
			pst.setInt(1, coupon.getCouponNUM());
			pst.setInt(2, SystemUserManager.currentUser.getSystemNUM());
			pst.setDouble(3, coupon.getC_discount());
			java.sql.Timestamp cstarttime=new java.sql.Timestamp(System.currentTimeMillis());
			pst.setTimestamp(4,cstarttime);
			java.sql.Timestamp cendtime=new java.sql.Timestamp(coupon.getC_endtime().getTime());
			pst.setTimestamp(5, cendtime);
			pst.setInt(6, coupon.getRequarrenum());
			pst.execute();
			pst.close();	
		}catch(SQLException ex) {
			ex.printStackTrace();
			throw new DbException(ex);
		}catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
