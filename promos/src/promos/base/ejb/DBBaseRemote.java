package promos.base.ejb;

import java.io.Serializable;

import promos.base.objects.BaseEjbHome;
import promos.core.ejb.Core;


/**
 *
 * <p>标题: 类 ComRemote </p>
 * <p>描述: 包com下EJB的远程接口创建类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 11/27/2003
 *
 */

public final class DBBaseRemote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构建，禁止子类继承
	 */
	private DBBaseRemote() {
	}

	/**
	 * 得到数据库操作基本bean的远程接口
	 *
	 * @return DBBase
	 */
	public static DBBase getDBBase() {

		//创建
		DBBase dm = null;
		try {

	    	dm = (DBBase) BaseEjbHome.getHome("java:global/promos/DBBaseBean!promos.base.ejb.DBBase", DBBase.class);

	        if(dm == null){
	          System.out.print("Now can not look up the JNDI name : "+DBBase.class.getName());
	          return null;
	        }
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dm;
	}

}