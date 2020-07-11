package cn.edu.zucc.takeoutfood.control;

import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.model.BeanAdministrator;
import cn.edu.zucc.takeoutfood.model.BeanRider;
import cn.edu.zucc.takeoutfood.model.BeanShop;
import cn.edu.zucc.takeoutfood.model.BeanSystemUser;
import cn.edu.zucc.takeoutfood.model.BeanUser;
public class SystemUserManager {
	public static BeanSystemUser currentUser=null;//记录登陆者信息
	
	public BeanSystemUser loadUser(String userid,int usertype) throws BaseException{
		BeanSystemUser USA=new BeanSystemUser();
		switch (usertype) {
		case 1:
			//管理员
			AdministratorManager am=new AdministratorManager();
			BeanAdministrator ba =am.loadAdmins(userid);
			USA.setSystemUserid(ba.getAdminid());
			USA.setSystemUsername(ba.getAdminname());
			USA.setPwd(ba.getAPwd());
			USA.setSystemNUM(ba.getaNUM());
			break;
		case 2:
			//用户
			UserManager um=new UserManager();
			BeanUser bu=um.loadUser(userid);
			USA.setSystemUserid(bu.getUserid());
			USA.setSystemUsername(bu.getUsername());
			USA.setPwd(bu.getUpwd());
			USA.setSystemNUM(bu.getpNUM());
			break;
		case 3:
			//商家
			ShopManager sm=new ShopManager();
			BeanShop bs=sm.loadShop(userid);
			USA.setSystemUserid(bs.getShopid());
			USA.setSystemUsername(bs.getShopname());
			USA.setPwd(bs.getShoppwd());
			USA.setSystemNUM(bs.getShopNUM());
			break;
		case 4:
			//骑手
			RiderManager rm=new RiderManager();
			BeanRider br=rm.loadRider(userid);
			USA.setSystemUserid(br.getRiderid());
			USA.setSystemUsername(br.getRidername());
			USA.setPwd(br.getRiderpwd());
			USA.setSystemNUM(br.getRiderID());
			break;
		default:
			break;
		}
		USA.setStype(usertype);
		return USA;
	}
	
	

}
