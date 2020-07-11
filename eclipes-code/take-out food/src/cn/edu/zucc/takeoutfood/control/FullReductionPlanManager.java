package cn.edu.zucc.takeoutfood.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.takeoutfood.model.BeanCoupon;
import cn.edu.zucc.takeoutfood.model.FullReductionPlan;
import cn.edu.zucc.takeoutfood.ui.FrmFullReductionPlan_addPlan;
import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.BusinessException;
import cn.edu.zucc.takeoutfood.util.DBUtil;
import cn.edu.zucc.takeoutfood.util.DbException;

public class FullReductionPlanManager {

	public void deleteComodity(int frpid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			//条件
			String sql="SELECT * FROM fullreductionplan where fullreduceplanID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, frpid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
			}else {
				throw new BusinessException("无该步骤 ");}			
			rs.close();
			pst.close();
			//删除
			sql="delete from fullreductionplan where fullreduceplanID=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,frpid);
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

	public void createFullReductionPlan(FullReductionPlan frp) throws BaseException{
		Connection conn=null;
		if(frp.getF_discount()<0.0001)
			throw new BusinessException("满减金额为空");
		if(frp.getFullreduceprice()<0.0001) 
			throw new BusinessException("优惠金额为空");
		if(frp.getF_discount()<frp.getFullreduceprice())
			throw new BusinessException("优惠金额>满减金额");
		try {
			conn=DBUtil.getConnection();
			String sql1="SELECT MAX(fullreduceplanID) FROM fullreductionplan where shopID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql1);
			pst.setInt(1, SystemUserManager.currentUser.getSystemNUM());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				frp.setFullreduceplanNUM(rs.getInt(1)+1);
			}
			else {
				frp.setFullreduceplanNUM(1);
			}
			rs.close();
			pst.close();
			
			String sql3="INSERT INTO fullreductionplan(fullreduceplanID,shopID,fullreduceprice,cdiscount,withcoupon) VALUES(?,?,?,?,?)";
			pst=conn.prepareStatement(sql3);
			pst.setInt(1, frp.getFullreduceplanNUM());
			pst.setInt(2, SystemUserManager.currentUser.getSystemNUM());
			pst.setDouble(3, frp.getF_discount());
			pst.setDouble(4, frp.getFullreduceprice());
			pst.setBoolean(5, frp.getWithcoupon());
			pst.execute();
			pst.close();	
		}catch(SQLException ex) {
			ex.printStackTrace();
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

	public List<FullReductionPlan> loadAllComoditys(boolean b) throws BaseException{
		Connection conn=null;
		List<FullReductionPlan> result =new ArrayList<FullReductionPlan>();
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT fullreduceplanID,shopID,fullreduceprice,cdiscount,withcoupon FROM fullreductionplan where shopID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,SystemUserManager.currentUser.getSystemNUM());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				FullReductionPlan p=new FullReductionPlan();
				p.setFullreduceplanNUM(rs.getInt(1));
				p.setShopNUM(rs.getInt(2));
				p.setF_discount(rs.getDouble(3));
				p.setFullreduceprice(rs.getDouble(4));
				p.setWithcoupon(rs.getBoolean(5));
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


}
