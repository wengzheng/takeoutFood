package cn.edu.zucc.takeoutfood.util;

public class DbException extends BaseException {
	public DbException(java.lang.Throwable ex){
		super("数据库操作错误："+ex.getMessage());
	}
}
