package cn.edu.zucc.personplan.comtrol.example;

import java.nio.channels.NonWritableChannelException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.zucc.personplan.itf.IStepManager;
import cn.edu.zucc.personplan.model.BeanPlan;
import cn.edu.zucc.personplan.model.BeanStep;
import cn.edu.zucc.personplan.model.BeanUser;
import cn.edu.zucc.personplan.util.BaseException;
import cn.edu.zucc.personplan.util.BusinessException;
import cn.edu.zucc.personplan.util.DBUtil;
import cn.edu.zucc.personplan.util.DbException;

public class ExampleStepManager implements IStepManager {
	
	//添加
	@Override
	public void add(BeanPlan plan, String name, String planstartdate,
			String planfinishdate) throws BaseException, BaseException {
		Connection conn=null;
		if(name==null||"".equals(name))
			throw new BusinessException("步骤名为空");
		if(planstartdate==null||"".equals(planstartdate))
			throw new BusinessException("计划开始时间为空");
		if(planfinishdate==null||"".equals(planfinishdate))
			throw new BusinessException("计划完成时间为空");
		try {
			//得到数据
			BeanStep step=new BeanStep();
			Date planstart_time=new Date();
			Date planfinish_time=new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			planstart_time=sdf.parse(planstartdate);
			planfinish_time=sdf.parse(planfinishdate);
			if(planstart_time.compareTo(planfinish_time)==1) {	
				throw new BusinessException("计划开始时间>计划完成时间");
			}
			step.setStep_name(name); 
			step.setPlan_begin_time(planstart_time);
			step.setPlan_end_time(planfinish_time);
			
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false); //事务
			String sql1="select max(step_order) from tbl_step where plan_id=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql1);
			pst.setInt(1, plan.getPlan_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) {
				step.setStep_order(rs.getInt(1)+1);
			}
			else {
				step.setStep_order(1);
			}
			rs.close();
			pst.close();
			
			//修改相关于plan表的数据
			String sql2="UPDATE tbl_plan SET step_count=? WHERE user_id=? and plan_id=?";
			pst=conn.prepareStatement(sql2);
			pst.setInt(1, step.getStep_order());
			pst.setString(2, BeanUser.currentLoginUser.getUser_id());
			pst.setInt(3, plan.getPlan_id());
			pst.execute();
			rs.close();
			pst.close();
			
			//添加步骤
			String sql3="INSERT into tbl_step ( plan_id, step_order, step_name, plan_begin_time, plan_end_time) VALUES (?,?,?,?,?)";
			pst=conn.prepareStatement(sql3);
			pst.setInt(1, plan.getPlan_id());
			pst.setInt(2, step.getStep_order());
			pst.setString(3, step.getStep_name());
			java.sql.Timestamp Plan_begin_timestamp = new java.sql.Timestamp(step.getPlan_begin_time().getTime());
			pst.setTimestamp(4,Plan_begin_timestamp);
			java.sql.Timestamp Plan_end_Timestamp= new java.sql.Timestamp(step.getPlan_end_time().getTime());
			pst.setTimestamp(5, Plan_end_Timestamp);
			pst.execute();
			pst.close();
			conn.commit(); 
			
			
		}catch(SQLException ex) {
			ex.printStackTrace();
			throw new DbException(ex);
		}catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

	//显示
	@Override
	public List<BeanStep> loadSteps(BeanPlan plan) throws BaseException {
		List<BeanStep> result=new ArrayList<BeanStep>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select step_id, plan_id, step_order, step_name, plan_begin_time, plan_end_time, real_begin_time, real_end_time from tbl_step where plan_id=? order by step_order";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlan_id());
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				BeanStep p=new BeanStep();
				p.setStep_id(rs.getInt(1));
				p.setPlan_id(rs.getInt(2));
				p.setStep_order(rs.getInt(3));
				p.setStep_name(rs.getString(4));
				p.setPlan_begin_time(rs.getTimestamp(5));
				p.setPlan_end_time(rs.getTimestamp(6));
				p.setReal_begin_time(rs.getTimestamp(7));
				p.setReal_end_time(rs.getTimestamp(8));
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
	
	//删除步骤
	@Override
	public void deleteStep(BeanStep step) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			//条件约束
			String sql1="select step_order,plan_id from tbl_step where step_id="+step.getStep_id();
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql1);
			
			if(rs.next()) {
				if(step.getStep_order()!=rs.getInt(1) || step.getPlan_id()!=rs.getInt(2))
					throw new BusinessException("找不到与该步骤相同的步骤 ");
			}else {
				throw new BusinessException("无该步骤 ");
			}
			rs.close();
			
			//删除
			String sql2="delete from tbl_step where step_id="+step.getStep_id();
			st.execute(sql2);
			st.close();
			
			//修改相关于plan表的数据
			String sql3="UPDATE tbl_plan SET step_count=step_count-1 WHERE user_id=? and plan_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql3);
			pst.setString(1, BeanUser.currentLoginUser.getUser_id());
			pst.setInt(2, step.getPlan_id());
			pst.execute();
			pst.close();
			Date sdate=step.getReal_begin_time();
			if(sdate!=null) {
				sql3="UPDATE tbl_plan SET start_step_count=start_step_count-1 WHERE user_id=? and plan_id=?";
				pst =conn.prepareStatement(sql3);
				pst.setString(1, BeanUser.currentLoginUser.getUser_id());
				pst.setInt(2, step.getPlan_id());
				pst.execute();
				pst.close();
			}
			
			Date edate=step.getReal_end_time();
			if(edate!=null) {
				sql3="UPDATE tbl_plan SET finished_step_count=finished_step_count-1 WHERE user_id=? and plan_id=?";
				pst =conn.prepareStatement(sql3);
				pst.setString(1, BeanUser.currentLoginUser.getUser_id());
				pst.setInt(2, step.getPlan_id());
				pst.execute();
				pst.close();
			}
					
			
			
			
			//修改相关step_order
			String sql4="UPDATE tbl_step SET step_order=-step_order WHERE plan_id=? and step_id >"+step.getStep_id();
			pst=conn.prepareStatement(sql4);
			pst.setInt(1, step.getPlan_id());
			pst.execute();
			sql4="UPDATE tbl_step SET step_order=-1-step_order WHERE plan_id=? and step_id >"+step.getStep_id();
			pst=conn.prepareStatement(sql4);
			pst.setInt(1, step.getPlan_id());
			pst.execute();
			conn.commit();
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
	
	//+实际开始时间
	@Override
	public void startStep(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql1="UPDATE tbl_step SET tbl_step.real_begin_time=NOW() WHERE step_id=? and plan_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql1);
			pst.setInt(1, step.getStep_id());
			pst.setInt(2, step.getPlan_id());
			pst.execute();
			pst.close();
			
			//修改相关于plan表的数据
			String sql2="UPDATE tbl_plan SET start_step_count=start_step_count+1 WHERE user_id=? and plan_id=?";
			pst=conn.prepareStatement(sql2);
			pst.setString(1, BeanUser.currentLoginUser.getUser_id());
			pst.setInt(2, step.getPlan_id());
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
					e.printStackTrace();
				}
			}
		}
	}
	
	//+实际结束时间
	@Override
	public void finishStep(BeanStep step) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql1="UPDATE tbl_step SET tbl_step.real_end_time=NOW() WHERE step_id=? and plan_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql1);
			pst.setInt(1, step.getStep_id());
			pst.setInt(2, step.getPlan_id());
			pst.execute();
			pst.close();
			
			//修改相关于plan表的数据
			String sql2="UPDATE tbl_plan SET finished_step_count=finished_step_count+1 WHERE user_id=? and plan_id=?";
			pst=conn.prepareStatement(sql2);
			pst.setString(1, BeanUser.currentLoginUser.getUser_id());
			pst.setInt(2, step.getPlan_id());
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
					e.printStackTrace();
				}
			}
		}
		
	}

	//上移
	@Override
	public void moveUp(BeanStep step) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			//条件约束
			String sql1="select step_order,plan_id from tbl_step where step_id="+step.getStep_id();
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql1);
			if(rs.next()) {
				step.setStep_order(rs.getInt(1));
				step.setPlan_id(rs.getInt(2));
				if(step.getStep_order()==1) {
					throw new BusinessException("该步骤为首，无法上移");
				}
			}else {
				throw new BusinessException("无该步骤");
			}
			rs.close();
			
			//up修改相关step_order
			String sql2="UPDATE tbl_step SET step_order=-(step_order-1) WHERE plan_id=? and step_id = "+step.getStep_id();
			java.sql.PreparedStatement pst=conn.prepareStatement(sql2);
			pst.setInt(1, step.getPlan_id());
			pst.execute();
			sql2="UPDATE tbl_step SET step_order=-(step_order+1) WHERE plan_id=? and step_order =" +(step.getStep_order()-1); 		
			pst=conn.prepareStatement(sql2);
			pst.setInt(1, step.getPlan_id());
			pst.execute();
			sql2="UPDATE tbl_step SET step_order=-step_order WHERE plan_id=? and step_order <0";
			pst=conn.prepareStatement(sql2);
			pst.setInt(1, step.getPlan_id());
			pst.execute();
			conn.commit();
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

	//下移
	@Override
	public void moveDown(BeanStep step) throws BaseException {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			conn.setAutoCommit(false);
			//条件约束
			String sql1="select max(step_order) from tbl_step where plan_id="+step.getPlan_id();
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql1);
			if(rs.next()) {
				if(step.getStep_order()==rs.getInt(1)) {
					throw new BusinessException("该步骤为尾部，无法下移");
				}
			}else {
				throw new BusinessException("无该步骤");
			}
			rs.close();
			
			//up修改相关step_order
			String sql2="UPDATE tbl_step SET step_order=-(step_order+1) WHERE plan_id=? and step_id = "+step.getStep_id();
			java.sql.PreparedStatement pst=conn.prepareStatement(sql2);
			pst.setInt(1, step.getPlan_id());
			pst.execute();
			sql2="UPDATE tbl_step SET step_order=-(step_order-1) WHERE plan_id=? and step_order =" +(step.getStep_order()+1); 		
			pst=conn.prepareStatement(sql2);
			pst.setInt(1, step.getPlan_id());
			pst.execute();
			sql2="UPDATE tbl_step SET step_order=-step_order WHERE plan_id=? and step_order <0";
			pst=conn.prepareStatement(sql2);
			pst.setInt(1, step.getPlan_id());
			pst.execute();
			conn.commit();
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

}
