package cn.edu.zucc.takeoutfood.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.takeoutfood.model.BeanComodity;
import cn.edu.zucc.takeoutfood.model.BeanShop;
import cn.edu.zucc.takeoutfood.ui.FrmComodityTypeManager;
import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.BusinessException;
import cn.edu.zucc.takeoutfood.util.DBUtil;
import cn.edu.zucc.takeoutfood.util.DbException;

public class ComodityManager {


	public void deleteComodity(int comodityid) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT commodityID FROM commodity WHERE commodityID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, comodityid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) { 
			}else {	
				throw new BusinessException("登陆账号不存在");}
			rs.close();
			pst.close();
			//删除表的内容
			sql="delete from commodity where commodityID=? ";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,comodityid);
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

	public void createComodity(BeanComodity comodity) throws BaseException{
		Connection conn=null;
		if("".equals(comodity.getCommodityname()))
			throw new BusinessException("商品名为空");
		if(comodity.getPrice()<0.0001) 
			throw new BusinessException("单价为空");
		
		try {
			conn=DBUtil.getConnection();
			String sql="select max(commodityID) from commodity ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			int cid=1;
			if(rs.next()) {
				cid=rs.getInt(1)+1;
			}
			rs.close();
			pst.close();
			sql="INSERT commodity(commodityID,commoditytypeID,commodityname,price) VALUES (?,?,?,?);";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,cid);
			pst.setInt(2,FrmComodityTypeManager.comoditytypeid);
			pst.setString(3, comodity.getCommodityname());
			pst.setFloat(4, comodity.getPrice());
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
	
	public List<BeanComodity> loadAllComoditys(boolean b) throws BaseException {
		Connection conn=null;
		List<BeanComodity> result=new ArrayList<BeanComodity>();
		try {
			conn=DBUtil.getConnection();
			String sql="select commodityID,commoditytypeID,commodityname,price from commodity where commoditytypeID="+FrmComodityTypeManager.comoditytypeid;
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				BeanComodity bc=new BeanComodity();
				bc.setCommodityNUM(rs.getInt(1));
				bc.setCommoditytypeNUM(rs.getInt(2));
				bc.setCommodityname(rs.getString(3));
				bc.setPrice(rs.getFloat(4));
				result.add(bc);
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
}
