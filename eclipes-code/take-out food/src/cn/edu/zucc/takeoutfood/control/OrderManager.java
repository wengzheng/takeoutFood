package cn.edu.zucc.takeoutfood.control;

import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import cn.edu.zucc.takeoutfood.model.BeanComodity;
import cn.edu.zucc.takeoutfood.model.BeanCouponHold;
import cn.edu.zucc.takeoutfood.model.BeanGiveCoupon;
import cn.edu.zucc.takeoutfood.model.BeanOrder;
import cn.edu.zucc.takeoutfood.model.BeanOrderdetail;
import cn.edu.zucc.takeoutfood.model.FullReductionPlan;
import cn.edu.zucc.takeoutfood.ui.FrmComodityTypeManager;
import cn.edu.zucc.takeoutfood.ui.FrmOrderManager;
import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.BusinessException;
import cn.edu.zucc.takeoutfood.util.DBUtil;
import cn.edu.zucc.takeoutfood.util.DbException;

public class OrderManager {
	public static int orderid=0;
	public static int fullreduceplanID=0;
	public List<BeanOrder> loadAllOrders(boolean b) throws BaseException{
		Connection conn=null;
		List<BeanOrder> result =new ArrayList<BeanOrder>();
		try {
			conn=DBUtil.getConnection();
			String sql="select orderID,userID,originalamount,settlementamount,downtime,requiretime,orderstate,evaluation  from orders WHERE userID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,SystemUserManager.currentUser.getSystemNUM());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				BeanOrder p=new BeanOrder();
				p.setOrderID(rs.getInt(1));
				p.setUserID(rs.getInt(2));
				p.setOriginalamount(rs.getDouble(3));
				p.setSettlementamount(rs.getDouble(4));
				p.setDowntime(rs.getTimestamp(5));
				p.setRequiretime(rs.getTimestamp(6));
				p.setOrderstate(rs.getString(7));
				p.setEvaluation(rs.getString(8));
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

	public void createOrder(BeanOrder order) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select commoditynum from comoditytype where commoditytypeID="+FrmComodityTypeManager.comoditytypeid;
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1)==0)
					throw new BusinessException("该商品类无可购商品");
			}
			String sql1="SELECT MAX(orderID) FROM orders ";
			pst=conn.prepareStatement(sql1);
			rs=pst.executeQuery();
			if(rs.next()) {
				order.setOrderID(rs.getInt(1)+1);
			}
			else {
				order.setOrderID(1);
			}
			this.orderid=order.getOrderID();
			rs.close();
			pst.close();
			
			String sql2="INSERT INTO orders(orderID,userID,shopID,originalamount,settlementamount,downtime,orderstate) VALUES(?,?,?,0,0,?,?)";
			pst=conn.prepareStatement(sql2);
			pst.setInt(1, order.getOrderID());
			pst.setInt(2, SystemUserManager.currentUser.getSystemNUM());
			pst.setInt(3, order.getShopID());
			pst.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setString(5, new String("Not Received Order"));
			pst.execute();
			pst.close();
			
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
	
	public void deleteOrder(int Orderid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			//约束
			String sql1="select * from ridesends where orderID="+Orderid;
			java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
			java.sql.ResultSet rs1=pst1.executeQuery();
			if(rs1.next()) 
				throw new BusinessException("骑手已接单，不能删除！");
			
			//前提
			String sql="select shopID from orders WHERE orderID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, Orderid);
			java.sql.ResultSet rs=pst.executeQuery();
			int sid=0;
			if(rs.next()) {
				sid=rs.getInt(1);
			}else {
				throw new BusinessException("无该订单 ");}			
			rs.close();
			pst.close();
			
			//修改集单送券表
			sql="UPDATE givecoupon SET numnowing=numnowing-1 WHERE shopID=? and userID=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,sid);
			pst.setInt(2,SystemUserManager.currentUser.getSystemNUM());
			pst.execute();
			pst.close();
			
			//删除相关orderdetail
			sql="delete from orderdetail where orderID=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, Orderid);
			pst.execute();	
			pst.close();	
			
			//删除指定order
			sql="delete from orders where orderID=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, Orderid);
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
	
	public void AddOrderdetail(int comodityid) throws BaseException{
		Connection conn=null;
		BeanOrderdetail detail=new BeanOrderdetail();
		try {
			conn=DBUtil.getConnection();
			String sql1="SELECT MAX(ordercommodityID) FROM orderdetail ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql1);
			pst.execute();
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				detail.setOrdercommodityID(rs.getInt(1)+1);
			}
			else {
				detail.setOrdercommodityID(1);
			}
			rs.close();
			pst.close();
			
			String sql2="select commodityID,commoditytypeID,commodityname,price from commodity where commodityID="+comodityid;
			java.sql.Statement st=conn.createStatement();
			rs=st.executeQuery(sql2);
			BeanComodity bc=new BeanComodity();
			if(rs.next()) {
				bc.setCommodityNUM(rs.getInt(1));
				bc.setCommoditytypeNUM(rs.getInt(2));
				bc.setCommodityname(rs.getString(3));
				bc.setPrice(rs.getFloat(4));
			}
			rs.close();
			st.close();
			
			String sql3="INSERT INTO orderdetail(ordercommodityID,commodityID,orderID,price) VALUES(?,?,?,?)";
			pst=conn.prepareStatement(sql3);
			pst.setInt(1,detail.getOrdercommodityID());
			pst.setInt(2,bc.getCommodityNUM());
			pst.setInt(3,this.orderid);
			pst.setDouble(4,bc.getPrice());
			pst.execute();
			pst.close();	
			
			String sql4="UPDATE orders SET originalamount=originalamount+?  WHERE orderID=?";
			pst=conn.prepareStatement(sql4);
			pst.setDouble(1, bc.getPrice());
			pst.setInt(2, this.orderid);
			pst.execute();
			pst.close();
			sql4="UPDATE orders SET settlementamount=originalamount  WHERE orderID=?";
			pst=conn.prepareStatement(sql4);
			pst.setInt(1, this.orderid);
			pst.execute();
			pst.close();
			
			//满减：
			double discount=0;
			String sql5="select fullreductionplan.fullreduceplanID, max(fullreductionplan.cdiscount) from fullreductionplan,orders \r\n" + 
					"where  fullreductionplan.fullreduceprice<orders.originalamount and orders.orderID=" +this.orderid;
			pst=conn.prepareStatement(sql5);
			rs=pst.executeQuery();
			if(rs.next()) {
				this.fullreduceplanID=rs.getInt(1);
				discount=rs.getInt(2);
				System.out.println("discount:"+discount);//--------------------
				String sql6="UPDATE orders SET settlementamount=originalamount-? , fullreduceplanID=? WHERE orderID=?";
				pst=conn.prepareStatement(sql6);
				pst.setDouble(1,discount);
				pst.setInt(2, fullreduceplanID);
				pst.setInt(3, this.orderid);
				pst.execute();
				pst.close();
			} 
				
			
			
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

	public void AddEvaluation(int orderid2, String evaluate) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql6="UPDATE orders SET evaluation=?  WHERE orderID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql6);
			pst.setString(1, evaluate);
			pst.setInt(2, this.orderid);
			pst.execute();
			pst.close();
		}catch(SQLException ex) {
			ex.printStackTrace();
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
