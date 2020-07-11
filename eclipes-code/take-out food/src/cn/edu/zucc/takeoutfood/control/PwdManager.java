package cn.edu.zucc.takeoutfood.control;

import java.sql.Connection;
import java.sql.SQLException;

import cn.edu.zucc.takeoutfood.util.BusinessException;
import cn.edu.zucc.takeoutfood.util.DbException;
import cn.edu.zucc.takeoutfood.model.BeanSystemUser;
import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.util.DBUtil;
public class PwdManager {
	public void changePwd(BeanSystemUser su, String oldPwd, String newPwd, String newPwd2) throws BaseException {
		if(oldPwd==null||newPwd==null||newPwd2==null)
			throw new BusinessException("密码为空");
		if("".equals(oldPwd)||"".equals(newPwd)||"".equals(newPwd2))
			throw new BusinessException("密码为空");
		if(!newPwd.equals(newPwd2))
			throw new BusinessException("两次密码不同");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql ="";
			if(su.getStype()==1)
				sql="select adminID from admin where adminaccount=?";
			else if(su.getStype()==2)
				sql="select userID from users where useraccount=?";
			else if(su.getStype()==3) 
				sql="select shopID from shop where shopaccount=?";
			else if(su.getStype()==4) 
				sql="select riderID from rider where rideraccount=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,su.getSystemUserid());
			java.sql.ResultSet rs=pst.executeQuery();
			int n;
			if(rs.next()) { 
				n=rs.getInt(1);
				if((n==1)&&(su.getStype()==1))
					throw new BusinessException("该账号不能修改密码");
			}
				
			else	
				throw new BusinessException("登陆账号不存在");
			rs.close();
			pst.close();
			//选择登录账号类型
			if(su.getStype()==1)
				sql="UPDATE admin SET adminpwd=? where adminpwd=? and adminID=?";
			else if(su.getStype()==2)
				sql="UPDATE users SET userpwd=? WHERE userpwd=? AND userID=?";
			else if(su.getStype()==3)
				sql="UPDATE shop SET shoppwd=? WHERE shoppwd=? AND shopID=?";				
			else if(su.getStype()==4)
				sql="UPDATE rider SET riderpwd=? WHERE riderpwd=? AND riderID=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, newPwd);
			pst.setString(2, oldPwd);
			pst.setInt(3, n);
			pst.execute();
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
