package promos.custom.ejb;

import java.io.Serializable;

import promos.base.ejb.Base;
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

public final class CustomReRemote
    implements Serializable {

  /**
   * 构建，禁止子类继承
   */
  private CustomReRemote() {
  }

  /**
   * 得到继保通知单管理bean的远程接口
   *
   * @return Main
   */
  public static CustomRe getCustomRe() {

    //创建
	  CustomRe dm = null;
    try {
    	dm = (CustomRe) BaseEjbHome.getHome("java:global/promos/CustomReBean!promos.custom.ejb.CustomRe", CustomRe.class);

        if(dm == null){
          System.out.print("Now can not look up the JNDI name : "+CustomRe.class.getName());
          return null;
        }
    	
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return dm;
  }
}