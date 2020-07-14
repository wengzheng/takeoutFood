package cn.edu.zucc.takeoutfood.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.takeoutfood.model.BeanCoupon;
import cn.edu.zucc.takeoutfood.model.BeanCouponHold;
import cn.edu.zucc.takeoutfood.model.BeanGiveCoupon;
import cn.edu.zucc.takeoutfood.model.BeanOrder;
import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.BusinessException;
import cn.edu.zucc.takeoutfood.util.DBUtil;
import cn.edu.zucc.takeoutfood.util.DbException;

public class CouponHoldManager {
	public static int dec_orderid=0;
	public List<BeanCouponHold> loadMyComoditys(boolean b) throws BaseException{
		List<BeanCouponHold> result =new ArrayList<BeanCouponHold>(); 
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select incouponID,shopID,couponID,userID,cdiscount,cnum,cdeadline from incoupon where userID="+SystemUserManager.currentUser.getSystemNUM();
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				BeanCouponHold p=new BeanCouponHold();
				p.setIncouponID(rs.getInt(1));
				p.setShopID(rs.getInt(2));
				p.setCouponID(rs.getInt(3));
				p.setUserID(rs.getInt(4));
				p.setCoupondiscount(rs.getDouble(5));
				p.setCouponNumber(rs.getInt(6));
				p.setCdeadline(rs.getTimestamp(7));
				result.add(p);
			}
			return result;
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

	

	public void addCouponHold() throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select coupon.couponID, givecoupon.givecouponID, givecoupon.userID, coupon.shopID, coupon.cdiscount, coupon.cendtime, givecoupon.numnowing, givecoupon.requarenum from coupon,givecoupon WHERE givecoupon.couponID=coupon.couponID";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				//System.out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getInt(3)+" "+rs.getInt(4)+" "+rs.getInt(7)+" "+rs.getInt(8));//=------------------------
				int m=rs.getInt(7);int n=rs.getInt(8);
				String sql1 = "SELECT incouponID from incoupon where userID=? and couponID=?";
				java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
				pst1.setInt(1, SystemUserManager.currentUser.getSystemNUM());
				pst1.setInt(2, rs.getInt(1));
				java.sql.ResultSet rs1=pst1.executeQuery();
				int iid=0;
				if(rs1.next()) {
					iid=rs1.getInt(1);
				}
				//System.out.println(rs1.getInt(1)+" "+iid);
				if(iid>0) {//System.out.println(" cnum is change");//=-------------
					String sql2="UPDATE incoupon SET cnum=? where incouponID="+iid;
					java.sql.PreparedStatement pst2=conn.prepareStatement(sql2);
					pst2.setInt(1,m/n );
					pst2.execute();
					pst2.close();
				}
				else if (iid==0){//System.out.println("cnum is create");//=-------------				
					String sql2="select max(incouponID) from incoupon";
					java.sql.PreparedStatement pst2=conn.prepareStatement(sql2);
					java.sql.ResultSet rs2=pst2.executeQuery();
					int ID=1;
					if(rs2.next())
						ID=rs2.getInt(1)+1;
					pst2.close();
					rs2.close();
					
					sql2="INSERT INTO incoupon (incouponID,couponID,userID,shopID,cdiscount,cnum,cdeadline) VALUES (?,?,?,?,?,1,?)";
					pst2=conn.prepareStatement(sql2);
					pst2.setInt(1, ID);
					pst2.setInt(2, rs.getInt(1));
					pst2.setInt(3, rs.getInt(3));
					pst2.setInt(4, rs.getInt(4));
					pst2.setDouble(5, rs.getDouble(5));
					pst2.setTimestamp(6,rs.getTimestamp(6));
					pst2.execute();
					pst2.close();
				}
				
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
		
	}


	
	public void deleteCoupon(int incouponid,int cnum,double dis) throws BaseException{
		if(this.dec_orderid==0) {
			throw new BusinessException("未选订单");
		}
		if(cnum<=0) {
			throw new BusinessException("优惠劵数量不足");
		}
		
		Connection conn=null;
		
		try {
			conn=DBUtil.getConnection();
			
			String sql="select settlementamount from orders where orderID="+this.dec_orderid;
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next())
				if(rs.getDouble(1)<dis)
					throw new BusinessException("该优惠卷不能应用在该订单上");
			
			sql="UPDATE orders SET settlementamount=settlementamount-?  WHERE orderID="+this.dec_orderid;
			pst=conn.prepareStatement(sql);
			pst.setDouble(1, dis);
			pst.execute();
			pst.close();
			sql="UPDATE incoupon SET cnum=cnum-1 where incouponID="+incouponid;
			pst=conn.prepareStatement(sql);
			pst.execute();
			pst.close();
			
			sql="select couponID,shopID,userID from incoupon where incouponID="+incouponid;
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			if(rs.next()) {
				sql="UPDATE givecoupon SET numnowing=numnowing-requarenum WHERE couponID=? and shopID=? and userID=?";
				pst=conn.prepareStatement(sql);
				pst.setInt(1, rs.getInt(1));
				pst.setInt(2, rs.getInt(2));
				pst.setInt(3, rs.getInt(3));
				pst.execute();
				pst.close();
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
	
	
	}
	

}
