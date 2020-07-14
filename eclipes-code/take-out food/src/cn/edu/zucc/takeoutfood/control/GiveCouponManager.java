package cn.edu.zucc.takeoutfood.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.edu.zucc.takeoutfood.model.BeanGiveCoupon;
import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.BusinessException;
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
	
	public void AddGiveCoupon(int shopID)  throws BaseException{
		Connection conn=null;
		try {
			conn= DBUtil.getConnection();			
			String sql1="select couponID,shopID,cdiscount,requarenum from coupon where cstarttime<NOW() AND cendtime>NOW() AND shopID="+shopID;
			java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
			java.sql.ResultSet rs1=pst1.executeQuery();
			while(rs1.next()) {
				
				int c=rs1.getInt(1);int s=rs1.getInt(2);int d=rs1.getInt(3);int num=rs1.getInt(4);
				String sql2="select givecouponID from givecoupon where shopID=? and userID=? and couponID=?";
				java.sql.PreparedStatement pst2=conn.prepareStatement(sql2);
				pst2.setInt(1, shopID);
				pst2.setInt(2, SystemUserManager.currentUser.getSystemNUM());
				pst2.setInt(3, c);
				java.sql.ResultSet rs2=pst2.executeQuery();
				//System.out.println("while: "+shopID+" "+SystemUserManager.currentUser.getSystemNUM()+" "+c+" "+num);//-------------
				
				int gid=0;
				if(rs2.next()) {
					gid=rs2.getInt(1);
				}
				//System.out.println("-----"+gid);
				if(gid>0) {
					//System.out.println("update ("+gid+"): "+shopID+" "+SystemUserManager.currentUser.getSystemNUM()+" "+c+" "+num);//-------
					String sql3="UPDATE givecoupon SET numnowing=numnowing+1 WHERE givecouponID="+gid;
					java.sql.PreparedStatement pst3=conn.prepareStatement(sql3);
					pst3.execute();
					pst3.close();
				}
				else if(gid==0) {
					//System.out.println("insert: "+shopID+" "+SystemUserManager.currentUser.getSystemNUM()+" "+c+" "+num);//-------
					String sql="select max(givecouponID) from givecoupon";
					java.sql.PreparedStatement pst=conn.prepareStatement(sql);
					java.sql.ResultSet rs=pst.executeQuery();
					int ID=1;
					if(rs.next())
						ID=rs.getInt(1)+1;
					pst.close();
					rs.close();
					
					String sql4="insert into givecoupon(givecouponID,shopID,userID,couponID,requarenum,numnowing) values(?,?,?,?,?,1)";
					java.sql.PreparedStatement pst4=conn.prepareStatement(sql4);
					pst4.setInt(1,ID);
					pst4.setInt(2,shopID);
					pst4.setInt(3,SystemUserManager.currentUser.getSystemNUM());
					pst4.setInt(4,c);
					pst4.setInt(5,num);
					pst4.execute();
					pst4.close();
				}
				pst2.close();
				rs2.close();
			}
			
			
		}catch (SQLException e) {
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


