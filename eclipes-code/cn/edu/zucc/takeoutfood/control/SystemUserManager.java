package cn.edu.zucc.takeoutfood.control;

import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.model.BeanSystemUser;

public class SystemUserManager {
	public static BeanSystemUser currentUser=null;//记录登陆者信息
	public BeanSystemUser loadUser(String userid,int usertype) throws BaseException{
		BeanSystemUser beanSystemUser=new BeanSystemUser();
		switch (usertype) {
		case 1:
			//管理员
			beanSystemUser.setSystemUserid("123");
			beanSystemUser.setPwd("123");
			break;
		case 2:
			//用户
			break;
		case 3:
			//商家
			break;
		case 4:
			//骑手
			break;
		default:
			break;
		}
		
		return beanSystemUser;
	}

}
