package promos.base.ejb;

import java.io.Serializable;

import promos.base.objects.BaseEjbHome;

/**
 *
 * <p>标题: 类 CoreRemote </p>
 * <p>描述: 包Core下EJB的远程接口创建类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 06/21/2004
 *
 */

public final class BaseRemote
    implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * 构建，禁止子类继承
   */
  private BaseRemote() {
  }

  /**
   * 得到继保通知单管理bean的远程接口
   *
   * @return Base
   */
  public static Base getBase() {

    //创建
    Base dm = null;
    try {

    	dm = (Base) BaseEjbHome.getHome("java:global/promos/BaseBean!promos.base.ejb.Base", Base.class);
	
	    if(dm == null){
	      System.out.print("Now can not look up the JNDI name : "+Base.class.getName());
	      return null;
	    }
      
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return dm;
  }
}