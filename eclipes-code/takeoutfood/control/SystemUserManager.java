package cn.edu.zucc.takeoutfood.control;

import cn.edu.zucc.takeoutfood.util.BaseException;
import cn.edu.zucc.takeoutfood.model.BeanSystemUser;

public class SystemUserManager {
	public static BeanSystemUser currentUser=null;//��¼��½����Ϣ
	public BeanSystemUser loadUser(String userid,int usertype) throws BaseException{
		BeanSystemUser beanSystemUser=new BeanSystemUser();
		switch (usertype) {
		case 1:
			//����Ա
			beanSystemUser.setSystemUserid("123");
			beanSystemUser.setPwd("123");
			break;
		case 2:
			//�û�
			break;
		case 3:
			//�̼�
			break;
		case 4:
			//����
			break;
		default:
			break;
		}
		
		return beanSystemUser;
	}

}
