package cn.edu.zucc.personplan.comtrol.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.zucc.personplan.itf.IPlanManager;
import cn.edu.zucc.personplan.model.BeanPlan;
import cn.edu.zucc.personplan.model.BeanUser;
import cn.edu.zucc.personplan.util.BaseException;
import cn.edu.zucc.personplan.util.BusinessException;
import cn.edu.zucc.personplan.util.DBUtil;
import cn.edu.zucc.personplan.util.DbException;

public class ExamplePlanManager implements IPlanManager {

	@Override
	public BeanPlan addPlan(String name) throws BaseException {
		// TODO Auto-generated method stub
		if(name==null||"".equals(name))
			throw new BusinessException("计划名为空");
		BeanPlan p=null;
		Connection conn=null;
		try {
			//得到数据
			conn=DBUtil.getConnection();
			String sql1="select max(plan_order) from tbl_plan where user_id=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql1);
			pst.setString(1, BeanUser.currentLoginUser.getUser_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				p=new BeanPlan();
				p.setPlan_order(rs.getInt(1)+1);
			}
			else{
				p.setPlan_order(1);
			}
			rs.close();
			pst.close();
			//添加计划
			String sql2="INSERT INTO tbl_plan (user_id, plan_order, plan_name, create_time, step_count, start_step_count, finished_step_count) values(?,?,?,?,0,0,0)";
			pst=conn.prepareStatement(sql2);
			pst.setString(1,BeanUser.currentLoginUser.getUser_id());
			pst.setInt(2,p.getPlan_order());
			pst.setString(3,name);
			pst.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.execute();
			pst.close();
			//显示
			String sql3="select * from tbl_plan where user_id=? and plan_order=? ";
			pst=conn.prepareStatement(sql3);
			pst.setString(1, BeanUser.currentLoginUser.getUser_id());
			pst.setInt(2, p.getPlan_order());
			rs=pst.executeQuery();
			if(rs.next()) {
				p=new BeanPlan();
				p.setPlan_id(rs.getInt(1));
				p.setUser_id(rs.getString(2));
				p.setPlan_order(rs.getInt(3));
				p.setPlan_name(rs.getString(4));
				p.setCreate_time(rs.getTimestamp(5));
				p.setStep_count(rs.getInt(6));
				p.setStart_step_count(rs.getInt(7));
				p.setFinished_step_count(rs.getInt(8));
			}
			rs.close();
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
		return p;
	}

	@Override
	public List<BeanPlan> loadAll() throws BaseException {
		List<BeanPlan> result=new ArrayList<BeanPlan>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select plan_id, user_id, plan_order, plan_name, create_time, step_count, start_step_count, finished_step_count from tbl_plan where user_id=? order by plan_order";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUser_id());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				BeanPlan p=new BeanPlan();
				p.setPlan_id(rs.getInt(1));
				p.setUser_id(rs.getString(2));
				p.setPlan_order(rs.getInt(3));
				p.setPlan_name(rs.getString(4));
				p.setCreate_time(rs.getTimestamp(5));
				p.setStep_count(rs.getInt(6));
				p.setStart_step_count(rs.getInt(7));
				p.setFinished_step_count(rs.getInt(8));
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

	@Override
	public void deletePlan(BeanPlan plan) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false); //事务
			//条件约束
			String sql1="select count(*) from tbl_step where plan_id= "+plan.getPlan_id();
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql1);
			if(rs.next()) {
				if(rs.getInt(1)>0) {
					rs.close();
					st.close();
					throw new BusinessException("该计划已含步骤，不能删除");
				}
			}
			rs.close();
			
			String sql2="select plan_order,user_id from tbl_plan where plan_id="+plan.getPlan_id();
			rs=st.executeQuery(sql2);
			if(rs.next()) {
				plan.setPlan_order(rs.getInt(1));
				plan.setPlan_name(rs.getString(2));
			}else {
				throw new BusinessException("无该计划");
			}
			rs.close();
			
			//删除
			String sql3="delete from tbl_plan where plan_id="+plan.getPlan_id();
			st.execute(sql3);
			st.close();
			//修改相关plan_order
			String sql4="UPDATE tbl_plan SET plan_order=-plan_order WHERE user_id=? and plan_id >"+plan.getPlan_id();
			java.sql.PreparedStatement pst=conn.prepareStatement(sql4);
			pst.setString(1, plan.getUser_id());
			pst.execute();
			sql4="UPDATE tbl_plan SET plan_order=-plan_order-1 WHERE user_id=? and plan_id>"+plan.getPlan_id();
			pst=conn.prepareStatement(sql4);
			pst.setString(1, plan.getUser_id());
			pst.execute();
			conn.commit(); 
		}catch(SQLException ex) {
			ex.printStackTrace();
			throw new DbException(ex);
		}catch(BaseException ex) { 
			try {
				conn.rollback();
				//操作不成功则回退
			}catch (Exception e) {
				ex.printStackTrace();
			}
			throw ex;
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
