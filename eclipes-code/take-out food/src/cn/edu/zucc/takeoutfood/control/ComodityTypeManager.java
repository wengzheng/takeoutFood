package cn.edu.zucc.takeoutfood.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.takeoutfood.model.BeanComodity;
import cn.edu.zucc.takeoutfood.model.BeanComoditytype;
import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.BusinessException;
import cn.edu.zucc.takeoutfood.util.DBUtil;
import cn.edu.zucc.takeoutfood.util.DbException;

public class ComodityTypeManager {

	public void createComodityType(BeanComoditytype comodity) throws BaseException{
		Connection conn=null;
		if("".equals(comodity.getCommoditytypename()))
			throw new BusinessException("商品类名为空");
		try {
			conn=DBUtil.getConnection();
			String sql="select commoditytypeID from comoditytype where commoditytypename=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, comodity.getCommoditytypename());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				throw new BusinessException("该商品已有");
			}
			rs.close();
			pst.close();
			
			sql="select max(commoditytypeID) from comoditytype where shopID=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, SystemUserManager.currentUser.getSystemNUM());
			rs=pst.executeQuery();
			int n=1;
			if(rs.next()) 
				n=rs.getInt(1)+1;	
			rs.close();
			pst.close();
			
			sql="INSERT comoditytype(commoditytypeID,shopID,commoditytypename,commoditynum) VALUES (?,?,?,0);";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,n);
			pst.setInt(2,SystemUserManager.currentUser.getSystemNUM());
			pst.setString(3, comodity.getCommoditytypename());
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

	public List<BeanComoditytype> loadAllComoditytype(boolean b) throws BaseException{
		Connection conn=null;
		List<BeanComoditytype> result=new ArrayList<BeanComoditytype>();
		try {
			conn=DBUtil.getConnection();
			String sql="select commoditytypeID,shopID,commoditytypename,commoditynum from comoditytype where shopID="+SystemUserManager.currentUser.getSystemNUM();
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				BeanComoditytype bc=new BeanComoditytype();
				bc.setCommoditytypeNUM(rs.getInt(1));
				bc.setShopNUM(rs.getInt(2));
				bc.setCommoditytypename(rs.getString(3));
				bc.setCommoditynum(rs.getInt(4));
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

	public void deleteComodityType(int comoditytypeid) throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="SELECT commoditytypeID FROM comoditytype WHERE commoditytypeID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,comoditytypeid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) { 
			}else	
				throw new BusinessException("登陆账号不存在");
			rs.close();
			pst.close();
			sql="SELECT * FROM commodity WHERE commoditytypeID=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,comoditytypeid);
			rs=pst.executeQuery();
			if(rs.next()) { 
				throw new BusinessException("该类商品类已有商品上架");
			}		
			rs.close();
			pst.close();
			//删除表的内容
			sql="delete from comoditytype where commoditytypeID=? ";
			pst=conn.prepareStatement(sql);
			pst.setInt(1,comoditytypeid);
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

}
