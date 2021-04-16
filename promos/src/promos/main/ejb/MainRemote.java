package promos.main.ejb;

import java.io.Serializable;

import promos.base.objects.BaseEjbHome;
import promos.core.ejb.Core;

/**
 *
 * <p>标题: 类 MainRemote </p>
 * <p>描述: 包Main下EJB的远程接口创建类 </p>
 * <p>版权: Copyright (c) 2003-2004 </p>
 * <p>公司: 广州升域传媒有限公司 </p>
 * 保留所有权利。
 *
 * @author 胡维新
 * @version 1.0 06/21/2004
 *
 */

public final class MainRemote
    implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
   * 构建，禁止子类继承
   */
  private MainRemote() {
  }

  /**
   * 得到继保通知单管理bean的远程接口
   *
   * @return Main
   */
  public static Main getMain() {

    //创建
	  Main dm = null;
    try {
      
	  	dm = (Main) BaseEjbHome.getHome("java:global/promos/MainBean!promos.main.ejb.Main", Main.class);
	
	    if(dm == null){
	      System.out.print("Now can not look up the JNDI name : "+Main.class.getName());
	      return null;
	    }
      

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return dm;
  }
}