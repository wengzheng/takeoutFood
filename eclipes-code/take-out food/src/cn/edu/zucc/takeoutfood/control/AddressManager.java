package cn.edu.zucc.takeoutfood.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.fabric.xmlrpc.base.Data;

import cn.edu.zucc.takeoutfood.model.BeanAddress;
import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.BusinessException;
import cn.edu.zucc.takeoutfood.util.DBUtil;
import cn.edu.zucc.takeoutfood.util.DbException;

public class AddressManager {

	public void createAddress(BeanAddress user,String time) throws BaseException{
		Connection conn=null;
		if("".equals(time))
			throw new BusinessException("要求送达时间为空");
		if("".equals(user.getAddress()))
			throw new BusinessException("地址为空");
		if("".equals(user.getPhone())) 
			throw new BusinessException("联系电话为空");
		if ("".equals(user.getUsername()))
			throw new BusinessException("昵称为空");
		if(OrderManager.orderid==0)
			throw new BusinessException("请先看看商品！！");
		try {
			conn=DBUtil.getConnection();
						
			String sql1="SELECT MAX(addressID) FROM address where userID=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql1);
			pst.setInt(1, SystemUserManager.currentUser.getSystemNUM());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				user.setAddressID(rs.getInt(1)+1);
			}
			else {
				user.setAddressID(1);
			}
			rs.close();
			pst.close();
			
			String sql2="INSERT INTO address(addressID,userID,nowaddress,username,phone) VALUES(?,?,?,?,?)";
			pst=conn.prepareStatement(sql2);
			pst.setInt(1, user.getAddressID());
			pst.setInt(2, SystemUserManager.currentUser.getSystemNUM());
			pst.setString(3, user.getAddress());
			pst.setString(4, user.getUsername());
			pst.setString(5, user.getPhone());
			pst.execute();
			pst.close();
			
			//填写到达时间
			String sql="UPDATE orders SET requiretime=? , addressID=? WHERE orderID="+OrderManager.orderid;
			pst=conn.prepareStatement(sql);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date Time=new Date();
			Time = sdf.parse(time);
			pst.setTimestamp(1, new java.sql.Timestamp(Time.getTime()));
			pst.setInt(2, user.getAddressID());
			pst.execute();
			pst.close();
		}catch(SQLException ex) {
			ex.printStackTrace();
			throw new DbException(ex);
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
